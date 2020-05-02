package com.myleetcode.string.defanging_an_ip_address;

public class Solution {
    public String defangIPaddr(String address) {
        return defangIPaddrByStringBuilder(address);
    }

    private String defangIPaddrByStringBuilder(String address) {
        if (address == null || address.length() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (char ch : address.toCharArray()) {
            if (ch == '.') {
                sb.append('[');
                sb.append('.');
                sb.append(']');
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();

    }
}