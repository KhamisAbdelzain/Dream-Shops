package com.khamis.dreamshops.service.cartService;

import com.khamis.dreamshops.exceptions.ProductNotFoundException;
import com.khamis.dreamshops.model.Cart;
import com.khamis.dreamshops.model.User;
import com.khamis.dreamshops.repository.cartRpository.CartItemRepository;
import com.khamis.dreamshops.repository.cartRpository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator=new AtomicLong(0);

    @Transactional
    @Override
    public Cart getCart(Long id) {
        Cart cart= cartRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("The CART not Found"));
        BigDecimal totalAmount=cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void cleanCart(Long id) {
    Cart cart=getCart(id);
    cartItemRepository.deleteAllByCartId(id);
    cart.getItems().clear();
    cartRepository.deleteById(id);


    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart=getCart(id);
       return cart.getTotalAmount();
    }
    @Transactional
    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(()->{
                    Cart cart=new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });

    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
