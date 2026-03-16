package com.biros.webcrawler.workflow;

import com.biros.webcrawler.service.HttpService;
import com.biros.webcrawler.util.UrlFinder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParallelCrawlerWorkflow {
    private static final Logger log = Logger.getLogger(ParallelCrawlerWorkflow.class.getName());
    private final String originalUrl;
    private final int maxDepth;
    private final Set<String> visitedUrls;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final Semaphore semaphore = new Semaphore(10);

    public ParallelCrawlerWorkflow(String originalUrl, int maxDepth) {
        visitedUrls = ConcurrentHashMap.newKeySet();
        this.originalUrl = originalUrl;
        this.maxDepth = maxDepth;
    }

    /**
     * Initiates a parallel crawling process starting from a specified original URL and logs the progress and results.
     * The method performs the following operations:
     * 1. Starts a parallel crawling task using an internal `crawl` method to traverse through URLs up to a specified depth.
     * 2. Manages parallel execution using a virtual thread-based executor and limits concurrent threads with a semaphore.
     * 3. Upon completion, logs the total number of unique URLs discovered and outputs the visited URLs in a sorted order.
     * This method ensures the executor is properly shut down even in cases of interruptions or errors during execution.
     */
    public void doCrawlParallel() {
        log.log(Level.INFO, "Parallel crawling started {0}", originalUrl);
        try {
            crawl(originalUrl, 0);
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        log.log(Level.INFO, "Parallel crawling finished");
        log.log(Level.INFO, "URLs found: {0}", visitedUrls.size());
        visitedUrls.stream().sorted().forEach(System.out::println);
    }

    private void crawl(String url, int depth) {
        try {
            semaphore.acquire();
            if (!visitedUrls.add(url)) {
                return;
            }
            if (depth != 0 && depth == maxDepth) {
                return;
            }
            String response = HttpService.callUrl(url);
            List<String> urls = UrlFinder.findUrls(response, originalUrl);

            urls.forEach((String foundUrl) -> {
                if (!visitedUrls.contains(foundUrl)) {
                    executor.submit(() -> crawl(foundUrl, depth + 1));
                }
            });

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.log(Level.SEVERE, "Crawling interrupted for URL: " + url, e);
        } finally {
            semaphore.release();
        }
    }
}
