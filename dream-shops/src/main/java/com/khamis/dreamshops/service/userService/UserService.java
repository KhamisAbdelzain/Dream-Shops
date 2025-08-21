package com.khamis.dreamshops.service.userService;

import com.khamis.dreamshops.request.CreateUserRequest;
import com.khamis.dreamshops.request.UpdateUserRequest;
import com.khamis.dreamshops.dto.UserDto;
import com.khamis.dreamshops.exceptions.ProductNotFoundException;
import com.khamis.dreamshops.model.User;
import com.khamis.dreamshops.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;



    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id).orElseThrow(()->new ProductNotFoundException("User not Found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request).filter
                        (user->! userRepository.existsByEmail(request.getEmail()))
                .map(req->{
                    User user=new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    return userRepository.save(user);
                }).orElseThrow(()->new ProductNotFoundException("Already Exists "+ request.getEmail()));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userid) {
        return userRepository.findById(userid).map(user->{
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            return userRepository.save(user);
        }).orElseThrow(()->new ProductNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete,()-> {
            throw new ProductNotFoundException("User Not found");
        });

    }
    @Override
    public UserDto convertUserToDto(User user){
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return userRepository.findByEmail(email);
    }
}
