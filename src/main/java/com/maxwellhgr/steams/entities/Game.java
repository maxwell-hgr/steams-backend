package com.maxwellhgr.steams.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
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

    @ManyToMany(mappedBy = "games")
    private Set<User> users = new HashSet<>();

}
