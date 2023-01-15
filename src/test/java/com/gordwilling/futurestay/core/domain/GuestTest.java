package com.gordwilling.futurestay.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuestTest implements ValidationTest {

    @Test
    @DisplayName("Validation should fail when the emailAddress is not formatted correct")
    void emailAddressValidationFails() {
        var guest = new Guest("invalid email address");
        var violations = validator.validate(guest);
        assertEquals(1, violations.size());
        var violation = violations.stream().toList().get(0);
        assertEquals("emailAddress", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Validation should pass when the emailAddress is formatted correctly")
    void cityValidationPasses() {
        var guest = new Guest("email@address.com");
        var violations = validator.validate(guest);
        assertEquals(0, violations.size());
    }
}
