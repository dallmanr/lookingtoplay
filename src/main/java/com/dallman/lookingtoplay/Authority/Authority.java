package com.dallman.lookingtoplay.Authority;

import com.dallman.lookingtoplay.User.User;
import jakarta.persistence.*;

@Entity
@Table(name="authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username") // whatever FK column exists in authorities
    private User user;

    @Column(name="authority")
    private String authority;

    public Authority(int id, User user, String authority) {
        this.id = id;
        this.user = user;
        this.authority = authority;
    }

    public Authority() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
