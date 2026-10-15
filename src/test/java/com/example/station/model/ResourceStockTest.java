package com.example.station.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.station.model.ResourceType.ENERGY;
import static com.example.station.model.ResourceType.WATER;
import static com.example.station.model.ResourceType.OXYGEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ResourceStockTest {
    private ResourceStock stock;

    @BeforeEach
    void setUp(){
        stock=new ResourceStock(100);
    }
    @Test
    void shouldStartEmpty(){
        for (ResourceType type : ResourceType.values()) {
            assertEquals(0, stock.get(type));
        }
    }

}
