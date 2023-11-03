package org.jmeifert.fsuvius.util;

import jakarta.servlet.http.HttpServletRequest;
import org.jmeifert.fsuvius.FsuviusMap;

/**
 * IPFilter provides functionality for checking IPs against a predefined filter.
 */
public class IPFilter {
    /**
     * Checks a request's IP address to see if it should be filtered.
     * @param request The request to check
     * @return True if the address should be filtered.
     */
    public static boolean isFiltered(HttpServletRequest request) {
        String ip = IPFilter.getAddress(request);
        for(String k : FsuviusMap.ALLOWED_ADDR_PREFIXES) {
            if(ip.startsWith(k)) { return false; }
        }
        return true;
    }

    /**
     * Gets the IP address a request originated from.
     * Will look for an X-Forwarded-For header first,
     * if there is none it will just use the remote address.
     * @param request HttpServletRequest to look for an address in
     * @return The IP address the request originated from
     */
    public static String getAddress(HttpServletRequest request) {
        String forwarded_ip = request.getHeader("X-Forwarded-For");
        String remote_ip = request.getRemoteAddr();
        if(forwarded_ip == null) { return remote_ip; }
        return forwarded_ip;
    }
}
