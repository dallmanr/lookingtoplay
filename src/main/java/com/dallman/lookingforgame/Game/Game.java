package com.dallman.lookingforgame.Game;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    private int cover;

    private String summary;

    private String url;

    private int gameType;

//    private List<Platform> platforms;


    public Game() {
    }

    public Game(int id, String name, int cover, String summary, String url, int gameType) {
        this.id = id;
        this.name = name;
        this.cover = cover;
        this.summary = summary;
        this.url = url;
        this.gameType = gameType;
//        this.platforms = platforms;
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

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
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

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cover=" + cover +
                '}';
    }
}
