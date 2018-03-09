package net.restapp.Validator;

public class RegexpPatterns {

    public static final String patternStringWithNumbersLettersAndDash="^\\s*[\\da-zA-Z][\\da-zA-Z\\s][\\d-a-z-A-Z\\s]*$";
    public static final String messageStringWithNumbersLettersAndDash="This field must contains only numbers, letters and dash";

    public static final String patternNumbers="^[\\0-9][\\0-9.0-9]";
    public static final String messageNumbers="This field must contains only numbers and one point";

}