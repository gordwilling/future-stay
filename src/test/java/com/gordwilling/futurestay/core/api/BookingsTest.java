package com.gordwilling.futurestay.core.api;

import com.gordwilling.futurestay.core.domain.Booking;
import com.gordwilling.futurestay.core.domain.Guest;
import com.gordwilling.futurestay.core.domain.Property;
import com.gordwilling.futurestay.core.domain.State;
import com.gordwilling.futurestay.infrastructure.BookingsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingsTest {

    /**
     * Normally we'd mock this or create a test implementation, but it doesn't
     * do anything right now, so we can just use it as is
     */
    private final BookingsRepository bookingsRepository = new BookingsRepository();

    private final Bookings bookings = new Bookings(bookingsRepository);

    @Test
    @DisplayName("create returns true")
    void createReturnsTrue() {
        var booking = new Booking(
            new Property("123 Fake Street", "Imaginary City", State.AK, "12345-1234"),
            new Guest("email@address.com"),
            LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(8)
        );

        var result = bookings.create(booking);
        assertTrue(result);

    }

    @Test
    void overlapsReturnsFalse() {
        var booking = new Booking(
                new Property("123 Fake Street", "Imaginary City", State.AK, "12345-1234"),
                new Guest("email@address.com"),
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(8)
        );
        var result = bookings.overlaps(booking, Collections.emptySet());
        assertFalse(result);
    }
}
