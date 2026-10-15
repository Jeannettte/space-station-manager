package com.example.station.model;

import com.example.station.exception.InsufficientResourceException;
import java.util.EnumMap; //Specialized implementation of the map interface designed specifically for enum keys
import java.util.Map; //Interface that represents a collection of objects

// This will be the station's resource store: it contains the amount of oxygen, energy, water, food, and research points
// modules deposit what they produce and take what they consume from it
public class ResourceStock {

    private final int maxCapacity;
    private final Map<ResourceType,Integer> amounts=new EnumMap<>(ResourceType.class);

    public ResourceStock (int maxCapacity){
        if (maxCapacity <=0){
            throw new IllegalArgumentException("Max capacity must be positive" + maxCapacity);
        }
        this.maxCapacity=maxCapacity;
        for (ResourceType type:ResourceType.values()){
            amounts.put(type,0);
        }
    }
    //Add a resource, capped at capacity. Returns the amount added
    public int add(ResourceType type, int amount){
        requirePositive(amount);
        int current=amounts.get(type);
        int added=Math.min(amount,maxCapacity-current);
        amounts.put(type,current+added);
        return added;
    }

    // Removes a resource. The stock remains unchanged if the quantity is insufficient.
    public void consume(ResourceType type, int amount){
        requirePositive(amount);
        int current=amounts.get(type);
        if (current<amount){
            throw new InsufficientResourceException(type, amount, current);
        }
        amounts.put(type, current-amount);
    }
    public boolean hasEnough(ResourceType type, int amount){
        return amounts.get(type)>=amount;
    }
    public int get(ResourceType type){
        return amounts.get(type);
    }
    public int getMaxCapacity(){
        return maxCapacity;
    }
    private void requirePositive(int amount){
        if (amount<=0){
            throw new IllegalArgumentException("Amount must be positive" + amount);
        }
    }
}
