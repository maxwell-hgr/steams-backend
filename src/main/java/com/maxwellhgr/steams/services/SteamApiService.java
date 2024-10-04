package com.maxwellhgr.steams.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxwellhgr.steams.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
public class SteamApiService {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public SteamApiService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public User getSteamUser(String steamUrl) {
        String steamData = webClientBuilder.build()
                .get()
                .uri(steamUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return getUserFromData(steamData);
    }

    public User getUserFromData(String steamData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(steamData);

            JsonNode playerNode = rootNode.path("response").path("players").get(0);

            User user = new User();
            user.setId(playerNode.path("steamid").asLong());
            user.setUsername(playerNode.path("personaname").asText());
            user.setPhotoUrl(playerNode.path("avatarfull").asText());

            return user;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
