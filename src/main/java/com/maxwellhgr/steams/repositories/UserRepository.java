package com.maxwellhgr.steams.repositories;

import com.maxwellhgr.steams.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
