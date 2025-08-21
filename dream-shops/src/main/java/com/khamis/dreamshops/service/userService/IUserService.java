package com.khamis.dreamshops.service.userService;

import com.khamis.dreamshops.request.CreateUserRequest;
import com.khamis.dreamshops.request.UpdateUserRequest;
import com.khamis.dreamshops.dto.UserDto;
import com.khamis.dreamshops.model.User;

public interface IUserService {
    User getUserById(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request,Long userid);
    void deleteUser(Long id);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
