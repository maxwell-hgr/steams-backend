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

    private final UserRepository userRepository;

    private final LobbyRepository lobbyRepository;

    @Autowired
    public TestConfig(UserRepository userRepository, LobbyRepository lobbyRepository) {
        this.userRepository = userRepository;
        this.lobbyRepository = lobbyRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        User u1 = new User("23123123", "Dole", "dole@james", "123456", "");
        User u2 = new User("21312312", "James", "james@james", "123456", "");
        User u3 = new User("213123123", "Silva", "dole@james", "123456", "");
        User u4 = new User("22132213", "Jack", "jack@james", "123456", "");
        User u5 = new User("4165416541", "John", "john@james", "123456", "");
        User u6 = new User("41351351", "Jack", "jack@james", "123456", "");

        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6));

        Lobby l1 = new Lobby(null, "fun lobby",255, u1.getId());
        Lobby l2 = new Lobby(null, "hate lobby",366, u3.getId());
        Lobby l3 = new Lobby(null, "friends lobby",26588, u3.getId());
        Lobby l4 = new Lobby(null, "red dead lobby",26588, u5.getId());

        lobbyRepository.saveAll(Arrays.asList(l1, l2, l3, l4));

        l1.getUsers().add(u1);
        l1.getUsers().add(u2);
        l1.getUsers().add(u3);
        l1.getUsers().add(u4);
        l1.getUsers().add(u5);
        l2.getUsers().add(u2);
        l3.getUsers().add(u1);
        l3.getUsers().add(u2);
        l3.getUsers().add(u3);
        l4.getUsers().add(u1);
        l4.getUsers().add(u2);
        l4.getUsers().add(u4);

        lobbyRepository.saveAll(Arrays.asList(l1, l2));
    }
}
