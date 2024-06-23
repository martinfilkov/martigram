package com.udemy.martigram.service;

import com.udemy.martigram.dao.UserRepository;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.dto.UserDTO;
import com.udemy.martigram.mapper.UserDTOMapper;
import com.udemy.martigram.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final UserDTOMapper userMapper;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public List<UserDTO> findAll() {
        List<GramUser> users = userRepository.findAll();
        return users.stream()
                .map(userMapper)
                .collect(Collectors.toList());
    }

    public UserDTO findById(long id) {
        Optional<GramUser> findUser = userRepository.findById(id);

        if(findUser.isPresent()) {
            return userMapper.apply(findUser.get());
        }
        else throw new NotFoundException("User with id " + id + " not found");
    }

    public void delete(Long id) {
        Optional<GramUser> deleteUser = userRepository.findById(id);

        if(deleteUser.isEmpty()) throw new NotFoundException("User with id " + id + " not found");

        userRepository.delete(deleteUser.get());
    }

    public UserDTO profile() {
        GramUser user = authenticatedUserProvider.getAuthenticatedUser();
        return userMapper.apply(user);
    }
}
