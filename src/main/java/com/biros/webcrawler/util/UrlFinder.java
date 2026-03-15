package com.biros.webcrawler.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlFinder {

    /**
     * Function to find all urls in the html
     * @param html the html body
     * @param baseUrl the base url of the website so no external urls are returned
     * @return a list of urls
     */
    public static List<String> findUrls(String html, String baseUrl) {
        List<String> urls = new ArrayList<>();
        Pattern pattern = Pattern.compile("https?://[^\\s\"'<>]+");
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            String url = matcher.group();
            if (UrlValidator.isValid(url) && url.startsWith(baseUrl)) {
                urls.add(url);
            }
        }

        return urls;
    }
}
