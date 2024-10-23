package com.maxwellhgr.steams.services;

import com.maxwellhgr.steams.dto.LobbyCreateDTO;
import com.maxwellhgr.steams.dto.LobbyUpdateDTO;
import com.maxwellhgr.steams.entities.Game;
import com.maxwellhgr.steams.entities.Lobby;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.repositories.GameRepository;
import com.maxwellhgr.steams.repositories.LobbyRepository;
import com.maxwellhgr.steams.repositories.UserRepository;
import com.maxwellhgr.steams.services.exceptions.DatabaseException;
import com.maxwellhgr.steams.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LobbyService {

    private final GameRepository gameRepository;
    LobbyRepository lobbyRepository;
    UserRepository userRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public List<Lobby> findAll(){
        return lobbyRepository.findAll();
    }

    public Lobby findById(long id) {
        Optional<Lobby> lobby = lobbyRepository.findById(id);
        return lobby.orElseThrow(() -> new RuntimeException("Lobby not found"));
    }

    public Lobby create(LobbyCreateDTO lobby, User user){
        Lobby createdLobby = new Lobby();

        Optional<Game> game = gameRepository.findById(lobby.appId());
        game.ifPresent(createdLobby::setGame);

        createdLobby.setName(lobby.name());
        createdLobby.setOwnerId(user.getId());
        createdLobby.addUser(user);

        user.addLobby(createdLobby);
        userRepository.save(user);

        List<String> users = lobby.usersId();
        for(String userId : users){
            Optional<User> userOptional = userRepository.findById(userId);
            if(userOptional.isPresent()){
                createdLobby.addUser(userOptional.get());
                userOptional.get().addLobby(createdLobby);
                userRepository.save(userOptional.get());
            }
        }
        return lobbyRepository.save(createdLobby);
    }

    public Lobby update(LobbyUpdateDTO data, Lobby lobby) {
        try {
            lobby.setName(data.name());
            if(!Objects.equals(lobby.getGame().getAppId(), data.appId())){
                Optional<Game> game = gameRepository.findById(data.appId());
                game.ifPresent(lobby::setGame);
            }
            if(data.addUserId() != null){
                Optional<User> user = userRepository.findById(data.addUserId());
                user.ifPresent(lobby::addUser);
            }
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

    public Set<Lobby> findAllByUser(User user){
        return user.getLobbies();
    }
}
