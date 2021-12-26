package com.kubilay.readingisgood.mapper.user;

import com.kubilay.readingisgood.entity.user.User;
import com.kubilay.readingisgood.model.dto.UserDTO;
import com.kubilay.readingisgood.model.request.CreateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UserMapper {

    public abstract UserDTO toDTO(User user);

    public abstract User toModel(UserDTO userDTO);

    public abstract User toModel(CreateUserRequest createUserRequest);
}
