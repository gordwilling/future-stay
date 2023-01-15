package com.gordwilling.futurestay.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingTest implements ValidationTest {

    private final Property validProperty = new Property("123 Fake Street", "Imaginary City", State.AK, "12345-1234");
    private final Guest validGuest = new Guest("email@address.com");
    private final LocalDateTime validCheckInDate = LocalDateTime.now().plusDays(1);
    private final LocalDateTime validCheckOutDate = LocalDateTime.now().plusDays(8);

    @Test
    @DisplayName("Validation should fail when the property value is null")
    void failsOnNullPropertyValue() {
        Booking booking = new Booking(null, validGuest, validCheckInDate, validCheckOutDate);
        var violations = validator.validate(booking);
        assertEquals(1, violations.size());
        var violation = violations.stream().toList().get(0);
        assertEquals("property", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Validation should fail when the guest value is null")
    void failsOnNullGuestValue() {
        Booking booking = new Booking(validProperty, null, validCheckInDate, validCheckOutDate);
        var violations = validator.validate(booking);
        assertEquals(1, violations.size());
        var violation = violations.stream().toList().get(0);
        assertEquals("guest", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Validation should fail when the checkIn date is null")
    void failsOnNullCheckinDate() {
        Booking booking = new Booking(validProperty, validGuest, null, validCheckOutDate);
        var violations = validator.validate(booking);
        assertEquals(1, violations.size());
        var violation = violations.stream().toList().get(0);
        assertEquals("checkIn", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Validation should fail when the checkIn date is not in the future")
    void failsOnCheckinDateInThePastOrPresent() {
        Booking booking = new Booking(validProperty, validGuest, LocalDateTime.now(), validCheckOutDate);
        var violations = validator.validate(booking);
        assertEquals(1, violations.size());
        var violation = violations.stream().toList().get(0);
        assertEquals("checkIn", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Validation should fail when the checkOut date is null")
    void failsOnNullCheckOutDate() {
        Booking booking = new Booking(validProperty, validGuest, validCheckInDate, null);
        var violations = validator.validate(booking);
        assertEquals(1, violations.size());
        var violation = violations.stream().toList().get(0);
        assertEquals("checkOut", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Validation should fail when the checkOut date is not after the checkIn date")
    void failsOnCheckOutNotAfterCheckInDate() {
        Booking booking = new Booking(validProperty, validGuest, validCheckInDate, validCheckInDate);
        var violations = validator.validate(booking);
        assertEquals(1, violations.size());
        var violation = violations.stream().toList().get(0);
        assertEquals("checkOut", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Validation should pass when all fields are valid")
    void passesValidationWhenAllFieldsAreValid() {
        Booking booking = new Booking(validProperty, validGuest, validCheckInDate, validCheckOutDate);
        var violations = validator.validate(booking);
        assertEquals(0, violations.size());
    }
}
