package com.khamis.dreamshops.controller;

import com.khamis.dreamshops.exceptions.ProductNotFoundException;
import com.khamis.dreamshops.model.Cart;
import com.khamis.dreamshops.model.CartItem;
import com.khamis.dreamshops.model.User;
import com.khamis.dreamshops.repository.userRepository.UserRepository;
import com.khamis.dreamshops.response.ApiResponse;
import com.khamis.dreamshops.service.cartService.ICartItemService;
import com.khamis.dreamshops.service.cartService.ICartService;
import com.khamis.dreamshops.service.userService.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.PartialResultException;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService iCartItemService;
    private final ICartService iCartService;
    private final IUserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse>addItemToCart(@RequestParam Long productId,@RequestParam Long quantity){
        try {
                User user=userService.getAuthenticatedUser();
                Cart cart= iCartService.initializeNewCart(user);

            iCartItemService.addItemToCart(cart.getId(),productId, Math.toIntExact(quantity));
            return ResponseEntity.ok(new ApiResponse("Success",null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        catch (JwtException e){
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @DeleteMapping("/cart/{cartId}/item/{productId}/remove")
    public ResponseEntity<ApiResponse>removeItemFromCart(@PathVariable Long cartId,@PathVariable Long productId){
        try {
            iCartItemService.removeItemFromCart(cartId,productId);
            return ResponseEntity.ok(new ApiResponse("Success Delete",null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse>updateQuantity(@PathVariable Long cartId,@PathVariable Long ProductId,@RequestParam Long Quantity){
        try {
            iCartItemService.updateItemQuantity(cartId, ProductId, Math.toIntExact(Quantity));
            return ResponseEntity.ok(new ApiResponse("Success update",null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }


}
