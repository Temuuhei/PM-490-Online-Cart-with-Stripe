package com.pm490.PM490.controller;

import com.pm490.PM490.model.Role;
import com.pm490.PM490.model.User;
import com.pm490.PM490.model.UserStatus;
import com.pm490.PM490.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/user")

public class UserController {

    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    List<User> index() {
        System.out.println("__________ SEARCH _______");
        //System.out.println(user);
        System.out.println("__________ SEARCH _______");
        return userRepository.findAll();
    }

    @GetMapping("/getbyrole/{role}")
    List<User> getByRole(@PathVariable String role) {
        System.out.println("__________ SEARCH _______");
        System.out.println("__________ SEARCH _______");
        return userRepository.findAllByRole(Role.valueOf(role));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user")
    User add(@RequestBody User newUser) {
        System.out.println("__________ REGISTER _______");
        System.out.println(newUser);
        System.out.println("__________ REGISTER _______");
        return userRepository.save(newUser);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{id}")
    User get(@PathVariable Long id) {
        System.out.println("__________ DETAIL _______");
        System.out.println(id);
        System.out.println("__________ DETAIL _______");
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found - %d !" + id));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/user/{id}")
    User update(@RequestBody User updateUser, @PathVariable Long id) {
        System.out.println("__________ UPDATE _______");
        System.out.println(id);
        System.out.println(updateUser);
        System.out.println("__________ UPDATE _______");
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updateUser.getUsername());
                    user.setRole(updateUser.getRole());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    updateUser.setId(id);
                    return userRepository.save(updateUser);
                });
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/user/{id}")
    @CrossOrigin
    User delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        System.out.println("__________ SOFT DELETE _______");
        System.out.println(id);
        System.out.println("__________ SOFT DELETE _______");
        return userRepository.findById(id)
                .map(user -> {
                    user.setStatus(UserStatus.INACTIVE);
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found - %d !" + id));
    }

//    @GetMapping("/users")
//    public String allAccess() {
//        System.out.println("------------ INDEX ---------");
//        System.out.println("------------ INDEX ---------");
//        System.out.println("------------ INDEX ---------");
//        return "Public Content.";
//    }
}
