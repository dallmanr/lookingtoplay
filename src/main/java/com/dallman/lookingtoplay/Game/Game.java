package com.dallman.lookingtoplay.Game;


import com.dallman.lookingtoplay.Lobby.Lobby;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    @NotNull
    private String name;

    @Column(name="cover")
    private String cover;

    @Column(name="summary")
    private String summary;

    @Column(name="url")
    private String url;

    @Column(name="game_type")
    private int gameType;

    @OneToMany(mappedBy = "game")
    private List<Lobby> lobbies;

    @OneToMany(mappedBy="gamesList")
    private List<Platform> platforms;


    public Game() {
    }

    public Game(String name, String summary, String url, int gameType) {
        this.name = name;
        this.summary = summary;
        this.url = url;
        this.gameType = gameType;
        this.platforms = new ArrayList<>();
    }

    public Game(String name, String cover, String summary, String url, int gameType) {
        this.name = name;
        this.cover = cover;
        this.summary = summary;
        this.url = url;
        this.gameType = gameType;
        this.platforms = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public List<Lobby> getLobbies() {
        return lobbies;
    }

    public void setLobbies(List<Lobby> lobbies) {
        this.lobbies = lobbies;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public Platform findPlatformById(int id) {
        if (this.getPlatforms().contains(id)) {
            return this.getPlatforms().stream().filter(p -> p.getId() == id).findFirst().get();
        } else  {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", summary='" + summary + '\'' +
                ", url='" + url + '\'' +
                ", gameType=" + gameType +
                '}';
    }
}
