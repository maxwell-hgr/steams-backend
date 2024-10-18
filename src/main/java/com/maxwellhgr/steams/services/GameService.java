package com.maxwellhgr.steams.services;

import com.maxwellhgr.steams.entities.Game;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

}
