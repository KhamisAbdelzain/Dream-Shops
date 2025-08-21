package com.khamis.dreamshops.service.order;

import com.khamis.dreamshops.dto.OrderDTO;
import com.khamis.dreamshops.enums.OrderStatus;
import com.khamis.dreamshops.exceptions.ProductNotFoundException;
import com.khamis.dreamshops.model.Cart;
import com.khamis.dreamshops.model.Order;
import com.khamis.dreamshops.model.OrderItem;
import com.khamis.dreamshops.model.Product;
import com.khamis.dreamshops.repository.ProductRepo.ProductRepository;
import com.khamis.dreamshops.repository.orderRepository.OrderRepository;
import com.khamis.dreamshops.service.cartService.ICartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderSerice implements IOrderService{
    private final OrderRepository orderRepository;
    private final ICartService cartService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    
    @Override
    public Order orderPlace(Long userId) {
        Cart cart=cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem>orderItems=createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Order order1=orderRepository.save(order);
        cartService.cleanCart(cart.getId());
    return order1;
    }
    private Order createOrder(Cart cart){
        Order order=new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;

    }

    private List<OrderItem>createOrderItems(Order order, Cart cart){
        return cart.getItems().stream().map(cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory()-cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()
                    );
                }
        ).toList();

    }



    private BigDecimal calculateTotalAmount(List<OrderItem>items){
        return items.stream()
                .map(item->item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        return orderRepository.findById(orderId).map(this::convertToDto).orElseThrow(()->new ProductNotFoundException("order Not Found"));
    }
    @Override
    public List<OrderDTO>orders(Long userId){
            List<Order>orders=orderRepository.findByUserId(userId);
            return orders.stream().map(this::convertToDto).toList();
    }
    @Override
    public OrderDTO convertToDto(Order order){
        return modelMapper.map(order,OrderDTO.class);
    }
}
