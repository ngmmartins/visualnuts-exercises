package com.visualnuts.exercises;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VNExercises {

    /**
     * Write or describe an algorithm that prints the whole integer numbers to the console, start
     * from the number 1, and print all numbers going up to the number 100.
     * However, when the number is divisible by 3, do not print the number, but print the word
     * 'Visual'. If the number is divisible by 5, do not print the number, but print 'Nuts'. And for all
     * numbers divisible by both (eg: the number 15) the same, but print 'Visual Nuts'.
     * How will you keep this code safe from bugs? Show how you would guarantee that this code
     * keeps working when developers start making small feature adjustments. (Maybe we would
     * want to print the first 500 numbers, ...).
     *
     * @param startNumber the starting number of the stream
     * @param endNumber   the ending number of the stream
     */
    public static void exercise1(int startNumber, int endNumber) {
        if (startNumber >= endNumber) {
            throw new IllegalArgumentException("startNumber must be lower than endNumber.");
        }

        IntStream.rangeClosed(startNumber, endNumber).mapToObj(filter())
                .forEach(System.out::println);
    }

    /**
     * Returns the number of countries
     *
     * @param json the provided json String
     * @return the number of countries
     */
    public static int countCountries(String json) {
        List<Record> records = jsonToRecords(json);
        if (records.isEmpty()) {
            return 0;
        }

        return records.size();
    }

    /**
     * Finds the country that has the most languages and contains the language provided in the languageFilter
     *
     * @param json           the provided json String
     * @param languageFilter the language filter to be applied
     * @return the country that has the most languages
     */
    public static String findCountryWithMostLanguages(String json, String languageFilter) {
        List<Record> records = jsonToRecords(json);
        if (records.isEmpty()) {
            return null;
        }

        // sort the list so that the countries with most languages are on top
        records.sort(Collections.reverseOrder(Comparator.comparingInt(r -> r.getLanguages().size())));

        // since the list is ordered just pick the first record
        Optional<Record> countryOptional;
        if (languageFilter != null) {
            countryOptional = records.stream()
                    .filter(r -> r.getLanguages().contains(languageFilter))
                    .findFirst();
        } else {
            countryOptional = records.stream()
                    .findFirst();
        }

        return countryOptional.map(Record::getCountry).orElse(null);
    }

    /**
     * Counts the languages spoken in the given JSON
     *
     * @param json the provided json String
     * @return the number of languages spoken in the given JSON
     */
    public static int countLanguages(String json) {
        List<Record> records = jsonToRecords(json);
        if (records.isEmpty()) {
            return 0;
        }

        // using a Set to ensure uniqueness
        Set<String> uniqueLanguages = new HashSet<>();

        records.stream().
                map(Record::getLanguages).
                forEach(uniqueLanguages::addAll);

        return uniqueLanguages.size();
    }

    /**
     * Finds the country that has the most languages
     *
     * @param json the provided json String
     * @return the country that has the most languages
     */
    public static String findCountryWithMostLanguages(String json) {
        return findCountryWithMostLanguages(json, null);
    }

    /**
     * Finds the most common languages of all countries
     *
     * @param json the provided json String
     * @return a list of the most common languages
     */
    public static List<String> findMostCommonLanguages(String json) {
        List<Record> records = jsonToRecords(json);
        if (records.isEmpty()) {
            return null;
        }

        // create a map with the number of times a language is present
        Map<String, Long> languageCount = records.stream()
                .map(Record::getLanguages)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(l -> l, Collectors.counting()));

        List<String> commonLanguages = new ArrayList<>();
        AtomicLong previousValue = new AtomicLong();

        // extracts the language(s) that occur the most and puts in a list
        languageCount.keySet().forEach(key -> {
            long value = languageCount.get(key);
            if (value >= previousValue.get()) {
                commonLanguages.add(key);
                previousValue.set(value);
            }
        });

        return commonLanguages;
    }

    /**
     * Function that filters integers and returns it's correspondent value accordingly with:<br/>
     * - Number divisible by 3 -> 'Visual';<br/>
     * - Number divisible by 5 -> 'Nuts';<br/>
     * - Number divisible by 3 and 5 -> 'Visual Nuts';<br/>
     * - All other cases return the number.<br/>
     *
     * @return the correspondent value accordingly with the logic above.
     */
    private static IntFunction<? extends Serializable> filter() {
        return n -> n % 3 == 0 ? (n % 5 == 0 ? "Visual Nuts" : "Visual") : (n % 5 == 0 ? "Nuts" : n);
    }

    /**
     * Converts the json to a list of Records
     *
     * @param json the provided json String
     * @return list of Records
     */
    private static List<Record> jsonToRecords(String json) {
        if (json == null) {
            throw new InvalidJsonException("JSON string must be provided");
        }

        try {
            return Arrays.asList(new ObjectMapper().readValue(json, Record[].class));
        } catch (IOException e) {
            throw new InvalidJsonException(e);
        }

    }
}
