package com.biros.webcrawler.util;

public class UrlValidator {
    public static boolean isValid(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }
}
