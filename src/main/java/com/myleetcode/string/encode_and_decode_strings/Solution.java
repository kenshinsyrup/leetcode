package com.myleetcode.string.encode_and_decode_strings;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    class Codec {

        // https://leetcode.com/problems/encode-and-decode-strings/discuss/70451/Java-solution-pretty-straight-forward
        // the basic idea is use the "escape" thought: we could replace all "/" and "*" in original str to "//" and "/*", then, we could use "*" and "/" freely. here we could use the "*" as the seperator of strs to encode strs
        // when decode, we use sb to build strs list, we check the input str, if we meet a "//" or "/*", we know we should append char '/' or '*' respectively. if we meet a "*", we know here is a sepertor.

        // Encodes a list of strings to a single string.
        public String encode(List<String> strs) {
            if(strs == null || strs.size() == 0){
                return null;
            }

            StringBuilder sb = new StringBuilder();
            for(String str: strs){
                sb.append(str.replace("/", "//").replace("*", "/*"));
                sb.append("*");
            }

            return sb.toString();
        }

        // Decodes a single string to a list of strings.
        public List<String> decode(String s) {
            List<String> ret = new ArrayList<>();

            if(s == null){
                return ret;
            }

            int i = 0;
            int len = s.length();
            StringBuilder sb = new StringBuilder();
            while(i < len){
                // first check the seperator
                // i now is at a seperator, so we add current sb to ret, clear the sb and forward i
                // !!! here, we only check current char to know if it's seperator '*', the same with below, we only check current and the folowing char to get the idea of if it's escaping '/' or '*'.
                // because we know if we have "/" or "*" in original str, then in s we must have the concsecutive chars '/''/' and '/''*', so we jump 2 when meet this, so we could be sure that if we meet a '*', that must be a seperator, not a '*' in "/*"
                if(s.charAt(i) == '*'){
                    ret.add(sb.toString());
                    i++;
                    sb = new StringBuilder();
                    continue;
                }

                // then, if not a seperator, there are 3 conditions:
                // 1 "//" means "/" in original str, forward i by 2
                if(i < len -1 && s.charAt(i) == '/' && s.charAt(i + 1) == '/'){
                    sb.append('/');
                    i += 2;
                    continue;
                }

                // 2 "/*" means "*" in original str, forward i by 2
                if(i < len - 1 && s.charAt(i) == '/' && s.charAt(i + 1) == '*'){
                    sb.append('*');
                    i += 2;
                    continue;
                }

                // 3 normal case, just append and forward i by 1
                sb.append(s.charAt(i));
                i++;
            }

            return ret;

        }
    }

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(strs));
}
