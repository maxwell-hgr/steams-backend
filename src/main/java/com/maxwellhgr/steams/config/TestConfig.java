package com.maxwellhgr.steams.config;

import com.maxwellhgr.steams.entities.User;
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

    @Autowired
    public TestConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        for(int i = 0; i < 10; i++){
            User user = new User();
            user.setId("0" + i);
            user.setUsername("username" + i);
            user.setPassword("password" + i);
            user.setEmail("email" + i);
            userRepository.save(user);
        }
    }
}
