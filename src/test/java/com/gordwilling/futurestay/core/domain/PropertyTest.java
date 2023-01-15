package com.gordwilling.futurestay.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest implements ValidationTest {

    @Test
    @DisplayName("Validation should fail if the address is blank")
    void addressValidationFails() {
        var property = new Property("", "Imaginary City", State.AK, "12345-1234");
        var violations = validator.validate(property);
        assertEquals(1, violations.size());
        var violation = violations.stream().toList().get(0);
        assertEquals("address", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Validation should fail if the city is blank")
    void cityValidationFails() {
        var property = new Property("123 Fake Street", "", State.AK, "12345-1234");
        var violations = validator.validate(property);
        assertEquals(1, violations.size());
        var violation = violations.stream().toList().get(0);
        assertEquals("city", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Validation should fail if the state is null")
    void stateValidationFails() {
        var property = new Property("123 Fake Street", "Imaginary City", null, "12345-1234");
        var violations = validator.validate(property);
        assertEquals(1, violations.size());
        var violation = violations.stream().toList().get(0);
        assertEquals("state", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Validation should fail if the zipCode is invalid")
    void zipCodeValidationFails() {
        var property = new Property("123 Fake Street", "Imaginary City", State.AK, "");
        var violations = validator.validate(property);
        assertEquals(1, violations.size());
        var violation = violations.stream().toList().get(0);
        assertEquals("zipCode", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Validation should pass if all fields have valid values")
    void zipCodeValidationPassesWith9DigitValue() {
        var property = new Property("123 Fake Street", "Imaginary zipCode", State.AK, "12345-1234");
        var violations = validator.validate(property);
        assertEquals(0, violations.size());
    }

    @Test
    @DisplayName("The ZipCodeValidationPattern accepts a 5-digit zip code")
    void zipCodeValidationPatternAccepts5DigitZipCode() {
        var result = "12345".matches(Property.ZipCodeValidationPattern);
        assertTrue(result);
    }

    @Test
    @DisplayName("The ZipCodeValidationPattern accepts a 9 digit zip code")
    void zipCodeValidationPatternAccepts9DigitZipCode() {
        var result = "12345-1234".matches(Property.ZipCodeValidationPattern);
        assertTrue(result);
    }

    @Test
    @DisplayName("The ZipCodeValidationPattern rejects 4-digit zip code")
    void zipCodeValidationPatternRejects4DigitZipCode() {
        var result = "1234".matches(Property.ZipCodeValidationPattern);
        assertFalse(result);
    }

    @Test
    @DisplayName("The ZipCodeValidationPattern rejects 4-digit zip code")
    void zipCodeValidationPatternRejects9DigitZipCodeWithoutDash() {
        var result = "123451234".matches(Property.ZipCodeValidationPattern);
        assertFalse(result);
    }
}
