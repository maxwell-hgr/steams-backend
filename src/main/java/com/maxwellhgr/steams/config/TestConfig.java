package com.maxwellhgr.steams.config;

import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.repositories.UserRepository;
import com.maxwellhgr.steams.services.SteamApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SteamApiService steamApiService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TestConfig(UserRepository userRepository, SteamApiService steamApiService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.steamApiService = steamApiService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception{
        User u1 = this.steamApiService.getUser("https://steamcommunity.com/id/maxwellhgr");
        u1.setPassword(passwordEncoder.encode(("123456")));
        u1.setEmail("maxwell.hygor01@gmail.com");
        this.userRepository.save(u1);
        User u2 = this.steamApiService.getUser("https://steamcommunity.com/id/COOOB");
        u2.setPassword(passwordEncoder.encode(("123456")));
        u2.setEmail("cob@gmail.com");
        this.userRepository.save(u2);
        User u3 = this.steamApiService.getUser("https://steamcommunity.com/id/DitoZ");
        u3.setPassword(passwordEncoder.encode(("123456")));
        u3.setEmail("dito@gmail.com");
        this.userRepository.save(u3);
        User u4 = this.steamApiService.getUser("https://steamcommunity.com/id/1DIA6GANHA");
        u4.setPassword(passwordEncoder.encode(("123456")));
        u4.setEmail("dudu@gmail.com");
        this.userRepository.save(u4);
        User u5 = this.steamApiService.getUser("https://steamcommunity.com/id/LPMS");
        u5.setPassword(passwordEncoder.encode(("123456")));
        u5.setEmail("dizas@gmail.com");
        this.userRepository.save(u5);
        User u6 = this.steamApiService.getUser("https://steamcommunity.com/id/mtss271");
        u6.setPassword(passwordEncoder.encode(("123456")));
        u6.setEmail("mts@gmail.com");
        this.userRepository.save(u6);
        User u7 = this.steamApiService.getUser("https://steamcommunity.com/id/lucassaitama");
        u7.setPassword(passwordEncoder.encode(("123456")));
        u7.setEmail("pabo@gmail.com");
        this.userRepository.save(u7);

    }
}
