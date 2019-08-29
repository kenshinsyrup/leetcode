package com.myleetcode.solid_code.unique_email_addresses;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public int numUniqueEmails(String[] emails) {
        return numUniqueEmailsBySet(emails);
    }

    // intuition:
    // traverse the array, for each email string, apply the rules to get real email string.
    // put real email to a set, if contains already, next
    // TC: O(N * M), N is emails array length, M is longest email string length
    // SC: O(N * M)
    private int numUniqueEmailsBySet(String[] emails){
        if(emails == null || emails.length == 0){
            return 0;
        }

        int len = emails.length;
        Set<String> emailSet = new HashSet<>();
        for(int i = 0; i < len; i++){
            String realEmailStr = getRealEmailString(emails[i]);

            if(!emailSet.contains(realEmailStr)){
                emailSet.add(realEmailStr);
            }
        }

        return emailSet.size();
    }

    // TC: O(M), M is length of given string
    // SC: O(M)
    private String getRealEmailString(String str){
        int len = str.length();
        StringBuilder sb = new StringBuilder();

        int idx = 0;
        // first process name part
        while(idx < len){
            char ch = str.charAt(idx);

            // find domain part
            // check @ first
            if(ch == '@'){
                break;
            }
            // check +, ignore all char after + until @
            if(str.charAt(idx) == '+'){
                while(idx < len && str.charAt(idx) != '@'){
                    idx++;
                }
                break;
            }
            // process . and normal chars
            if(ch != '.'){
                sb.append(ch);
            }
            idx++;
        }
        // then process domain part
        while(idx < len){
            sb.append(str.charAt(idx));

            idx++;
        }

        return sb.toString();
    }
}
