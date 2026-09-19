package com.dallman.lookingtoplay.Game;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="platforms")
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,  CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name="platform_game",
            joinColumns = @JoinColumn(name="platform_id"),
            inverseJoinColumns = @JoinColumn(name="game_id"))
    private List<Game> gamesList;

    private int platformId;

    public Platform(int platformId) {
        this.id = id;
        this.platformId = platformId;
    }

    public Platform() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public List<Game> getGamesList() {

        if (gamesList == null) {
            gamesList = new ArrayList<Game>();
        }

        return gamesList;
    }

    public void setGamesList(List<Game> gamesList) {
        this.gamesList = gamesList;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "id=" + id +
                ", platformId=" + platformId +
                '}';
    }
}
