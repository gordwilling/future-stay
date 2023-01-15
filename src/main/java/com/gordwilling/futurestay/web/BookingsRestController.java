package com.gordwilling.futurestay.web;

import com.gordwilling.futurestay.core.api.Bookings;
import com.gordwilling.futurestay.core.domain.Booking;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@Validated
@RestController
public class BookingsRestController {

    private final Bookings bookings;

    @Inject
    public BookingsRestController(Bookings bookings) {
        this.bookings = bookings;
    }

    @Operation(summary = "Book (reserve) a property")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully booked the property",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Booking.class)
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid booking request provided",
                    content = @Content)
    })
    @PostMapping(
            path = "/bookings",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> createBooking(
            @Valid
            @RequestBody
            @Parameter(description = "Details of a booking request")
            Booking booking) {
        var result = bookings.create(booking);
        return ResponseEntity.ok(result);
    }
}
