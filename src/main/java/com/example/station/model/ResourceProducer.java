package com.example.station.model;

import java.util.Map;
// something that factory resources every turn
public interface ResourceProducer {
    Map<ResourceType, Integer> produce();
}
