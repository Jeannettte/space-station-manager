package com.example.station.model;

import java.util.Map;

// something that consumes resources every turn
public interface ResourceConsumer {
    Map<ResourceType,Integer> consumption();
}
