package com.gordwilling.futurestay.core.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.ScriptAssert;

import java.time.LocalDateTime;

@ScriptAssert(
        // Three obscure things are being used here:
        // 1. @ScriptAssert is an annotation from the validation api
        //    that enables the execution of a custom script to perform validation.
        // 2. The provided script runs using the "Java" "Scripting" Engine, specified
        //    in JSR-223. This enables running scripts within the java runtime. Multiple
        //    scripting languages are supported.
        // 3. Jexl is the scripting language selected for this particular instance
        //    see: https://commons.apache.org/proper/commons-jexl/reference/jsr223.html
        lang = "jexl",
        script = """
        // validates that the checkOut date is after the checkIn date
        if (_this.checkOut() != null && _this.checkIn() != null) {
            _this.checkOut().isAfter(_this.checkIn());
        } else {
            true;
        }""",
        reportOn = "checkOut",
        message = "{validation.error.booking.checkOut.not.after.checkIn}")
public record Booking(
        @Valid
        @NotNull(message = "{validation.error.NotNull}")
        Property property,

        @Valid
        @NotNull(message = "{validation.error.NotNull}")
        Guest guest,

        @Valid
        @NotNull(message = "{validation.error.NotNull}")
        @Future(message = "{validation.error.Future}")
        LocalDateTime checkIn,

        @Valid
        @NotNull(message = "{validation.error.NotNull}")
        // further validation handled by @ScriptAssert at class level
        LocalDateTime checkOut) {
}
