package com.example.station.model;
import java.util.Map;

//Cultive de la nourriture et dégage un peu d'oxygène
public class Greenhouse extends StationModule {
    private final int foodOutput;
    private final int oxygenOutput;
    private final int waterCost;
    private final int energyCost;

    public Greenhouse(String name, int integrity, int foodOutput, int oxygenOutput,
                      int waterCost, int energyCost) {
        super(name, integrity);
        this.foodOutput = requireNonNegative("Food output", foodOutput);
        this.oxygenOutput = requireNonNegative("Oxygen output", oxygenOutput);
        this.waterCost = requireNonNegative("Water cost", waterCost);
        this.energyCost = requireNonNegative("Energy cost", energyCost);
    }
    @Override
    protected Map<ResourceType, Integer> baseProduction() {
        return Map.of(ResourceType.FOOD, foodOutput, ResourceType.OXYGEN, oxygenOutput);
    }

    @Override
    public Map<ResourceType, Integer> consumption() {
        return Map.of(ResourceType.WATER, waterCost, ResourceType.ENERGY, energyCost);
    }
}
