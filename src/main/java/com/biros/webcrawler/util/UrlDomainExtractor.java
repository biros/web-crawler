package com.biros.webcrawler.util;

import java.net.URI;
import java.util.Optional;

public class UrlDomainExtractor {
    private UrlDomainExtractor() {
        /* This utility class should not be instantiated */
    }

    /**
     * Extracts the domain name from a given URL.
     * The "www." prefix will be removed from the domain if present.
     *
     * @param url the URL from which to extract the domain name
     * @return an {@code Optional} containing the extracted domain name, or an {@code Optional.empty()} if the URL is invalid or the domain cannot be determined
     */
    public static Optional<String> extractDomain(String url) {
        try {
            String host = new URI(url).getHost();
            if (host == null) {
                return Optional.empty();
            }
            return Optional.of(host.startsWith("www.") ? host.substring(4) : host);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
