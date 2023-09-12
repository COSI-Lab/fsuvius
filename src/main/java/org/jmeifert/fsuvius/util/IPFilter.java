package org.jmeifert.fsuvius.util;

import org.jmeifert.fsuvius.FsuviusMap;

/**
 * IPFilter provides functionality for checking IPs against a predefined allow-list.
 */
public class IPFilter {
    /**
     * Checks an address to see if it is included in the allow-list.
     * @param ip The IP address to check
     * @return True if the address is included in the allow-list.
     */
    public static boolean checkAddress(String ip) {
        for(String k : FsuviusMap.ALLOWED_ADDR_PREFIXES) {
            if(ip.startsWith(k)) {
                return true;
            }
        }
        return false;
    }
}
