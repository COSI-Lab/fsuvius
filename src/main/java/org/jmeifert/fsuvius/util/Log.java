package org.jmeifert.fsuvius.util;

import java.util.Date;

/**
 * Log provides simple functionality for logging events by class.
 * Log events can have one of three severity levels:
 *  - 0: [  OK  ]
 *  - 1: [ WARN ]
 *  - 2: [ERROR!]
 */
public class Log {
    private final String PREFIX = "(FsuviusApplication) ";
    private final String LOG_OK = " [  OK  ] ";
    private final String LOG_WARN = " [ WARN ] ";
    private final String LOG_ERROR = " [ERROR!] ";

    private String className;

    /**
     * Instantiates a Log for this class
     * @param className This class's name
     */
    public Log(String className) {
        this.className = className;
    }

    /**
     * Logs an event with severity 0
     * @param entry Event to log
     */
    public void print(String entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX);
        sb.append(new Date());
        sb.append(LOG_OK);
        sb.append(className);
        sb.append(": ");
        sb.append(entry);
        System.out.println(sb.toString());
    }

    /**
     * Logs an event with a specified severity
     * @param level Severity
     * @param entry Event to log
     */
    public void print(int level, String entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX);
        sb.append(new Date());
        switch (level) {
            case 1 -> sb.append(LOG_WARN);
            case 2 -> sb.append(LOG_ERROR);
            default -> sb.append(LOG_OK);
        }
        sb.append(className);
        sb.append(": ");
        sb.append(entry);
        System.out.println(sb.toString());
    }
}