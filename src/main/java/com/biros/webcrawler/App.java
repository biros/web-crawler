package com.biros.webcrawler;

import com.biros.webcrawler.util.UrlValidator;
import com.biros.webcrawler.workflow.ParallelCrawlerWorkflow;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This application crawls the webpage given as an argument and searches for additional urls. It navigates through the
 * pages to a specified depth or until all the links were visited once.
 * args[0]: url to crawl
 * args[1]: depth (use 0 for infinite crawl)
 */
public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        if (args.length == 0) {
            log.info("""
                    use the following parameters to run the crawer:
                    [0]: url to crawl (eg https://google.com)
                    [1]: depth (use 0 for infinite crawl)
                    """);
            return;
        }

        String url = args[0];

        int depth = 0;
        if (args.length >= 2) {
            depth = Integer.parseInt(args[1]);
        }

        if (!UrlValidator.isValid(url)) {
            log.log(Level.INFO, "The given url is invalid: {0}", url);
            return;
        }

        ParallelCrawlerWorkflow crawlerWorkflow = new ParallelCrawlerWorkflow(url, depth);
        crawlerWorkflow.doCrawlParallel();
    }
}
