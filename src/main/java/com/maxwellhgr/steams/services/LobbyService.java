package com.maxwellhgr.steams.services;

import com.maxwellhgr.steams.dto.LobbyUpdateDTO;
import com.maxwellhgr.steams.dto.UserUpdateDTO;
import com.maxwellhgr.steams.entities.Lobby;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.repositories.LobbyRepository;
import com.maxwellhgr.steams.services.exceptions.DatabaseException;
import com.maxwellhgr.steams.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Lobby findById(long id) {
        Optional<Lobby> lobby = lobbyRepository.findById(id);
        return lobby.orElseThrow(() -> new RuntimeException("Lobby not find"));
    }

    public Lobby create(Lobby lobby, User user){
        lobby.setOwnerId(user.getId());
        return lobbyRepository.save(lobby);
    }

    public Lobby update(LobbyUpdateDTO data, Lobby lobby) {
        try {
            lobby.setName(data.name());
            lobby.setGameCode(data.gameCode());
            lobbyRepository.save(lobby);
            return lobby;
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    public void delete(long id) {
        try {
            lobbyRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
