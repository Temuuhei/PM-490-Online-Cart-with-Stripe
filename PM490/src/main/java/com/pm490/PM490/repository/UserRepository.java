package com.pm490.PM490.repository;

import java.util.List;
import java.util.Optional;

import com.pm490.PM490.model.Role;
import com.pm490.PM490.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findAllByRole(Role role);

    void delete(User user);
}
