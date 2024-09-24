package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.UserDTO;
import com.epam.springcore.task.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapUserToDTO() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setActive(true);

        UserDTO userDTO = mapper.userToDTO(user);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getUsername()).isEqualTo("testuser");
    }

    @Test
    void shouldMapUserDTOToUserAndIgnoreFields() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");

        User user = mapper.userToEntity(userDTO);

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getUserId()).isNull();
        assertThat(user.getPassword()).isNull();
        assertThat(user.isActive()).isFalse();
    }
}