package com.example.station.model;
 import com.example.station.exception.ModuleOfflineException;

 import java.util.EnumMap;
 import java.util.Map;

 //Station part: has integrity, produces and consumes resources.
public abstract class StationModule implements Repairable,ResourceProducer,ResourceConsumer{
    public static final int MAX_INTEGRITY =100;
    public static final int DAMAGED_BELOW=50;

    private final String name;
    private int integrity;

    protected StationModule(String name, int integrity){
        if (name==null||name.isBlank()){
            throw new IllegalArgumentException("Module name must not be blank");
        }
        if (integrity<0 || integrity>MAX_INTEGRITY) {
            throw new IllegalArgumentException("Integrity must be between 0 and 100 : ");
        }
        this.name=name;
        this.integrity=integrity;
    }

    //Basic production, at full integrity. Each subclass defines its own.
     protected abstract Map<ResourceType,Integer> baseProduction();

     @Override
     public final Map<ResourceType, Integer> produce() {
         if (!isOperational()) {
             throw new ModuleOfflineException(name + " is offline and cannot produce");
         }
         Map<ResourceType, Integer> result = new EnumMap<>(ResourceType.class);
         baseProduction().forEach((type, amount) -> result.put(type, amount * integrity / MAX_INTEGRITY));
         return result;
     }

     public void applyDamage(int amount) {
         requirePositive(amount);
         integrity = Math.max(0, integrity - amount);
     }

     @Override
     public void repair(int amount) {
         requirePositive(amount);
         integrity = Math.min(MAX_INTEGRITY, integrity + amount);
     }

     public ModuleStatus getStatus() {
         if (integrity == 0) {
             return ModuleStatus.OFFLINE;
         }
         return integrity < DAMAGED_BELOW ? ModuleStatus.DAMAGED : ModuleStatus.ONLINE;
     }

     public boolean isOperational() {
         return getStatus() != ModuleStatus.OFFLINE;
     }

     @Override
     public int getIntegrity() {
         return integrity;
     }

     public String getName() {
         return name;
     }

     private void requirePositive(int amount) {
         if (amount <= 0) {
             throw new IllegalArgumentException("Amount must be positive: " + amount);
         }
     }
 }
