package com.maxwellhgr.steams.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class Game implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int appId;
    private String name;
    private String banner;

    public Game() {}

}
