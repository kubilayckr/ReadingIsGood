package com.kubilay.readingisgood.service.user;

import com.kubilay.readingisgood.entity.user.User;
import com.kubilay.readingisgood.exception.BusinessException;
import com.kubilay.readingisgood.mapper.user.UserMapper;
import com.kubilay.readingisgood.model.dto.UserDTO;
import com.kubilay.readingisgood.model.request.CreateUserRequest;
import com.kubilay.readingisgood.model.response.CreateUserResponse;
import com.kubilay.readingisgood.repository.user.UserRepository;
import com.kubilay.readingisgood.service.SequenceGeneratorServiceImpl;
import com.kubilay.readingisgood.service.customer.impl.CustomerServiceImpl;
import com.kubilay.readingisgood.service.user.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {

    @Mock
    SequenceGeneratorServiceImpl sequenceGenerator;

    @Mock
    MessageSource messageSource;

    @Mock
    UserRepository userRepository;

    @Spy
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    CustomerServiceImpl customerService;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void test_CreateOrder(){
        User user = generateUser();
        when(sequenceGenerator.generateSequence(any())).thenReturn(1L);
        when(userRepository.save(any())).thenReturn(user);
        when(customerService.createCustomer(any())).thenReturn(1L);
        assertEquals(1L, userService.createUser(generateCreateUserRequest()).getUserId());
    }

    @Test
    void test_GetUser_NotNull(){
        User user = generateUser();
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(user));
        UserDTO userDTO = userService.getUser(1L);
        assertEquals(user.getId(), userDTO.getId());
    }

    @Test
    void test_GetUser_Null(){
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> userService.getUser(1L));
    }

    private UserDTO generateUserDTO() {
        return new UserDTO(null, "email@gmail.com", "Password", "Type");
    }

    private User generateUser() {
        return new User(1L, "email@gmail.com", "Password", "Type", List.of("TYPE"));
    }

    private CreateUserResponse generateCreateUserResponse() {
        return new CreateUserResponse(1L, 1L);
    }

    private CreateUserRequest generateCreateUserRequest() {
        return new CreateUserRequest(null, null, "email@gmail.com", "Password", "Type");
    }
}
