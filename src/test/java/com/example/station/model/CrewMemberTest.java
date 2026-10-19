package com.example.station.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.station.exception.CrewAssignmentException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CrewMemberTest {
    //creat an engineer
    private Engineer engineer(int health, int morale) {
        return new Engineer("Ava", health, morale, 15);
    }

    //if there is a damage, so the health reduce
    @Test
    void shouldReduceHealthOnDamage() {
        Engineer ava = engineer(100, 80);

        ava.takeDamage(30);

        assertEquals(70, ava.getHealth());
        assertTrue(ava.isAlive());
    }

    // the heath can't be negative so the personnage died
    @Test
    void shouldDieWhenHealthReachesZeroAndNeverGoNegative() {
        Engineer ava = engineer(20, 80);

        ava.takeDamage(500);

        assertEquals(0, ava.getHealth());
        assertFalse(ava.isAlive());
    }

    //the healt can't do over the maxHealth
    @Test
    void shouldNotHealAboveMaxHealth() {
        Engineer ava = engineer(90, 80);

        ava.heal(50);

        assertEquals(100, ava.getHealth());
    }

    //doctor can't heal a dead member
    @Test
    void shouldRefuseToHealADeadMember() {
        Engineer ava = engineer(100, 80);
        ava.takeDamage(100);

        assertThrows(IllegalStateException.class, () -> ava.heal(10));
    }

    //member keep moral between 0 and 100
    @Test
    void shouldKeepMoraleBetweenZeroAndMax() {
        Engineer ava = engineer(100, 90);

        ava.changeMorale(50);
        assertEquals(100, ava.getMorale());

        ava.changeMorale(-500);
        assertEquals(0, ava.getMorale());
    }

    // a dead member can't work
    @Test
    void deadMemberShouldNotWork() {
        Engineer ava = engineer(100, 80);
        ava.takeDamage(100);

        assertThrows(IllegalStateException.class, () -> ava.work(List.of(ava)));
    }

    //should assign and unassign module
    @Test
    void shouldAssignAndUnassignModule() {
        Engineer ava = engineer(100, 80);
        SolarPanelArray panel = new SolarPanelArray("Solar", 100, 20);
        assertTrue(ava.getAssignedModule().isEmpty());

        ava.assignTo(panel);
        assertSame(panel, ava.getAssignedModule().orElseThrow());

        ava.unassign();
        assertTrue(ava.getAssignedModule().isEmpty());
    }

    // it's no possible to assigne a dead member
    @Test
    void shouldNotAssignADeadMember() {
        Engineer ava = engineer(100, 80);
        ava.takeDamage(100);

        assertThrows(CrewAssignmentException.class,
                () -> ava.assignTo(new SolarPanelArray("Solar", 100, 20)));
    }

    // reject all invalid arguments
    @Test
    void shouldRejectInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> new Engineer(" ", 100, 80, 15));
        assertThrows(IllegalArgumentException.class, () -> new Engineer("Ava", 101, 80, 15));
        assertThrows(IllegalArgumentException.class, () -> new Engineer("Ava", 100, -1, 15));
        assertThrows(IllegalArgumentException.class, () -> new Engineer("Ava", 100, 80, 0));
        assertThrows(IllegalArgumentException.class, () -> engineer(100, 80).takeDamage(0));
    }
}
