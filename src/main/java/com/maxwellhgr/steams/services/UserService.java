package com.maxwellhgr.steams.services;

import com.maxwellhgr.steams.dto.UserUpdateDTO;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.infra.security.SecurityFilter;
import com.maxwellhgr.steams.repositories.UserRepository;
import com.maxwellhgr.steams.services.exceptions.DatabaseException;
import com.maxwellhgr.steams.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SecurityFilter securityFilter;

    @Autowired
    public UserService(UserRepository userRepository, SecurityFilter securityFilter) {
        this.userRepository = userRepository;
        this.securityFilter = securityFilter;
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User update(UserUpdateDTO data, User user) {
        try {
            user.setEmail(data.email());
            user.setUsername(data.username());
            user.setPhotoUrl(data.photoUrl());
            userRepository.save(user);
            return user;
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    public void delete(String id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public User getUserFromRequest(HttpServletRequest request) {
        String email = securityFilter.recoverEmailFromToken(request);
        return this.findByEmail(email);
    }
}
