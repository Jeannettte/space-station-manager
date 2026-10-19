package com.example.station.model;
import java.util.List;

//Lifts the spirits of the entire crew still alive.
public class Commander extends CrewMember{
    private final int moraleBoost;

    public Commander(String name, int health, int morale, int moraleBoost) {
        super(name, health, morale);
        this.moraleBoost = requirePositive("Morale boost", moraleBoost);
    }

    @Override
    public Role getRole() {
        return Role.COMMANDER;
    }

    @Override
    protected void performWork(List<CrewMember> team) {
        team.stream()
                .filter(CrewMember::isAlive)
                .forEach(member -> member.changeMorale(moraleBoost));
    }
}
