package com.maxwellhgr.steams.repositories;

import com.maxwellhgr.steams.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
