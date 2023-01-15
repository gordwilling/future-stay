package com.gordwilling.futurestay.core.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record Property(
    @NotBlank(message = "{validation.error.NotBlank}")
    String address,

    @NotBlank(message = "{validation.error.NotBlank}")
    String city,

    @NotNull(message = "{validation.error.State}")
    State state,

    // A zip code can be:
    // <ul>
    //     <li>5 digits, such as 12345</li>
    //     <li>5 digits, a dash, then 4 digits, such as 12345-1234</li>
    // </ul>
    // The regular expression in the pattern covers both cases
    @Pattern(
            regexp = ZipCodeValidationPattern,
            message = "{validation.error.ZipCode}")
    String zipCode
) {
    public static final String ZipCodeValidationPattern = "^[0-9]{5}(?:-[0-9]{4})?$";
}
