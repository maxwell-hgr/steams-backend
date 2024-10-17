package com.maxwellhgr.steams.repositories;

import com.maxwellhgr.steams.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    void deleteById(String id);
}
