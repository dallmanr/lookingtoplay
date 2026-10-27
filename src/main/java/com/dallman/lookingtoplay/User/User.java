package com.dallman.lookingtoplay.user;

import com.dallman.lookingtoplay.authority.Authority;
import com.dallman.lookingtoplay.lobby.Lobby;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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

    @Column(name="first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name="last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name="email_address")
    private String emailAddress;

    @OneToMany(mappedBy = "userOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // Use Lazy because over time, a user may have a lot of historical lobbies!
    private List<Lobby> ownedLobbies;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "players")
    private List<Lobby> joinedLobbies;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Authority> authorities;

    public User(String username, String password, boolean enabled, String emailAddress) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.emailAddress = emailAddress;
        this.authorities = new HashSet<Authority>();
    }

    public User(String username, String password, boolean enabled, Collection<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = authorities;
    }

    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
