package com.khamis.dreamshops.controller;

import com.khamis.dreamshops.exceptions.ProductNotFoundException;
import com.khamis.dreamshops.model.Cart;
import com.khamis.dreamshops.response.ApiResponse;
import com.khamis.dreamshops.service.cartService.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

@GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse>getCart(@PathVariable Long cartId){
        try {
            Cart cart=cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success",cart));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse>clearCart( @PathVariable Long cartId){
        try {
            cartService.cleanCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success",null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error in task",null));
            }
    }
    @GetMapping("/{cartId}/cart/total-price")
    public  ResponseEntity<ApiResponse>getTotalPrice(@PathVariable Long cartId){
        try {
            BigDecimal totalPrice=cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Success",totalPrice));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Eroor",null));
        }
    }




}
