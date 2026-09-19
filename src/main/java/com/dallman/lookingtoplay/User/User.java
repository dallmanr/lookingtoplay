package com.dallman.lookingtoplay.User;

import com.dallman.lookingtoplay.Authority.Authority;
import com.dallman.lookingtoplay.Lobby.Lobby;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private int id;

    @Column(name="username", nullable = false, length = 50)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private int enabled;

    @Column(name="email_address")
    private String emailAddress;

    @OneToMany(mappedBy = "userOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // Use Lazy because over time, a user may have a lot of historical lobbies!
    private List<Lobby> ownedLobbies;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "players")
    private List<Lobby> joinedLobbies;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Authority> roles;

    public User(String username, String password, int enabled, String emailAddress) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.emailAddress = emailAddress;
    }

    public User(String username, String password, int enabled, Collection<Authority> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<Lobby> getOwnedLobbies() {
        return ownedLobbies;
    }

    public void setOwnedLobbies(List<Lobby> ownedLobbies) {
        this.ownedLobbies = ownedLobbies;
    }

    public List<Lobby> getJoinedLobbies() {
        return joinedLobbies;
    }

    public void setJoinedLobbies(List<Lobby> joinedLobbies) {
        this.joinedLobbies = joinedLobbies;
    }

    public Collection<Authority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Authority> roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
