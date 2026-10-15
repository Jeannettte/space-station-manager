package com.example.station.exception;
import com.example.station.model.ResourceType;

public class InsufficientResourceException extends RuntimeException {
    private final ResourceType resource;
    private final int requested;
    private final int available;

    public InsufficientResourceException(ResourceType resource, int requested, int available){
        super("Not enough" + resource + ": requested" + requested + "available" + available);
        this.resource=resource;
        this.requested=requested;
        this.available=available;
    }
    public ResourceType getResource (){
        return resource;
    }
    public int getRequested (){
        return requested;
    }
    public int getAvailable (){
        return available;
    }


}
