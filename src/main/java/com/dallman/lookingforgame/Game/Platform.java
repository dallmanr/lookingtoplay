package com.dallman.lookingforgame.Game;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class Platform {


//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="id")
    private int id;

    private int platformId;

    public Platform(int id, int platformId) {
        this.id = id;
        this.platformId = platformId;
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

    @Override
    public String toString() {
        return "Platform{" +
                "id=" + id +
                ", platformId=" + platformId +
                '}';
    }
}
