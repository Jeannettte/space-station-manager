package com.example.station.model;
import java.util.Map;

//Produces research points, which count towards the final score.
public class Laboratory extends StationModule {
    private final int researchOutput;
    private final int energyCost;
    private int researchBonus;

    public Laboratory(String name, int integrity, int researchOutput, int energyCost) {
        super(name, integrity);
        this.researchOutput = requireNonNegative("Research output", researchOutput);
        this.energyCost = requireNonNegative("Energy cost", energyCost);
    }

    public void addResearchBonus(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Bonus must be positive: " + amount);
        }
        researchBonus += amount;
    }
    //To be called at the end of the turn: the bonus only applies for the current turn.
    public void resetResearchBonus() {
        researchBonus = 0;
    }

    @Override
    protected Map<ResourceType, Integer> baseProduction() {
        return Map.of(ResourceType.RESEARCH, researchOutput + researchBonus);
    }

    @Override
    public Map<ResourceType, Integer> consumption() {
        return Map.of(ResourceType.ENERGY, energyCost);
    }



}
