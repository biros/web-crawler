package com.biros.webcrawler.util;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlDomainExtractorTest {

    @Test
    public void testExtractDomainReturnsDomainForHttpsUrl() {
        assertEquals(
                Optional.of("example.com"),
                UrlDomainExtractor.extractDomain("https://example.com/page")
        );
    }

    @Test
    public void testExtractDomainRemovesWwwPrefix() {
        assertEquals(
                Optional.of("example.com"),
                UrlDomainExtractor.extractDomain("https://www.example.com/page")
        );
    }

    @Test
    public void testExtractDomainReturnsEmptyForInvalidUrl() {
        assertEquals(
                Optional.empty(),
                UrlDomainExtractor.extractDomain("not-a-valid-url")
        );
    }

    @Test
    public void testExtractDomainReturnsEmptyWhenHostIsMissing() {
        assertEquals(
                Optional.empty(),
                UrlDomainExtractor.extractDomain("/relative/path")
        );
    }
}