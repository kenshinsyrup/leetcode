package com.myleetcode.string.validate_ip_address;

public class Solution {
    public String validIPAddress(String IP) {
        return validIPAddressByCheck(IP);
    }

    public String validIPAddressByCheck(String IP) {
        int dotNum = 0;
        int colonNum = 0;
        for (char ch : IP.toCharArray()) {
            if (ch == ':') {
                colonNum++;
            } else if (ch == '.') {
                dotNum++;
            }
        }

        if (dotNum == 3) {
            return validateIPv4(IP);
        }

        if (colonNum == 7) {
            return validateIPv6(IP);
        }

        return "Neither";
    }

    public String validateIPv4(String IP) {
        String[] nums = IP.split("\\.", -1);
        for (String x : nums) {
            // Validate integer in range (0, 255):
            // 1. length of chunk is between 1 and 3
            if (x.length() == 0 || x.length() > 3) {
                return "Neither";
            }

            // 2. no extra leading zeros
            if (x.charAt(0) == '0' && x.length() != 1) {
                return "Neither";
            }

            // 3. only digits are allowed
            for (char ch : x.toCharArray()) {
                if (!Character.isDigit(ch)) {
                    return "Neither";
                }
            }

            // 4. less than 255
            if (Integer.parseInt(x) > 255) {
                return "Neither";
            }
        }

        return "IPv4";
    }

    public String validateIPv6(String IP) {
        String hexdigits = "0123456789abcdefABCDEF";
        String[] nums = IP.split(":", -1);
        for (String x : nums) {
            // Validate hexadecimal in range (0, 2**16):
            // 1. at least one and not more than 4 hexdigits in one chunk
            if (x.length() == 0 || x.length() > 4) {
                return "Neither";
            }

            // 2. only hexdigits are allowed: 0-9, a-f, A-F
            for (Character ch : x.toCharArray()) {
                if (hexdigits.indexOf(ch) == -1) {
                    return "Neither";
                }
            }
        }

        return "IPv6";
    }


}