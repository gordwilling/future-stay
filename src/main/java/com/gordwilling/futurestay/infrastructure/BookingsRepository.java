package com.gordwilling.futurestay.infrastructure;

import com.gordwilling.futurestay.core.domain.Booking;
import com.gordwilling.futurestay.core.domain.Property;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

/**
 * Stub for if, say, there was a data source containing all the property bookings.
 */
@SuppressWarnings("unused")
@Service
public class BookingsRepository {
    public Set<Booking> bookingsFor(Property p) {
        return Collections.emptySet();
    }

    public void save(Booking b) {
    }
}
