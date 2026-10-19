package com.example.station.model;

import static com.example.station.model.ResourceType.RESEARCH;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CrewRoleTest {
//The engineer has to fix the module assigned to him
    @Test
    void engineerShouldRepairItsAssignedModule() {
        Engineer engineer = new Engineer("Ava", 100, 80, 15);
        SolarPanelArray panel = new SolarPanelArray("Solar", 40, 20);
        engineer.assignTo(panel);

        engineer.work(List.of(engineer));

        assertEquals(55, panel.getIntegrity());
    }

    //unassigned engineer should do nothing
    @Test
    void unassignedEngineerShouldDoNothing() {
        Engineer engineer = new Engineer("Ava", 100, 80, 15);

        assertDoesNotThrow(() -> engineer.work(List.of(engineer)));
    }

    //The doctor has to treat the most injured limb while it's still alive.
    @Test
    void doctorShouldHealOnlyTheMostInjuredLivingMember() {
        Doctor doctor = new Doctor("Doc", 100, 80, 20);
        Engineer hurt = new Engineer("A", 60, 80, 10);
        Engineer hurter = new Engineer("B", 30, 80, 10);
        Engineer healthy = new Engineer("C", 100, 80, 10);
        Engineer dead = new Engineer("D", 100, 80, 10);
        dead.takeDamage(100);

        doctor.work(List.of(doctor, hurt, hurter, healthy, dead));

        assertEquals(60, hurt.getHealth());
        assertEquals(50, hurter.getHealth());
        assertEquals(100, healthy.getHealth());
        assertEquals(0, dead.getHealth());
    }

    //Scientist have to boost the laboratory
    @Test
    void scientistShouldBoostItsLaboratory() {
        Scientist scientist = new Scientist("Sam", 100, 80, 5);
        Laboratory lab = new Laboratory("Lab", 100, 10, 12);
        scientist.assignTo(lab);

        scientist.work(List.of(scientist));
        assertEquals(Map.of(RESEARCH, 15), lab.produce());

        lab.resetResearchBonus();
        assertEquals(Map.of(RESEARCH, 10), lab.produce());
    }

    //scientist Outside A Laboratory Should Have No Effect
    @Test
    void scientistOutsideALaboratoryShouldHaveNoEffect() {
        Scientist scientist = new Scientist("Sam", 100, 80, 5);
        scientist.assignTo(new SolarPanelArray("Solar", 100, 20));

        assertDoesNotThrow(() -> scientist.work(List.of(scientist)));
    }

    //commander have to raise the moral of living member
    @Test
    void commanderShouldRaiseMoraleOfLivingMembersOnly() {
        Commander commander = new Commander("Cmd", 100, 90, 20);
        Engineer other = new Engineer("A", 100, 50, 10);
        Engineer dead = new Engineer("D", 100, 50, 10);
        dead.takeDamage(100);

        commander.work(List.of(commander, other, dead));

        assertEquals(100, commander.getMorale()); // plafonné
        assertEquals(70, other.getMorale());
        assertEquals(50, dead.getMorale());
    }

    //each  role do only his role
    @Test
    void eachRoleShouldReportItsRole() {
        assertEquals(Role.ENGINEER, new Engineer("A", 100, 80, 1).getRole());
        assertEquals(Role.DOCTOR, new Doctor("B", 100, 80, 1).getRole());
        assertEquals(Role.SCIENTIST, new Scientist("C", 100, 80, 1).getRole());
        assertEquals(Role.COMMANDER, new Commander("D", 100, 80, 1).getRole());
    }
}
