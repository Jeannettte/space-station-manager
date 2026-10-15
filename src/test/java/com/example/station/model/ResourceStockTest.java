package com.example.station.model;
import com.example.station.exception.InsufficientResourceException;
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

    //First of all, create a new inventory with a maximum capacity of 100
    @BeforeEach
    void setUp(){
        stock=new ResourceStock(100);
    }

    //// we start with no resources
    @Test
    void shouldStartEmpty(){
        for (ResourceType type : ResourceType.values()) {
            assertEquals(0, stock.get(type));
        }
    }

    //Check that we can add a resource
    @Test
    void shouldAddResource(){
        int added=stock.add(OXYGEN,40);
        assertEquals(40, added);
        assertEquals(40, stock.get(OXYGEN));
    }

    // Check the capacity's max
    @Test
    void shouldCapAtMaxCapacity(){
        stock.add(OXYGEN,80);
        int added=stock.add(OXYGEN,80);
        assertEquals(20,added);
        assertEquals(100,stock.get(OXYGEN));
    }

    //Check that we can consume correctly resource
    @Test
    void shouldConsumeResource(){
        stock.add(ENERGY,50);
        stock.consume(ENERGY,30);
        assertEquals(20,stock.get(ENERGY));
    }

    //Ckeck that we can consume all resource
    @Test
    void shouldConsumeExactlyAllStock(){
        stock.add(WATER,25);
        stock.consume(WATER,25);
        assertEquals(0,stock.get(WATER));
    }

    //Check that you can't consume more than you have
    @Test
    void shouldThrowWhenConsumingMoreThanAvailable(){
        stock.add(ENERGY,10);
        InsufficientResourceException ex=assertThrows(
                InsufficientResourceException.class, () -> stock.consume(ENERGY, 11));
        assertEquals(ENERGY, ex.getResource());
        assertEquals(11,ex.getRequested());
        assertEquals(10,ex.getAvailable());
        assertEquals(10, stock.get(ENERGY));
    }
    //Check you can't have negative amount
    @Test
    void shouldRejectNonPositiveAmounts(){
        assertThrows(IllegalArgumentException.class, () -> stock.add(OXYGEN,0));
        assertThrows(IllegalArgumentException.class, () -> stock.add(OXYGEN,-5));
        assertThrows(IllegalArgumentException.class, () -> stock.consume(OXYGEN,-1));
    }

    //Check that you only keep the right capacity
    @Test
    void shouldRejectInvalidCapacity(){
        assertThrows(IllegalArgumentException.class, ()->new ResourceStock(0));
    }

    // Check that you have enough capacity for an exchange
    @Test
    void shouldReportWhetherEnoughResourceIsAvailable(){
        stock.add(WATER,10);
        assertTrue(stock.hasEnough(WATER,10));
        assertFalse(stock.hasEnough(WATER,11));
    }

    //keep the independent resource
    @Test
    void shouldKeepResourceIndependent(){
        stock.add(OXYGEN,30);
        assertEquals(0,stock.get(ENERGY));
    }

}
