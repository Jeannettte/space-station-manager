package com.example.station.model;
import java.util.List;

//Fix the module it's assigned to.
public class Engineer extends CrewMember {
    private final int repairPower;

    public Engineer(String name, int health, int morale, int repairPower) {
        super(name, health, morale);
        this.repairPower = requirePositive("Repair power", repairPower);
    }

    @Override
    public Role getRole() {
        return Role.ENGINEER;
    }

    @Override
    protected void performWork(List<CrewMember> team) {
        getAssignedModule().ifPresent(module -> module.repair(repairPower));
    }
}
