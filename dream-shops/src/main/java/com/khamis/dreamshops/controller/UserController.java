package com.khamis.dreamshops.controller;

import com.khamis.dreamshops.request.CreateUserRequest;
import com.khamis.dreamshops.request.UpdateUserRequest;
import com.khamis.dreamshops.dto.UserDto;
import com.khamis.dreamshops.model.User;
import com.khamis.dreamshops.response.ApiResponse;
import com.khamis.dreamshops.service.userService.IUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;


    @GetMapping("/{id}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {

        try {
            User user = userService.getUserById(id);
            UserDto userDto=userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Field", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            UserDto userDto=userService.convertUserToDto(user);

            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User Already here " + e.getMessage(), null));
        }

    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId) {
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto=userService.convertUserToDto(user);

            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User Already here " + e.getMessage(), null));
        }

    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> createUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Success Delete", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User Already here " + e.getMessage(), null));
        }

    }
}