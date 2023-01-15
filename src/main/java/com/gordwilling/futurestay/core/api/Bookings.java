package com.gordwilling.futurestay.core.api;

import com.gordwilling.futurestay.core.domain.Booking;
import com.gordwilling.futurestay.infrastructure.BookingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

/**
 * The Bookings API provides the means to book (reserve) a property
 */
@Service
public class Bookings {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final BookingsRepository repository;

    @Inject
    public Bookings(BookingsRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new booking provided that there are no current bookings that
     * overlap with the dates specified in the new booking
     * @param booking the booking to create
     * @return true if the booking was created, false if the dates were already taken for the respective property
     */
    public boolean create(Booking booking) {
        log.info("Looking to create {}", booking);
        Set<Booking> currentBookings = repository.bookingsFor(booking.property());
        if (!overlaps(booking, currentBookings)) {
            repository.save(booking);
            log.info("Created {}", booking);
            return true;
        }
        log.info("Booking dates unavailable. Skipped creation of {}", booking);
        return false;
    }

    @SuppressWarnings("unused")
    boolean overlaps(Booking booking, Set<Booking> currentBookings) {
        return false;
    }
}
