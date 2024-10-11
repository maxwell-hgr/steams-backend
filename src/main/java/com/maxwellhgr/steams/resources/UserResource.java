package com.maxwellhgr.steams.resources;

import com.maxwellhgr.steams.dto.UserUpdateDTO;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.infra.security.SecurityFilter;
import com.maxwellhgr.steams.services.UserService;
import com.maxwellhgr.steams.services.exceptions.DatabaseException;
import com.maxwellhgr.steams.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserUpdateDTO data, HttpServletRequest request){
        User user = userService.getUserFromRequest(request);
        if(user.getId().equals(id)){
            User updatedUser = userService.update(data, user);
            return ResponseEntity.ok().body(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        if(user.getId().equals(id)){
            userService.delete(id);
        }
        return ResponseEntity.noContent().build();
    }
}
