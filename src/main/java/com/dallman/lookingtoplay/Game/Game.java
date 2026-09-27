package com.dallman.lookingtoplay.Game;


import com.dallman.lookingtoplay.Lobby.Lobby;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="game_id")
    private int id;

    @Column(name="igdb_id")
    @NotNull
    private int igdbId;

    @Column(name="name")
    @NotNull
    private String name;

    @Column(name="cover_id")
    private Integer coverId;

    @Column(name="summary")
    private String summary;

    @Column(name="first_release_date")
    private Long firstReleaseDate;

    @Column(name="rating")
    private Double rating;

    @Column(name="url")
    private String url;

    @Column(name="release_status")
    private Integer releaseStatus;

    @Column(name="game_type")
    private Integer gameType;

    @OneToMany(mappedBy = "game")
    private List<Lobby> lobbies;

    @ManyToMany(mappedBy="gamesList")
    private List<Platform> platforms;


    public Game() {
    }

    public Game(String name, String summary, String url, Integer gameType) {
        this.name = name;
        this.summary = summary;
        this.url = url;
        this.gameType = gameType;
    }

    public Game(int igdbId, String name, Integer coverId, String summary, Long firstReleaseDate, Double rating, String url, Integer releaseStatus, Integer gameType, List<Platform> platforms) {
        this.igdbId = igdbId;
        this.name = name;
        this.coverId = coverId;
        this.summary = summary;
        this.firstReleaseDate = firstReleaseDate;
        this.rating = rating;
        this.url = url;
        this.releaseStatus = releaseStatus;
        this.gameType = gameType;
        this.platforms = platforms;
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

    public Integer getCoverId() {
        return coverId;
    }

    public void setCoverId(Integer coverId) {
        this.coverId = coverId;
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

    public Integer getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public Integer getIgdbId() {
        return igdbId;
    }

    public void setIgdbId(int igdbId) {
        this.igdbId = igdbId;
    }

    public Long getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(Long firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Integer releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public void setGameType(Integer gameType) {
        this.gameType = gameType;
    }

    public List<Lobby> getLobbies() {
        return lobbies;
    }

    public void setLobbies(List<Lobby> lobbies) {
        this.lobbies = lobbies;
    }

    public List<Platform> getPlatforms() {
        if (this.platforms == null) {
            this.platforms = new ArrayList<>();
        }
        return platforms;
    }

    public void addPlatform(Platform platform) {
        getPlatforms().add(platform);

        if (!platform.getGamesList().contains(this)) {
            platform.getGamesList().add(this);
        }
    }

    public void removePlatform(Platform platform) {
        getPlatforms().remove(platform);
        if (platform.getGamesList().contains(this)) {
            platform.getGamesList().remove(this);
        }
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
                "name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", firstReleaseDate=" + firstReleaseDate +
                ", rating=" + rating +
                ", url='" + url + '\'' +
                ", releaseStatus=" + releaseStatus +
                '}';
    }
}
