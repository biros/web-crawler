package com.biros.webcrawler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        if (args.length == 0) {
            log.info( """
                    use the following parameters to run the crawer:
                    [0]: url to crawl (eg https://google.com)
                    [1]: depth (use 0 for infinite crawl)
                    """);
            return;
        }
        log.log(Level.INFO, "Crawling {0}", args[0]);
    }
}
