package com.example.station.model;
import java.util.Map;

//Produces research points, which count towards the final score.
public class Laboratory extends StationModule {
    private final int researchOutput;
    private final int energyCost;

    public Laboratory(String name, int integrity, int researchOutput, int energyCost) {
        super(name, integrity);
        this.researchOutput = requireNonNegative("Research output", researchOutput);
        this.energyCost = requireNonNegative("Energy cost", energyCost);
    }

    @Override
    protected Map<ResourceType, Integer> baseProduction() {
        return Map.of(ResourceType.RESEARCH, researchOutput);
    }

    @Override
    public Map<ResourceType, Integer> consumption() {
        return Map.of(ResourceType.ENERGY, energyCost);
    }
}
