package com.biros.webcrawler.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlFinderTest {

    private static final String BASE_URL = "https://example.com";

    @Test
    public void testFindUrlsReturnsOnlyValidUrlsStartingWithBaseUrl() {
        String html = """
                <html>
                    <body>
                        <a href="https://example.com/page1">Page 1</a>
                        <a href="https://example.com/page2">Page 2</a>
                        <a href="https://other.com/page3">External</a>
                        <a href="invalid-url">Invalid</a>
                    </body>
                </html>
                """;

        List<String> urls = UrlFinder.findUrls(html, BASE_URL);

        assertEquals(
                List.of("https://example.com/page1", "https://example.com/page2"),
                urls
        );
    }

    @Test
    public void testFindUrlsReturnsEmptyListWhenHtmlContainsNoUrlsForBaseUrl() {
        String html = """
                <html>
                    <body>
                        <a href="https://other.com/page1">External</a>
                        <a href="not-a-url">Invalid</a>
                    </body>
                </html>
                """;

        List<String> urls = UrlFinder.findUrls(html, BASE_URL);

        assertEquals(List.of(), urls);
    }

    @Test
    public void testFindUrlsFindsHttpAndHttpsUrlsMatchingBaseUrl() {
        String html = """
                <html>
                    <body>
                        <a href="http://example.com/page1">HTTP page</a>
                        <a href="https://example.com/page2">HTTPS page</a>
                    </body>
                </html>
                """;

        List<String> urls = UrlFinder.findUrls(html, BASE_URL);

        assertEquals(List.of("http://example.com/page1", "https://example.com/page2"), urls);
    }
}
