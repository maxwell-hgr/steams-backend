package com.maxwellhgr.steams.services;

import com.maxwellhgr.steams.dto.UserResponseDTO;
import com.maxwellhgr.steams.dto.UserUpdateDTO;
import com.maxwellhgr.steams.entities.Game;
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
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SecurityFilter securityFilter;
    private final SteamApiService steamApiService;

    @Autowired
    public UserService(UserRepository userRepository, SecurityFilter securityFilter, SteamApiService steamApiService) {
        this.userRepository = userRepository;
        this.securityFilter = securityFilter;
        this.steamApiService = steamApiService;
    }

    public UserResponseDTO getProfileFromRequest(HttpServletRequest request) {
        User user = this.getUserFromRequest(request);
        String id = user.getId();
        String photoUrl = user.getPhotoUrl();
        Set<Game> games = user.getGames();
        List<User> friends = steamApiService.getFriendsFromId(user.getId());

        return new UserResponseDTO(id, photoUrl, games, friends);
    };

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

    public User getUserFromRequest(HttpServletRequest request) {
        String email = securityFilter.recoverEmailFromToken(request);
        return this.findByEmail(email);
    }
}
