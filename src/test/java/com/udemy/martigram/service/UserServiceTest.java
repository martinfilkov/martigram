package com.udemy.martigram.service;

import com.udemy.martigram.dao.UserRepository;
import com.udemy.martigram.dto.UserDTO;
import com.udemy.martigram.entity.GramRole;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.entity.RoleType;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.mapper.UserDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDTOMapper userMapper;

    private GramUser user;
    private UserDTO dto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = GramUser.builder()
                .id(1L)
                .email("test@test.com")
                .username("test")
                .role(new GramRole(RoleType.USER))
                .build();

        dto = UserDTO.builder()
                .id(1L)
                .username("test")
                .email("test@test.com")
                .build();
    }

    @Test
    public void shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.apply(any(GramUser.class)))
                .thenReturn(dto);

        List<UserDTO> response = userService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    public void shouldReturnUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.apply(any(GramUser.class)))
                .thenReturn(dto);

        dto = userService.findById(1L);
        assertNotNull(dto);
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getUsername(), user.getUsername());
    }

    @Test
    public void shouldDeleteUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFoundForDeletion() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            userService.delete(1L);
        });

        assertEquals("User with id 1 not found", thrown.getMessage());
    }
}