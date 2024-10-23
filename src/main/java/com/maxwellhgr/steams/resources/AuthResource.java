package com.maxwellhgr.steams.resources;

import com.maxwellhgr.steams.dto.LoginRequestDTO;
import com.maxwellhgr.steams.dto.RegisterRequestDTO;
import com.maxwellhgr.steams.dto.ResponseDTO;
import com.maxwellhgr.steams.entities.Game;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.infra.security.TokenService;
import com.maxwellhgr.steams.repositories.UserRepository;
import com.maxwellhgr.steams.services.SteamApiService;
import com.maxwellhgr.steams.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final SteamApiService steamApiService;
    private final UserService userService;

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
            User newUser = this.steamApiService.getUser(body.steamUrl());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());

            this.userRepository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getId(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validate(HttpServletRequest request){
        User user = userService.getUserFromRequest(request);
        if(user != null){
            return ResponseEntity.ok().body(user.getId());
        }
        return ResponseEntity.badRequest().build();
    }
}
