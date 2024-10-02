package com.maxwellhgr.steams.repositories;

import com.maxwellhgr.steams.entities.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LobbyRepository extends JpaRepository<Lobby, Long> {
}
