package com.dallman.lookingtoplay.Game;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="platforms")
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="platform_name")
    private String name;

    @Column(name="abbreviation")
    private String abbreviation;

    @Column(name="igdb_platform_id", unique = true)
    @NotNull
    private Integer igdbPlatformId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,  CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name="platform_game",
            joinColumns = @JoinColumn(name="platform_id"),
            inverseJoinColumns = @JoinColumn(name="game_id"))
    private List<Game> games;

    public Platform() {
    }

    public Platform(Integer igdbPlatformId) {
        this.igdbPlatformId = igdbPlatformId;
    }

    public Platform(Integer igdbPlatformId, String abbreviation, String name) {
        this.igdbPlatformId = igdbPlatformId;
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public Platform(Integer igdbPlatformId, String abbreviation, String name, List<Game> games) {
        this.igdbPlatformId = igdbPlatformId;
        this.abbreviation = abbreviation;
        this.name = name;
        this.games = games;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIgdbPlatformId() {
        return igdbPlatformId;
    }

    public void setIgdbPlatformId(Integer igdbPlatformId) {
        this.igdbPlatformId = igdbPlatformId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<Game> getGames() {

        if (games == null) {
            games = new ArrayList<Game>();
        }

        return games;
    }

    public void setGames(List<Game> games) {
        games = getGames();
        this.games = games;
    }

    public void addGame(Game game) {
        if (!games.contains(game)) {
            games.add(game);
            game.getPlatforms().add(this);
        }
    }

    @Override
    public String toString() {
        return "Platform{" +
                "id=" + id +
                ", platformId=" + igdbPlatformId +
                '}';
    }
}
