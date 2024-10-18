package com.maxwellhgr.steams.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxwellhgr.steams.entities.Game;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SteamApiService {

    private final String key = "11266E6017DE54EFC4B5133C24C680CD";

    private final WebClient.Builder webClientBuilder;
    private final GameRepository gameRepository;

    @Autowired
    public SteamApiService(WebClient.Builder webClientBuilder, GameRepository gameRepository) {
        this.webClientBuilder = webClientBuilder;
        this.gameRepository = gameRepository;
    }

    public User getSteamUserAndGames(String steamUrl) {
        String name = getVanityName(steamUrl);
        String id = getSteamIdFromName(name);
        String url = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + key + "&steamids=" + id;
        List<Game> games = getOwnedGames(id);

        String steamData = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        User user = getUserFromData(steamData);
        for(Game game : games) {
            Integer gameId = game.getAppId();
            Optional<Game> savedGame = gameRepository.findById(gameId);
            if(savedGame.isEmpty()){
                gameRepository.save(game);
            }
            user.addGame(game);
        }
        return user;
    }

    public User getUserFromData(String steamData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(steamData);

            JsonNode playerNode = rootNode.path("response").path("players").get(0);

            User user = new User();
            user.setId(playerNode.path("steamid").asText());
            user.setUsername(playerNode.path("personaname").asText());
            user.setPhotoUrl(playerNode.path("avatarfull").asText());

            return user;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSteamIdFromName(String name){
        String url = "https://api.steampowered.com/ISteamUser/ResolveVanityURL/v1/?key=" + key + "&vanityurl=";
        String steamData = webClientBuilder.build()
                .get()
                .uri(url + name)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return getIdFromData(steamData);
    }

    public String getIdFromData(String steamData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(steamData);
            JsonNode responseNode = rootNode.path("response");

            return (responseNode.path("steamid").asText());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getVanityName(String steamUrl) {
        String[] parts = steamUrl.split("/");
        return parts[parts.length - 1].isEmpty() ? parts[parts.length - 2] : parts[parts.length - 1];
    }

    public List<Game> getOwnedGames(String id) {
        String url = "https://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=" + key + "&steamid=" + id + "&include_appinfo=1&include_played_free_games=1";
        String steamData = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return getGamesFromData(steamData);

    }

    public List<Game> getGamesFromData(String steamData) {
        ObjectMapper mapper = new ObjectMapper();
        List<Game> gamesList = new ArrayList<>();

        try {
            JsonNode rootNode = mapper.readTree(steamData);
            JsonNode responseNode = rootNode.path("response");
            JsonNode gamesNode = responseNode.path("games");

            for (JsonNode gameNode : gamesNode) {
                Game game = new Game();
                game.setAppId(gameNode.path("appid").asInt());
                game.setName(gameNode.path("name").asText());
                game.setBanner("https://cdn.cloudflare.steamstatic.com/steam/apps/" + game.getAppId() + "/header.jpg");
                gamesList.add(game);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return gamesList;
    }

}
