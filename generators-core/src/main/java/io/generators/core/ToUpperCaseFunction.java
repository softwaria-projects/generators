package io.generators.core;

import com.google.common.base.Function;

import java.util.Locale;

/**
 * Converts string to upper case. Handles null <code>input</code>s
 */
public class ToUpperCaseFunction implements Function<String, String> {
    private final Locale locale;

    public ToUpperCaseFunction(Locale locale) {
        this.locale = locale;
    }

    public ToUpperCaseFunction() {
        this(Locale.getDefault());
    }

    @Override
    public String apply(String input) {
        return input == null ? null : input.toUpperCase(locale);
    }
}
