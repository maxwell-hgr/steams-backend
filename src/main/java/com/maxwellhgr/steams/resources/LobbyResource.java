package com.maxwellhgr.steams.resources;

import com.maxwellhgr.steams.dto.LobbyUpdateDTO;
import com.maxwellhgr.steams.entities.Lobby;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.services.LobbyService;
import com.maxwellhgr.steams.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/lobbies")
public class LobbyResource {

    private final LobbyService lobbyService;
    private final UserService userService;

    @Autowired
    public LobbyResource(LobbyService lobbyService, UserService userService) {
        this.lobbyService = lobbyService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Lobby>> findAll(){
        List<Lobby> lobbies = lobbyService.findAll();
        return ResponseEntity.ok().body(lobbies);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Set<Lobby>> findAllByUser(HttpServletRequest request){
        User user = userService.getUserFromRequest(request);
        Set<Lobby> lobbies = user.getLobbies();
        return ResponseEntity.ok().body(lobbies);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<Set<Lobby>> findAllByUserId(@PathVariable long id){
        User user = userService.findById(id);
        Set<Lobby> lobbies = user.getLobbies();
        return ResponseEntity.ok().body(lobbies);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Lobby> findById(@PathVariable("id") long id){
        Lobby lobby = lobbyService.findById(id);
        return ResponseEntity.ok().body(lobby);
    }

    @PostMapping
    public ResponseEntity<Lobby> create(@RequestBody Lobby lobby, HttpServletRequest request){
        User user = userService.getUserFromRequest(request);
        Lobby result = lobbyService.create(lobby, user);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Lobby> update(@PathVariable long id, @RequestBody LobbyUpdateDTO data, HttpServletRequest request){
        User user = userService.getUserFromRequest(request);
        Lobby lobby = lobbyService.findById(id);
        if(lobby.getOwnerId().equals(user.getId())){
            Lobby updatedLobby = lobbyService.update(data, lobby);
            return ResponseEntity.ok().body(updatedLobby);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Lobby> delete(@PathVariable long id, HttpServletRequest request){
        User user = userService.getUserFromRequest(request);
        Lobby lobby = lobbyService.findById(id);
        if(lobby.getOwnerId().equals(user.getId())){
            lobbyService.delete(id);
            return ResponseEntity.ok().body(lobby);
        }
        return ResponseEntity.notFound().build();
    }
}
