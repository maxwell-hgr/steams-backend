package com.maxwellhgr.steams.dto;

import java.util.List;

public record LobbyCreateDTO(String name, String appId, List<String> usersId) {
}
