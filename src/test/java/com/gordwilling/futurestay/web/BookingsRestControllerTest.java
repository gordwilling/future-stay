package com.gordwilling.futurestay.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.inject.Inject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.net.http.HttpResponse.BodyHandlers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingsRestControllerTest {

    @Value(value="${local.server.port}")
    private int port;

    @Inject
    ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/bookings yields HTTP 200 OK when a valid booking is POSTed in request body")
    void createBookingRespondsWith200WhenSuppliedWithAValidBooking() throws Exception {
        var bookingJson = """
                {
                    "property": {
                      "address" : "46 Bayard St. #410",
                      "city" : "New Brunswick",
                      "state" : "NJ",
                      "zipCode" : "08901"
                    },
                    "guest": {
                      "emailAddress" : "some.user@emailaddress.com"
                    },
                    "checkIn": "2099-05-05T12:00",
                    "checkOut": "2099-05-06T12:00"
                }""".stripIndent();

        var request = HttpRequest.newBuilder()
                .uri(new URI(format("http://localhost:%d/api/bookings", port)))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(bookingJson))
                .build();

        var httpClient = HttpClient.newHttpClient();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertEquals("true", response.body());
    }

    @Test
    @DisplayName("POST /api/bookings yields HTTP 400 BadRequest when no booking is POSTed in request body")
    void createBookingRespondsWith400WhenNoBookingIsPostedInRequestBody() throws Exception {
        var request = HttpRequest.newBuilder()
                .uri(new URI(format("http://localhost:%d/api/bookings", port)))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        var httpClient = HttpClient.newHttpClient();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        assertEquals("""
                {
                  "messages" : [ "missing or unexpected value in request body" ]
                }
                """.trim(), response.body());
    }

    @Test
    @DisplayName("POST /api/bookings yields HTTP 400 BadRequest when the POSTed booking fails validation")
    void createBookingRespondsWith400WhenInvalidBookingIsPostedInRequestBody() throws Exception {
        // this value should fail validation on all seven nested fields
        var bookingJson = """
                {
                    "property": {
                      "address" : "",
                      "city" : "",
                      "state" : "",
                      "zipCode" : ""
                    },
                    "guest": {
                      "emailAddress" : "invalid"
                    },
                    "checkIn": "2022-05-05T12:00",
                    "checkOut": "2022-05-05T12:00"
                }""".stripIndent();

        var request = HttpRequest.newBuilder()
                .uri(new URI(format("http://localhost:%d/api/bookings", port)))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(bookingJson))
                .build();

        var httpClient = HttpClient.newHttpClient();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());

        var errorJson = response.body();
        var errors = objectMapper.readValue(errorJson, ApiErrors.class);

        var messages = errors.messages();

        // all seven fields should have a validation error, given the request payload
        assertEquals(7, messages.size());
        assertTrue(messages.contains("property.address: missing value"));
        assertTrue(messages.contains("property.city: missing value"));
        assertTrue(messages.contains("property.state: expected 2-letter state abbreviation"));
        assertTrue(messages.contains("property.zipCode: invalid zip code"));
        assertTrue(messages.contains("guest.emailAddress: invalid email address"));
        assertTrue(messages.contains("checkIn: must be a date in the future"));
        assertTrue(messages.contains("checkOut: must be after the checkIn date"));
    }
}
