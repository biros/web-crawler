package com.biros.webcrawler.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpService {

    private static final Logger log = Logger.getLogger(HttpService.class.getName());

    /**
     * Function to call the given url and return the response
     * @param url of the website to call
     * @return the body of the website
     */
    public static String callUrl(String url) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            log.log(Level.SEVERE,"Request failed with IOException", e);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE,"Request failed with InterruptedException", e);
        }
        return null;
    }
}
