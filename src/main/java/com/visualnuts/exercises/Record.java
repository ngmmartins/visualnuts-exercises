package com.visualnuts.exercises;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Represents an entry of a Country and it's languages
 */
@Getter
@Setter
public class Record {

    private String country;
    private List<String> languages;

}
