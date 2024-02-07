package edu.clarkson.cosi.fsuvius.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Log provides simple functionality for logging events by class.
 * Log events can have one of four severity levels:
 *  - 0: [  OK  ]
 *  - 1: [ WARN ]
 *  - 2: [ ERROR ]
 *  - 3: [ FATAL ]
 */
public class Log {
    private static final SimpleDateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String LOG_PREFIX = " (Fsuvius)";
    private static final String LOG_INFO = " \u001B[32m[ INFO ]\u001B[0m  ";
    private static final String LOG_WARN = " \u001B[33m[ WARN ]\u001B[0m  ";
    private static final String LOG_ERROR = " \u001B[31m[ ERROR ]\u001B[0m ";
    private static final String LOG_FATAL = " \u001B[31m[ FATAL ]\u001B[0m ";

    private final String className;

    /**
     * Instantiates a Log for this class
     * @param className This class's name
     */
    public Log(String className) {
        this.className = className;
    }

    /**
     * Logs an event with a specified severity
     * @param level Severity (0: INFO, 1: WARN, 2: ERROR)
     * @param message Message to log
     */
    public void print(int level, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(LOG_DATE_FORMAT.format(new Date()));
        sb.append(LOG_PREFIX);
        switch (level) {
            case 1 -> sb.append(LOG_WARN);
            case 2 -> sb.append(LOG_ERROR);
            case 3 -> sb.append(LOG_FATAL);
            default -> sb.append(LOG_INFO);
        }
        sb.append(className);
        sb.append(" ".repeat(Math.max(0, 24 - className.length())));
        sb.append(": ");
        sb.append(message);
        System.out.println(sb);
    }

    /**
     * Logs an event with severity 0
     * @param message Message to log
     */
    public void print(String message) { this.print(0, message); }
}