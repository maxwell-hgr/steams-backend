package com.maxwellhgr.steams.config;

import com.maxwellhgr.steams.entities.Lobby;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.repositories.LobbyRepository;
import com.maxwellhgr.steams.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LobbyRepository lobbyRepository;

    @Override
    public void run(String... args) throws Exception{
        User u1 = new User(null, "Dole", "dole@james", "123456", "");
        User u2 = new User(null, "James", "james@james", "123456", "");
        User u3 = new User(null, "Silva", "dole@james", "123456", "");

        userRepository.saveAll(Arrays.asList(u1, u2, u3));

        Lobby l1 = new Lobby(null, 255, u1);
        Lobby l2 = new Lobby(null, 366, u3);

        lobbyRepository.saveAll(Arrays.asList(l1, l2));

        l1.getUsers().add(u1);
        l1.getUsers().add(u2);
        l1.getUsers().add(u3);

        l2.getUsers().add(u2);

        lobbyRepository.saveAll(Arrays.asList(l1, l2));
    }
}
