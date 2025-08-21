package com.khamis.dreamshops.service.cartService;

import com.khamis.dreamshops.model.Cart;
import com.khamis.dreamshops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void cleanCart(Long id);
    BigDecimal getTotalPrice(Long id);


    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
