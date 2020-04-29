package com.myleetcode.stack.tag_validator;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public boolean isValid(String code) {
        return isValidByStack(code);
    }

    private boolean isValidByStack(String code) {
        if (code == null || code.length() == 0) {
            return false;
        }

        int len = code.length();
        Deque<String> tagStack = new ArrayDeque<>();
        int i = 0;
        while (i < len) {
            // Lack of open tag.
            if (i > 0 && tagStack.isEmpty()) {
                return false;
            }

            if (code.startsWith("<![CDATA[", i)) { // CDATA.
                int j = i + 9;
                i = code.indexOf("]]>", j);
                if (i < 0) {
                    return false;
                }

                i += 3;
            } else if (code.startsWith("</", i)) { // Closed tag.
                int j = i + 2;
                i = code.indexOf('>', j);
                if (i < 0 || i == j || i - j > 9) {
                    return false;
                }

                // Only upper case in tag name.
                for (int k = j; k < i; k++) {
                    if (!Character.isUpperCase(code.charAt(k))) {
                        return false;
                    }
                }

                String tagName = code.substring(j, i);
                if (tagStack.isEmpty() || !tagStack.pop().equals(tagName)) {
                    return false;
                }

                i++;
            } else if (code.startsWith("<", i)) { // Open tag.
                int j = i + 1;
                i = code.indexOf('>', j);
                if (i < 0 || i == j || i - j > 9) {
                    return false;
                }

                // Only upper case in tag name.
                for (int k = j; k < i; k++) {
                    if (!Character.isUpperCase(code.charAt(k))) {
                        return false;
                    }
                }

                String tagName = code.substring(j, i);
                tagStack.push(tagName);

                i++;
            } else { // Normal content.
                i++;
            }
        }

        return tagStack.isEmpty();
    }
}