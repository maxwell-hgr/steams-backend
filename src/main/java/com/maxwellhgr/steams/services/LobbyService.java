package com.maxwellhgr.steams.services;

import com.maxwellhgr.steams.entities.Lobby;
import com.maxwellhgr.steams.repositories.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LobbyService {

    LobbyRepository lobbyRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    public List<Lobby> findAll(){
        return lobbyRepository.findAll();
    }
}
