package com.maxwellhgr.steams.resources;

import com.maxwellhgr.steams.dto.LobbyUpdateDTO;
import com.maxwellhgr.steams.entities.Lobby;
import com.maxwellhgr.steams.services.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/lobbies")
public class LobbyResource {

    private final LobbyService lobbyService;

    @Autowired
    public LobbyResource(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @GetMapping
    public ResponseEntity<List<Lobby>> findAll(){
        List<Lobby> lobbies = lobbyService.findAll();
        return ResponseEntity.ok().body(lobbies);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Lobby> findById(@PathVariable("id") long id){
        Lobby lobby = lobbyService.findById(id);
        return ResponseEntity.ok().body(lobby);
    }

    @PostMapping
    public ResponseEntity<Lobby> create(@RequestBody Lobby lobby){
        Lobby result = lobbyService.create(lobby);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Lobby> update(@PathVariable long id, @RequestBody LobbyUpdateDTO data){
        Lobby lobby = lobbyService.findById(id);
        Lobby updatedLobby = lobbyService.update(data, lobby);
        return ResponseEntity.ok().body(updatedLobby);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Lobby> delete(@PathVariable long id){
        Lobby lobby = lobbyService.findById(id);
        lobbyService.delete(id);
        return ResponseEntity.ok().body(lobby);
    }
}
