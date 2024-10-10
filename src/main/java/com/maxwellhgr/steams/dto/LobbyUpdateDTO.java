package com.maxwellhgr.steams.dto;

import com.maxwellhgr.steams.entities.User;

public record LobbyUpdateDTO(String name, Integer gameCode, Long addUserId) {
}
