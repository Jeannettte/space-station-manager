package com.example.station.model;
import com.example.station.exception.CrewAssignmentException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

// team member : health, morale,assigned module and work
public abstract class CrewMember implements Treatable{
    public static final int MAX_HEALTH = 100;
    public static final int MAX_MORALE = 100;

    private final String name;
    private int health;
    private int morale;
    private StationModule assignedModule;

    protected CrewMember(String name, int health, int morale) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Crew member name must not be blank");
        }
        if (health < 0 || health > MAX_HEALTH) {
            throw new IllegalArgumentException("Health must be between 0 and 100: " + health);
        }
        if (morale < 0 || morale > MAX_MORALE) {
            throw new IllegalArgumentException("Morale must be between 0 and 100: " + morale);
        }
        this.name = name;
        this.health = health;
        this.morale = morale;
    }
    public abstract Role getRole();
// What this role does during a turn. Each subclass defines its own.
    protected abstract void performWork(List<CrewMember> team);

    public final void work(List<CrewMember> team) {
        Objects.requireNonNull(team, "team must not be null");
        if (!isAlive()) {
            throw new IllegalStateException(name + " is dead and cannot work");
        }
        performWork(team);
    }
    public void takeDamage(int amount) {
        requirePositive("Damage", amount);
        health = Math.max(0, health - amount);
    }

    @Override
    public void heal(int amount) {
        requirePositive("Heal amount", amount);
        if (!isAlive()) {
            throw new IllegalStateException(name + " is dead and cannot be healed");
        }
        health = Math.min(MAX_HEALTH, health + amount);
    }
    // Changes the mood (positive or negative delta), limited between 0 and 100.
    public void changeMorale(int delta) {
        morale = Math.max(0, Math.min(MAX_MORALE, morale + delta));
    }

    public void assignTo(StationModule module) {
        Objects.requireNonNull(module, "module must not be null");
        if (!isAlive()) {
            throw new CrewAssignmentException(name + " is dead and cannot be assigned");
        }
        this.assignedModule = module;
    }

    public void unassign() {
        this.assignedModule = null;
    }

    public Optional<StationModule> getAssignedModule() {
        return Optional.ofNullable(assignedModule);
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public int getMorale() {
        return morale;
    }

    public String getName() {
        return name;
    }

    protected static int requirePositive(String label, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(label + " must be positive: " + value);
        }
        return value;
    }



}
