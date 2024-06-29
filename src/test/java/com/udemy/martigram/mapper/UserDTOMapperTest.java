package com.udemy.martigram.mapper;

import com.udemy.martigram.dto.UserDTO;
import com.udemy.martigram.entity.GramRole;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.entity.RoleType;
import com.udemy.martigram.exception.InvalidActionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDTOMapperTest {

    @Autowired
    private UserDTOMapper mapper;

    @Test
    public void shouldMapUserToUserDto(){
        GramUser user = GramUser.builder()
                .id(1L)
                .username("johndoe")
                .email("johndoe@gmail.com")
                .role(new GramRole(RoleType.USER))
                .build();

        UserDTO dto = mapper.apply(user);

        assertEquals(dto.getUsername(), user.getUsername());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getRoleId(), user.getRole().getId());
    }

    @Test
    public void shouldThrowInvalidArgumentsExceptionWhenUserIsNull(){
        var ex = assertThrows(InvalidActionException.class, () -> mapper.apply(null));

        assertEquals("User cannot be null", ex.getMessage());
    }
}