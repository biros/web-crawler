package com.biros.webcrawler.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UrlValidatorTest {

    @Test
    public void testIsValidWithHttpUrl() {
        assertTrue(UrlValidator.isValid("http://www.google.com"));
    }

    @Test
    public void testIsValidWithHttpsUrl() {
        assertTrue(UrlValidator.isValid("https://www.google.com"));
    }

    @Test
    public void testIsValidWithInvalidUrl() {
        assertFalse(UrlValidator.isValid("www.google.com"));
    }
}
