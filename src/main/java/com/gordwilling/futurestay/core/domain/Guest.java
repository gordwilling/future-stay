package com.gordwilling.futurestay.core.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Guest(
        @NotBlank(message = "{validation.error.EmailAddress}")
        @Email(message = "{validation.error.EmailAddress}")
        String emailAddress) {
}
