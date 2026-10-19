package com.example.station.model;
import java.util.Map;

//Recycle l'eau de la station au prix d'un peu d'énergie
public class WaterRecycler extends StationModule {
    private final int waterOutput;
    private final int energyCost;

    public WaterRecycler(String name, int integrity, int waterOutput, int energyCost) {
        super(name, integrity);
        this.waterOutput = requireNonNegative("Water output", waterOutput);
        this.energyCost = requireNonNegative("Energy cost", energyCost);
    }

    @Override
    protected Map<ResourceType, Integer> baseProduction() {
        return Map.of(ResourceType.WATER, waterOutput);
    }

    @Override
    public Map<ResourceType, Integer> consumption() {
        return Map.of(ResourceType.ENERGY, energyCost);
    }

}
