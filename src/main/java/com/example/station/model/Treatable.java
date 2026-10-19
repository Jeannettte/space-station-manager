package com.example.station.model;

// someone we can be treaten
public interface Treatable {
    void heal(int amount);
    int getHealth();
    boolean isAlive();
}
