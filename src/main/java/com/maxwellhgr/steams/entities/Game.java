package com.maxwellhgr.steams.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_games")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String appId;
    private String name;
    private String banner;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<Lobby> lobbies;

    @ManyToMany(mappedBy = "games")
    private Set<User> users = new HashSet<>();

}
