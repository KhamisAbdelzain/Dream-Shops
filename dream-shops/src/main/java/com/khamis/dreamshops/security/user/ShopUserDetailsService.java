package com.khamis.dreamshops.security.user;

import com.khamis.dreamshops.exceptions.ProductNotFoundException;
import com.khamis.dreamshops.model.User;
import com.khamis.dreamshops.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ShopUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= Optional.ofNullable(userRepository
                .findByEmail(email))
                .orElseThrow(()->new ProductNotFoundException("User NOt FOUND"));
        return ShopUserDetails.buildUserDetails(user);

    }
}
