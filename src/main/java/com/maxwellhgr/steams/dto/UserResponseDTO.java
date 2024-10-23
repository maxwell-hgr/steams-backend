package com.maxwellhgr.steams.dto;

import com.maxwellhgr.steams.entities.Game;
import com.maxwellhgr.steams.entities.User;

import java.util.List;
import java.util.Set;

public record UserResponseDTO (String id, String photoUrl, Set<Game> games, List<User> friends){
}