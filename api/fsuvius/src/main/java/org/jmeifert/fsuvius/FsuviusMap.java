package org.jmeifert.fsuvius;

public class FsuviusMap {
    /**
        Maximum amount of requests that we can process per minute.
        Additional requests will generate HTTP 429 responses.
     */
    public static final int MAX_REQUESTS_PER_MINUTE = 1200;
}
