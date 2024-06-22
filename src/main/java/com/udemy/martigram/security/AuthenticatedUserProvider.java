package com.udemy.martigram.security;

import com.udemy.martigram.dao.UserRepository;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserProvider {
    private final UserRepository userRepository;

    public GramUser getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<GramUser> findUser = userRepository.findByUsername(auth.getName());

        if (findUser.isEmpty()) {
            throw new NotFoundException("User authentication not found");
        }

        return findUser.get();
    }
}
