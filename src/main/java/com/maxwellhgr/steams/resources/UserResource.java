package com.maxwellhgr.steams.resources;

import com.maxwellhgr.steams.dto.UserResponseDTO;
import com.maxwellhgr.steams.dto.UserUpdateDTO;
import com.maxwellhgr.steams.entities.Game;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.services.SteamApiService;
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
    private final SteamApiService steamApiService;

    @Autowired
    public UserResource(UserService userService, SteamApiService steamApiService) {
        this.userService = userService;
        this.steamApiService = steamApiService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable String id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<UserResponseDTO> getProfile(HttpServletRequest request) {
        UserResponseDTO user = userService.getProfileFromRequest(request);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody UserUpdateDTO data, HttpServletRequest request){
        User user = userService.getUserFromRequest(request);
        User updatedUser = userService.update(data, user);
        return ResponseEntity.ok().body(updatedUser);
    }

    @GetMapping(value = "/games")
    public ResponseEntity<List<Game>> getGamesById(HttpServletRequest request){
        User user = userService.getUserFromRequest(request);
        List<Game> games = steamApiService.getOwnedGames(user.getId());
        return ResponseEntity.ok().body(games);
    }

    @GetMapping(value = "/friends")
    public ResponseEntity<List<User>> getFriends(HttpServletRequest request){
        User user = userService.getUserFromRequest(request);
        List<User> friends = steamApiService.getFriendsFromId(user.getId());
        return ResponseEntity.ok().body(friends);
    }
}
