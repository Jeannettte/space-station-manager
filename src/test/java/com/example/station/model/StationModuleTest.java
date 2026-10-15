package com.example.station.model;

import static com.example.station.model.ResourceType.ENERGY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.station.exception.ModuleOfflineException;
import org.junit.jupiter.api.Test;

public class StationModuleTest {
    //check that you can create a solarpanel
    private SolarPanelArray panelWithIntegrity(int integrity) {
        return new SolarPanelArray("Solar A", integrity, 20);
    }

    //un panneau à 100d'intégrité produit toutes son énergie
    @Test
    void shouldProduceFullOutputWhenIntact() {
        assertEquals(20, panelWithIntegrity(100).produce().get(ENERGY));
    }

    //A 50 d'intégrité, il n'en produit que la moitié
    @Test
    void shouldScaleProductionWithIntegrity() {
        assertEquals(10, panelWithIntegrity(50).produce().get(ENERGY));
    }

    //un panneau solaire ne comsomme auncune ressource
    @Test
    void shouldNotConsumeAnything() {
        assertTrue(panelWithIntegrity(100).consumption().isEmpty());
    }

    //applyDamage(30) fait passe l'intégrité de 100 à 70
    @Test
    void shouldReduceIntegrityOnDamage() {
        SolarPanelArray panel = panelWithIntegrity(100);

        panel.applyDamage(30);

        assertEquals(70, panel.getIntegrity());
    }

    // des dégats énormes bloquent l'intégrité à 0 et mettent le module OFFLINE
    @Test
    void shouldNotGoBelowZeroIntegrity() {
        SolarPanelArray panel = panelWithIntegrity(20);

        panel.applyDamage(500);

        assertEquals(0, panel.getIntegrity());
        assertEquals(ModuleStatus.OFFLINE, panel.getStatus());
    }

    //une réparation trop forte s'arrete à 100
    @Test
    void shouldNotExceedMaxIntegrityOnRepair() {
        SolarPanelArray panel = panelWithIntegrity(90);

        panel.repair(50);

        assertEquals(100, panel.getIntegrity());
    }

    //un module à 0 peut être réparé, il redevient opérationnel
    @Test
    void shouldRepairAnOfflineModule() {
        SolarPanelArray panel = panelWithIntegrity(0);

        panel.repair(40);

        assertTrue(panel.isOperational());
        assertEquals(ModuleStatus.DAMAGED, panel.getStatus());
    }

    //le statut suit bien les seuils
    @Test
    void shouldComputeStatusFromIntegrity() {
        assertEquals(ModuleStatus.ONLINE, panelWithIntegrity(100).getStatus());
        assertEquals(ModuleStatus.ONLINE, panelWithIntegrity(50).getStatus());
        assertEquals(ModuleStatus.DAMAGED, panelWithIntegrity(49).getStatus());
        assertEquals(ModuleStatus.OFFLINE, panelWithIntegrity(0).getStatus());
    }

    //un module à l'arrêt qui essaie de produire lève ModuleOfflineException
    @Test
    void shouldThrowWhenOfflineModuleProduces() {
        SolarPanelArray panel = panelWithIntegrity(0);

        assertFalse(panel.isOperational());
        assertThrows(ModuleOfflineException.class, panel::produce);
    }

    // une intégrité hors de 0-100,un nom vide ou un produit négative lèvent IllegalArgumentException
    @Test
    void shouldRejectInvalidConstructorArguments() {
        assertThrows(IllegalArgumentException.class, () -> panelWithIntegrity(101));
        assertThrows(IllegalArgumentException.class, () -> panelWithIntegrity(-1));
        assertThrows(IllegalArgumentException.class, () -> new SolarPanelArray(" ", 100, 20));
        assertThrows(IllegalArgumentException.class, () -> new SolarPanelArray("Solar A", 100, -5));
    }

    // Des dégats ou une répartition de 0 ou négatifs sont refusés
    @Test
    void shouldRejectNonPositiveDamageAndRepair() {
        SolarPanelArray panel = panelWithIntegrity(100);

        assertThrows(IllegalArgumentException.class, () -> panel.applyDamage(0));
        assertThrows(IllegalArgumentException.class, () -> panel.repair(-3));
    }
}
