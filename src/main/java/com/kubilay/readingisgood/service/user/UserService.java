package com.kubilay.readingisgood.service.user;

import com.kubilay.readingisgood.model.dto.UserDTO;
import com.kubilay.readingisgood.model.request.CreateUserRequest;
import com.kubilay.readingisgood.model.response.CreateUserResponse;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest request);

    UserDTO getUser(Long id);
}
