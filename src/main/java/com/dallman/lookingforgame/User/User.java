package com.dallman.lookingforgame.User;

import com.dallman.lookingforgame.Authority.Authority;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name="username", nullable = false, length = 50)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private int enabled;

    @Column(name="email_address")
    private String emailAddress;

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
}
