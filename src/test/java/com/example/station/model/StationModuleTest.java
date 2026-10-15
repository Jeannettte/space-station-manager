package com.example.station.model;

import static com.example.station.model.ResourceType.ENERGY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.station.exception.ModuleOfflineException;
import org.junit.jupiter.api.Test;

public class StationModuleTest {
    //check that you can create a solar panel
    private SolarPanelArray panelWithIntegrity(int integrity) {
        return new SolarPanelArray("Solar A", integrity, 20);
    }

    //a panel at 100% integrity produces all its energy
    @Test
    void shouldProduceFullOutputWhenIntact() {
        assertEquals(20, panelWithIntegrity(100).produce().get(ENERGY));
    }

    //At 50 of integrity, it only produces half
    @Test
    void shouldScaleProductionWithIntegrity() {
        assertEquals(10, panelWithIntegrity(50).produce().get(ENERGY));
    }

    //a solar panel doesn't consume any resources
    @Test
    void shouldNotConsumeAnything() {
        assertTrue(panelWithIntegrity(100).consumption().isEmpty());
    }

    //applyDamage(30) reduces integrity from 100 to 70
    @Test
    void shouldReduceIntegrityOnDamage() {
        SolarPanelArray panel = panelWithIntegrity(100);

        panel.applyDamage(30);

        assertEquals(70, panel.getIntegrity());
    }

    // huge damage blocks integrity at 0 and puts the module OFFLINE
    @Test
    void shouldNotGoBelowZeroIntegrity() {
        SolarPanelArray panel = panelWithIntegrity(20);

        panel.applyDamage(500);

        assertEquals(0, panel.getIntegrity());
        assertEquals(ModuleStatus.OFFLINE, panel.getStatus());
    }

    //a repair that's too strong stops at 100
    @Test
    void shouldNotExceedMaxIntegrityOnRepair() {
        SolarPanelArray panel = panelWithIntegrity(90);

        panel.repair(50);

        assertEquals(100, panel.getIntegrity());
    }

    //a module at 0 can be repaired, it becomes operational again
    @Test
    void shouldRepairAnOfflineModule() {
        SolarPanelArray panel = panelWithIntegrity(0);

        panel.repair(40);

        assertTrue(panel.isOperational());
        assertEquals(ModuleStatus.DAMAGED, panel.getStatus());
    }

    //the status correctly follows the thresholds
    @Test
    void shouldComputeStatusFromIntegrity() {
        assertEquals(ModuleStatus.ONLINE, panelWithIntegrity(100).getStatus());
        assertEquals(ModuleStatus.ONLINE, panelWithIntegrity(50).getStatus());
        assertEquals(ModuleStatus.DAMAGED, panelWithIntegrity(49).getStatus());
        assertEquals(ModuleStatus.OFFLINE, panelWithIntegrity(0).getStatus());
    }

    //a stopped module that tries to produce raises ModuleOfflineException
    @Test
    void shouldThrowWhenOfflineModuleProduces() {
        SolarPanelArray panel = panelWithIntegrity(0);

        assertFalse(panel.isOperational());
        assertThrows(ModuleOfflineException.class, panel::produce);
    }

    // an integrity outside 0-100, an empty name, or a negative product throw IllegalArgumentException
    @Test
    void shouldRejectInvalidConstructorArguments() {
        assertThrows(IllegalArgumentException.class, () -> panelWithIntegrity(101));
        assertThrows(IllegalArgumentException.class, () -> panelWithIntegrity(-1));
        assertThrows(IllegalArgumentException.class, () -> new SolarPanelArray(" ", 100, 20));
        assertThrows(IllegalArgumentException.class, () -> new SolarPanelArray("Solar A", 100, -5));
    }

    // Damage or a distribution of 0 or negative numbers is not allowed
    @Test
    void shouldRejectNonPositiveDamageAndRepair() {
        SolarPanelArray panel = panelWithIntegrity(100);

        assertThrows(IllegalArgumentException.class, () -> panel.applyDamage(0));
        assertThrows(IllegalArgumentException.class, () -> panel.repair(-3));
    }
}