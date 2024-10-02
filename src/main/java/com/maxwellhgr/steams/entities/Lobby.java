package com.maxwellhgr.steams.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Lobby implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer gameCode;

    private final Set<User> users = new HashSet<User>();

    public Lobby() {}

    public Lobby(Long id, Integer gameCode) {
        this.id = id;
        this.gameCode = gameCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGameCode() {
        return gameCode;
    }

    public void setGameCode(Integer gameCode) {
        this.gameCode = gameCode;
    }

    public Set<User> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lobby lobby = (Lobby) o;
        return Objects.equals(id, lobby.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
