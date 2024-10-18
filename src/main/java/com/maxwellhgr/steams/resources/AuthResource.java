package com.maxwellhgr.steams.resources;

import com.maxwellhgr.steams.dto.LoginRequestDTO;
import com.maxwellhgr.steams.dto.RegisterRequestDTO;
import com.maxwellhgr.steams.dto.ResponseDTO;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.infra.security.TokenService;
import com.maxwellhgr.steams.repositories.UserRepository;
import com.maxwellhgr.steams.services.SteamApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final SteamApiService steamApiService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body){
        User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getId(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.userRepository.findByEmail(body.email());

        if(user.isEmpty()) {
            User newUser = this.steamApiService.getSteamUserAndGames(body.steamUrl());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            this.userRepository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getId(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
