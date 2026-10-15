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
     protected abstract Map<ResourceType,Integer> bas

 }
