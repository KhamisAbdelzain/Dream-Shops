package com.khamis.dreamshops.controller;

import com.khamis.dreamshops.dto.OrderDTO;
import com.khamis.dreamshops.model.Order;
import com.khamis.dreamshops.response.ApiResponse;
import com.khamis.dreamshops.service.cartService.ICartService;
import com.khamis.dreamshops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse>createOrder(@RequestParam Long userId){
        try {
            Order order=orderService.orderPlace(userId);
            OrderDTO orderDTO=orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Success",orderDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal Server Error",e.getMessage()) );
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<ApiResponse>getOrderById(@PathVariable Long id){
        try {
            OrderDTO order=orderService.getOrder(id);
            return ResponseEntity.ok(new ApiResponse("Success",order));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal Error",e.getMessage()));
        }

    }
    @GetMapping("/order/{userId}")
    public ResponseEntity<ApiResponse>getUserOrder(@RequestParam Long userId){
        try {
            List<OrderDTO> order=orderService.orders(userId);
            return ResponseEntity.ok(new ApiResponse("Success",order));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal Error",e.getMessage()));
        }

    }

}
