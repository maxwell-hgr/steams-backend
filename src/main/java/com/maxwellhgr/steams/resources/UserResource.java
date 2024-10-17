package com.maxwellhgr.steams.resources;

import com.maxwellhgr.steams.dto.UserUpdateDTO;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<User> getProfile(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable String id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody UserUpdateDTO data, HttpServletRequest request){
        User user = userService.getUserFromRequest(request);
        if(user.getId().equals(id)){
            User updatedUser = userService.update(data, user);
            return ResponseEntity.ok().body(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> delete(@PathVariable String id, HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        if(user.getId().equals(id)){
            userService.delete(id);
        }
        return ResponseEntity.noContent().build();
    }
}
