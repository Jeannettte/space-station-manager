package com.example.station;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/** Test de fumée : vérifie que JUnit est bien configuré et que l'application démarre. */
class ApplicationTest {

    @Test
    void shouldStartWithoutThrowing() {
        assertDoesNotThrow(() -> Application.main(new String[0]));
    }
}
