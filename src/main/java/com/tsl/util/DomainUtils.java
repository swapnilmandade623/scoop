package com.tsl.util;

import java.net.URI;
import java.net.URISyntaxException;

public class DomainUtils {
    public static String extractBaseDomain(String urlString) {
        if (urlString == null || urlString.isEmpty()) {
            throw new IllegalArgumentException("URL string cannot be null or empty");
        }

        try {
            if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
                urlString = "http://" + urlString; 
            }
            
            URI uri = new URI(urlString);
            String host = uri.getHost(); 

            if (host != null && host.startsWith("www.")) {
                host = host.substring(4); 
            }

            return host; 
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URL: " + urlString, e);
        }
    }
}

