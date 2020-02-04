package com.myleetcode.map.subdomain_visit_count;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<String> subdomainVisits(String[] cpdomains) {
        return subdomainVisitsByMap(cpdomains);
    }

    /*

    TC: O(N * M)
    SC: O(N)
    */
    private List<String> subdomainVisitsByMap(String[] cpdomains) {
        List<String> ret = new ArrayList<>();
        if (cpdomains == null || cpdomains.length == 0) {
            return ret;
        }

        Map<String, Integer> domainCountMap = new HashMap<>();
        for (String cpdomain : cpdomains) {
            String[] cp = cpdomain.split(" ");
            int count = Integer.valueOf(cp[0]);
            String domain = cp[1];

            while (domain.indexOf(".") != -1) {
                domainCountMap.put(domain, count + domainCountMap.getOrDefault(domain, 0));

                domain = domain.substring(domain.indexOf(".") + 1, domain.length());
            }
            // After loop, only the last part in the domain string, count it.
            domainCountMap.put(domain, count + domainCountMap.getOrDefault(domain, 0));

        }

        StringBuilder sb = new StringBuilder();
        for (String domain : domainCountMap.keySet()) {
            sb.append(domainCountMap.get(domain)).append(" ").append(domain);
            ret.add(sb.toString());

            sb = new StringBuilder();
        }

        return ret;
    }
}
