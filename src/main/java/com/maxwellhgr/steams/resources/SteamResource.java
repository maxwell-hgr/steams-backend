package com.maxwellhgr.steams.resources;

import com.maxwellhgr.steams.entities.Game;
import com.maxwellhgr.steams.entities.User;
import com.maxwellhgr.steams.services.GameService;
import com.maxwellhgr.steams.services.SteamApiService;
import com.maxwellhgr.steams.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/steam")
public class SteamResource {

    private final SteamApiService steamApiService;
    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public SteamResource(SteamApiService steamApiService, GameService gameService, UserService userService) {
        this.steamApiService = steamApiService;
        this.gameService = gameService;
        this.userService = userService;
    }

    @GetMapping(value = "/games")
    public ResponseEntity<List<Game>> getGames() {
        List<Game> games = gameService.findAll();
        return ResponseEntity.ok(games);
    }

    @GetMapping(value = "/games/{id}")
    public ResponseEntity<List<Game>> getGamesById(@PathVariable String id){
        String gamesData = steamApiService.getOwnedGames(id);
        List<Game> games = steamApiService.getGamesFromData(gamesData);
        return ResponseEntity.ok().body(games);
    }

    @GetMapping(value = "/friends/{appId}")
    public ResponseEntity<List<User>> getFriendsWithGame(@PathVariable String appId, HttpServletRequest request){
        User user = userService.getUserFromRequest(request);
        String friendsData = steamApiService.getFriendsFromId(user.getId());
        List<User> friendsWithGame = steamApiService.friendsWithGame(friendsData, appId);
        return ResponseEntity.ok().body(friendsWithGame);
    }
}
