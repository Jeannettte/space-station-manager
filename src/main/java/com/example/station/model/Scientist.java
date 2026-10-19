package com.example.station.model;

import java.util.List;

//Increases a lab's output if assigned there.
public class Scientist extends CrewMember{
    private final int researchBonus;

    public Scientist(String name, int health, int morale, int researchBonus) {
        super(name, health, morale);
        this.researchBonus = requirePositive("Research bonus", researchBonus);
    }

    @Override
    public Role getRole() {
        return Role.SCIENTIST;
    }

    @Override
    protected void performWork(List<CrewMember> team) {
        if (getAssignedModule().orElse(null) instanceof Laboratory lab) {
            lab.addResearchBonus(researchBonus);
        }
    }
}
