package com.visualnuts.exercises;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class VNExercisesTest {

    private static final String JSON = "[" +
            "{" +
            "\"country\": \"US\"," +
            "\"languages\": [ \"en\" ] }," +
            "{" +
            "\"country\": \"BE\"," +
            "\"languages\": [ \"nl\", \"fr\", \"de\" ]" +
            "}, {" +
            "\"country\": \"NL\"," +
            "\"languages\": [ \"nl\", \"fy\" ] }," +
            "{" +
            "\"country\": \"DE\", \"languages\": [ \"de\" ]" +
            "}, {" +
            "\"country\": \"ES\"," +
            "\"languages\": [ \"es\" ] }" +
            "]";

    @Test
    @DisplayName("Exercise 1 - Not much of a test - just here to execute and print the result.")
    void exercise1() {
        VNExercises.exercise1(0, 100);
    }

    @Test
    @DisplayName("returns the number of countries in the world")
    void countCountries() {
        assertEquals(5, VNExercises.countCountries(JSON));
    }

    @Test
    @DisplayName("to find the country with the highest number of official languages (includes language filter")
    void findCountryWithMostLanguages() {
        assertEquals("BE", VNExercises.findCountryWithMostLanguages(JSON));
        assertEquals("BE", VNExercises.findCountryWithMostLanguages(JSON, "de"));
        assertEquals("BE", VNExercises.findCountryWithMostLanguages(JSON, "nl"));
        assertEquals("NL", VNExercises.findCountryWithMostLanguages(JSON, "fy"));
    }

    @Test
    @DisplayName("that counts all the official languages spoken in the listed countries")
    void countLanguages() {
        assertEquals(6, VNExercises.countLanguages(JSON));
    }

    @Test
    @DisplayName("to find the most common official language(s), of all countries")
    void findMostCommonLanguages() {
        assertTrue(VNExercises.findMostCommonLanguages(JSON).containsAll(Arrays.asList("nl", "de")));
    }

    @Test
    @DisplayName("test sending series of invalid JSON strings")
    void testInvalidJson() {
        assertThrows(InvalidJsonException.class, () -> {
            VNExercises.countCountries(null);
        });

        assertThrows(InvalidJsonException.class, () -> {
            VNExercises.countCountries("");
        });

        assertThrows(InvalidJsonException.class, () -> {
            VNExercises.countCountries("{\"country\": \"DE\"}");
        });
    }

}