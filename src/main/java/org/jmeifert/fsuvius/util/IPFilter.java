package org.jmeifert.fsuvius.util;

import jakarta.servlet.http.HttpServletRequest;
import org.jmeifert.fsuvius.FsuviusMap;

/**
 * IPFilter provides functionality for checking IPs against a predefined allow-list.
 */
public class IPFilter {
    /**
     * Checks an address to see if it is included in the allow-list.
     * @param request The request to check
     * @return True if the address is included in the allow-list.
     */
    public static boolean checkAddress(HttpServletRequest request) {
        String fwd_ip = request.getHeader("X-Forwarded-For");
        String raw_ip = request.getRemoteAddr();
        if(fwd_ip == null) {
            for(String k : FsuviusMap.ALLOWED_ADDR_PREFIXES) {
                if(raw_ip.startsWith(k)) {
                    return true;
                }
            }
        } else {
            for(String k : FsuviusMap.ALLOWED_ADDR_PREFIXES) {
                if(fwd_ip.startsWith(k)) {
                    return true;
                }
            }
        }
        return false;
    }
}
