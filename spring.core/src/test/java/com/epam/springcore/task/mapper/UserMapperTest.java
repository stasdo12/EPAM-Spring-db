package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.UserDTO;
import com.epam.springcore.task.model.User;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;


    @Test
    public void shouldMapUserToDTO() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setActive(true);

        UserDTO userDTO = userMapper.userToDTO(user);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void shouldMapUserDTOToUserAndIgnoreFields() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");

        User user = userMapper.userToEntity(userDTO);

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getUserId()).isNull();
        assertThat(user.getPassword()).isNull();
        assertThat(user.isActive()).isFalse();
    }
}