package com.maxwellhgr.steams.resources;

import com.maxwellhgr.steams.entities.Lobby;
import com.maxwellhgr.steams.services.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/lobbies")
public class LobbyResource {
    @Autowired
    private LobbyService lobbyService;

    @GetMapping
    public ResponseEntity<List<Lobby>> findAll(){
        List<Lobby> lobbies = lobbyService.findAll();
        return ResponseEntity.ok().body(lobbies);
    }
}
