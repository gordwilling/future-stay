package com.gordwilling.futurestay.core.domain;

import jakarta.validation.Validator;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

public interface ValidationTest {
    Validator validator = buildDefaultValidatorFactory().getValidator();
}
