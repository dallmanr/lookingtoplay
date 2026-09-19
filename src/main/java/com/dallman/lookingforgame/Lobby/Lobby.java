package com.dallman.lookingforgame.Lobby;

import com.dallman.lookingforgame.Game.Game;
import com.dallman.lookingforgame.User.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="lobby")
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    /*
     * There are many lobbies to one owner, an owner will have historical lobbies but only one that is currently OPEN
     * We do not cascade DELETE because a lobby owner deleting a lobby should not delete their account.
     * Use Lazy loading because whilst they may only have 1 currently OPEN lobby, they may have a large amount of historical ones
    */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,  CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="owner_id")
    private User userOwner;

    /*
     * A lobby can have many users, who are not owners, and owners can have many lobbies, including historical ones.
     * We do not cascade DELETE because if a lobby is deleted, the players who joined should NOT be deleted.
     * A set means no duplicates so a user cannot join the same lobby > once
    */
    @ManyToMany(cascade = {CascadeType.PERSIST,  CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name="lobby_user",
            joinColumns = @JoinColumn(name="lobby_id"),
            inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<User> players;

    /*
    * There can be many lobbies to one game as other users can create their own instances
    * A lobby is only ever for one game.
    * Use LAZY load because we don't want to retrieve all historical lobbies
    * Do not cascade DELETE because deleting a lobby should not delete the game
    * */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,  CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="game_id")
    private Game game;

    /*
    * Only valid statuses are: OPEN, CLOSED, IN_PROGRESS
    * */
    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private LobbyStatus lobbyStatus;

    @Column(name="lobby_info")
    private String lobbyInfo;

    public Lobby() {
    }

    public Lobby(User userOwner, String name, String lobbyInfo) {
        this.userOwner = userOwner;
        this.lobbyInfo = lobbyInfo;
        this.name = name;
        this.lobbyStatus = LobbyStatus.OPEN;
        this.players = new HashSet<>();
    }

    public User getOwner() {
        return userOwner;
    }

    public void setOwner(User owner) {
        this.userOwner = owner;
    }

    public Set<User> getPlayers() {

        if (players == null) {
            players = new HashSet<>();
        }

        return players;
    }

    public void setPlayers(Set<User> players) {
        this.players = players;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public LobbyStatus getLobbyStatus() {
        return lobbyStatus;
    }

    public void setLobbyStatus(LobbyStatus lobbyStatus) {
        this.lobbyStatus = lobbyStatus;
    }

    public String getLobbyInfo() {
        return lobbyInfo;
    }

    public void setLobbyInfo(String lobbyInfo) {
        this.lobbyInfo = lobbyInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLobbyName() {
        return name;
    }

    public void setLobbyName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public String getOwnerName() {
        return this.getOwner().getUsername();
    }

    @Override
    public String toString() {
        return "Lobby{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + userOwner +
                ", game=" + game +
                ", lobbyStatus=" + lobbyStatus +
                ", lobbyInfo='" + lobbyInfo + '\'' +
                '}';
    }
}
