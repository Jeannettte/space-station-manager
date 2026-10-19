package com.example.station.model;

import static com.example.station.model.ResourceType.ENERGY;
import static com.example.station.model.ResourceType.FOOD;
import static com.example.station.model.ResourceType.OXYGEN;
import static com.example.station.model.ResourceType.RESEARCH;
import static com.example.station.model.ResourceType.WATER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.station.exception.ModuleOfflineException;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ModuleTypeTest {
    //Check that generator consumes energy and water and create oxygen
    @Test
    void oxygenGeneratorShouldProduceOxygenAndConsumeEnergyAndWater() {
        OxygenGenerator generator = new OxygenGenerator("O2", 100, 30, 10, 5);

        assertEquals(Map.of(OXYGEN, 30), generator.produce());
        assertEquals(Map.of(ENERGY, 10, WATER, 5), generator.consumption());
    }

    //Check that recycler produce water and consume energy
    @Test
    void waterRecyclerShouldProduceWaterAndConsumeEnergy() {
        WaterRecycler recycler = new WaterRecycler("H2O", 100, 20, 8);

        assertEquals(Map.of(WATER, 20), recycler.produce());
        assertEquals(Map.of(ENERGY, 8), recycler.consumption());
    }

    //check that greenhous create food and oxygen
    @Test
    void greenhouseShouldProduceFoodAndOxygen() {
        Greenhouse greenhouse = new Greenhouse("Farm", 100, 15, 5, 6, 4);

        assertEquals(Map.of(FOOD, 15, OXYGEN, 5), greenhouse.produce());
        assertEquals(Map.of(WATER, 6, ENERGY, 4), greenhouse.consumption());
    }

    //check that laboratoty product positives points and consume energy
    @Test
    void laboratoryShouldProduceResearchAndConsumeEnergy() {
        Laboratory lab = new Laboratory("Lab", 100, 10, 12);

        assertEquals(Map.of(RESEARCH, 10), lab.produce());
        assertEquals(Map.of(ENERGY, 12), lab.consumption());
    }

    //Check when the module is damaged, it product less but consume the same energy
    @Test
    void damagedModuleShouldProduceLessButConsumeTheSame() {
        Greenhouse greenhouse = new Greenhouse("Farm", 50, 20, 10, 6, 4);

        assertEquals(Map.of(FOOD, 10, OXYGEN, 5), greenhouse.produce());
        assertEquals(Map.of(WATER, 6, ENERGY, 4), greenhouse.consumption());
    }

    //with polymorphism call, each module has his product
    @Test
    void polymorphicCallShouldGiveEachModuleItsOwnProduction() {
        StationModule solar = new SolarPanelArray("Solar", 100, 25);
        StationModule lab = new Laboratory("Lab", 100, 10, 12);

        assertEquals(Map.of(ENERGY, 25), solar.produce());
        assertEquals(Map.of(RESEARCH, 10), lab.produce());
    }

    //when the module is offline, it can't product
    @Test
    void offlineModuleShouldNotProduce() {
        WaterRecycler recycler = new WaterRecycler("H2O", 0, 20, 8);

        assertThrows(ModuleOfflineException.class, recycler::produce);
    }

    //check that all negativ parameters is rejected
    @Test
    void shouldRejectNegativeParameters() {
        assertThrows(IllegalArgumentException.class, () -> new OxygenGenerator("O2", 100, -1, 10, 5));
        assertThrows(IllegalArgumentException.class, () -> new WaterRecycler("H2O", 100, 20, -8));
        assertThrows(IllegalArgumentException.class, () -> new Greenhouse("Farm", 100, 15, 5, -6, 4));
        assertThrows(IllegalArgumentException.class, () -> new Laboratory("Lab", 100, 10, -12));
    }
}
