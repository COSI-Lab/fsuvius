package org.jmeifert.fsuvius;

public class FsuviusMap {
    /**
     * Maximum amount of requests that we can process per minute.
     * Additional requests will generate HTTP 429 responses.
     * Example: 1200
     */
    public static final int MAX_REQUESTS_PER_MINUTE = 1200;

    /**
     * CORS filter allowed origins.
     * Requests from origins not listed here will be refused.
     * Example: "*", "site.org", "*.site.org"
     */
    public static final String CORS_ALLOWED_ORIGINS = "*";
}
