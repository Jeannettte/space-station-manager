package com.example.station.model;
import java.util.Map;

//Solar panel field: produces energy, doesn't consume any
public class SolarPanelArray extends StationModule {
    private final int energyOutput;

    public SolarPanelArray(String name, int integrity, int energyOutput) {
        super(name, integrity);
        if (energyOutput < 0) {
            throw new IllegalArgumentException("Energy output must not be negative: " + energyOutput);
        }
        this.energyOutput = energyOutput;
    }

    @Override
    protected Map<ResourceType, Integer> baseProduction() {
        return Map.of(ResourceType.ENERGY, energyOutput);
    }

    @Override
    public Map<ResourceType, Integer> consumption() {
        return Map.of();
    }
}
