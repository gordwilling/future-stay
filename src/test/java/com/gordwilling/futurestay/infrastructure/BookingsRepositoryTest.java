package com.gordwilling.futurestay.infrastructure;

import com.gordwilling.futurestay.core.domain.Booking;
import com.gordwilling.futurestay.core.domain.Guest;
import com.gordwilling.futurestay.core.domain.Property;
import com.gordwilling.futurestay.core.domain.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingsRepositoryTest {

    private final BookingsRepository repository = new BookingsRepository();

    @Test
    @DisplayName("bookingsFor should return an empty set")
    void bookingsFor() {
        var property = new Property("123 Fake Street", "Imaginary City", State.AK, "12345-1234");
        var bookings = repository.bookingsFor(property);
        assertEquals(0, bookings.size());
    }

    @Test
    @DisplayName("save should not throw an exception")
    void save() {
        var booking = new Booking(
                new Property("123 Fake Street", "Imaginary City", State.AK, "12345-1234"),
                new Guest("email@address.com"),
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(8));

        assertDoesNotThrow(() -> repository.save(booking));
    }
}
