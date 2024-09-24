package com.epam.springcore.task.mapper;


import com.epam.springcore.task.dto.UserDTO;
import com.epam.springcore.task.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToDTO(User user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "active", ignore = true)
    User userToEntity(UserDTO userDTO);
}
