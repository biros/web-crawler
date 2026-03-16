package com.biros.webcrawler.workflow;

import com.biros.webcrawler.service.HttpService;
import com.biros.webcrawler.util.UrlFinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrawlerWorkflow {
    private static final Logger log = Logger.getLogger(CrawlerWorkflow.class.getName());
    private final String originalUrl;
    private final Set<String> visitedUrls;
    private final int maxDepth;

    public CrawlerWorkflow(String originalUrl, int maxDepth) {
        visitedUrls = new HashSet<>();
        this.originalUrl = originalUrl;
        this.maxDepth = maxDepth;
    }

    /**
     * Initiates the crawling process starting from the specified original URL and
     * logs the progress and results.
     * The method performs the following actions:
     * 1. Invokes an internal recursive method to crawl from the original URL
     *    to a specified depth or indefinitely if no limit is set.
     * 2. Outputs the list of visited URLs in a sorted order.
     */

    public void doCrawl() {
        log.log(Level.INFO, "Crawling started {0}", originalUrl);
        crawl(originalUrl, 0);
        log.log(Level.INFO, "Crawling finished");
        log.log(Level.INFO, "URLs found: {0}", visitedUrls.size());
        visitedUrls.stream().sorted().forEach(System.out::println);
    }

    private  void crawl(String url, int depth) {
        visitedUrls.add(url);
        if (depth != 0 && depth == maxDepth) {
            return;
        }
        String response = HttpService.callUrl(url);
        List<String> urls = UrlFinder.findUrls(response, originalUrl);

        urls.forEach((String foundUrl) -> {
            if (!visitedUrls.contains(foundUrl)) {
                crawl(foundUrl, depth + 1);
            }
        });
    }



}
