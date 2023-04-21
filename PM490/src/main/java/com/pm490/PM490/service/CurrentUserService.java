package com.pm490.PM490.service;

import com.pm490.PM490.model.User;
import com.pm490.PM490.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CurrentUserService {
    @Autowired
    UserRepository userRepository;

    public Long findLoggedUserId() {
        UserDetailsImpl userDetail = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found - %d !"));
        return user.getId();
    }

    public User findLoggedUser() {
        UserDetailsImpl userDetail = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found - %d !"));
    }
}
