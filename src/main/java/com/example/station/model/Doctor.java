package com.example.station.model;

import java.util.Comparator;
import java.util.List;

//Take care of the most injured crew member.
public class Doctor extends CrewMember {
    private final int healPower;

    public Doctor(String name, int health, int morale, int healPower) {
        super(name, health, morale);
        this.healPower = requirePositive("Heal power", healPower);
    }

    @Override
    public Role getRole() {
        return Role.DOCTOR;
    }

    @Override
    protected void performWork(List<CrewMember> team) {
        team.stream()
                .filter(CrewMember::isAlive)
                .filter(member -> member.getHealth() < MAX_HEALTH)
                .min(Comparator.comparingInt(CrewMember::getHealth))
                .ifPresent(patient -> patient.heal(healPower));
    }

}
