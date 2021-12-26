package com.kubilay.readingisgood.service.user.impl;

import com.kubilay.readingisgood.entity.user.User;
import com.kubilay.readingisgood.exception.BusinessException;
import com.kubilay.readingisgood.mapper.user.UserMapper;
import com.kubilay.readingisgood.model.dto.UserDTO;
import com.kubilay.readingisgood.model.request.CreateUserRequest;
import com.kubilay.readingisgood.model.response.CreateUserResponse;
import com.kubilay.readingisgood.repository.user.UserRepository;
import com.kubilay.readingisgood.service.SequenceGeneratorServiceImpl;
import com.kubilay.readingisgood.service.customer.CustomerService;
import com.kubilay.readingisgood.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

import static com.kubilay.readingisgood.exception.BusinessException.ServiceException.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SequenceGeneratorServiceImpl sequenceGenerator;
    private final MessageSource messageSource;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CustomerService customerService;

    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = userMapper.toModel(request);
        long id = sequenceGenerator.generateSequence(User.SEQUENCE_NAME);
        user.setId(id);
        userRepository.save(user).getId();
        Long customerNo = customerService.createCustomer(request);
        return new CreateUserResponse(id, customerNo);
    }

    public UserDTO getUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userMapper.toDTO(userOptional.orElseThrow(() ->
                new BusinessException(messageSource.getMessage(USER_NOT_FOUND.getKey(), new Object[] {id},
                        Locale.getDefault()), USER_NOT_FOUND.getStatus())));
    }
}
