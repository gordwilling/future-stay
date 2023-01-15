package com.gordwilling.futurestay.web;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Locale;

/**
 * Utility class to make localization lookups a bit more terse than when using <code>MessageSource</code>.
 * The name comes from the fact that "localization"* is often abbreviated as L10N.
 */
@Component
public class Lion {

    private final MessageSource messageSource;

    @Inject
    public Lion(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String of(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }
}
