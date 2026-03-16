package com.biros.webcrawler.util;

public class UrlValidator {
    private UrlValidator() {
        /* This utility class should not be instantiated */
    }

    /**
     * Checks whether a given URL is valid by verifying if it starts with "http://" or "https://".
     *
     * @param url the URL to be validated
     * @return {@code true} if the URL starts with "http://" or "https://", otherwise {@code false}
     */
    public static boolean isValid(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }
}
