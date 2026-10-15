package com.example.station;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class ApplicationTest {

    @Test
    void shouldStartWithoutThrowing() {
        assertDoesNotThrow(() -> Application.main(new String[0]));
    }
}
