package com.example.station.model;
import java.util.Map;

//Makes oxygen from energy and water
public class OxygenGenerator extends StationModule {
    private final int oxygenOutput;
    private final int energyCost;
    private final int waterCost;

    public OxygenGenerator(String name, int integrity, int oxygenOutput, int energyCost, int waterCost) {
        super(name, integrity);
        this.oxygenOutput = requireNonNegative("Oxygen output", oxygenOutput);
        this.energyCost = requireNonNegative("Energy cost", energyCost);
        this.waterCost = requireNonNegative("Water cost", waterCost);
    }

    @Override
    protected Map<ResourceType, Integer> baseProduction() {
        return Map.of(ResourceType.OXYGEN, oxygenOutput);
    }

    @Override
    public Map<ResourceType, Integer> consumption() {
        return Map.of(ResourceType.ENERGY, energyCost, ResourceType.WATER, waterCost);
    }
}
