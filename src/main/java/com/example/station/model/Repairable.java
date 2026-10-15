package com.example.station.model;

// something an engineer can fix
public interface Repairable {
    void repair(int amount);
    int getIntegrity ();
}
