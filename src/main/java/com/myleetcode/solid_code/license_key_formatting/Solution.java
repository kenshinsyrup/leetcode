package com.myleetcode.solid_code.license_key_formatting;

class Solution {
  public String licenseKeyFormatting(String S, int K) {
    return licenseKeyFormattingByTraverse(S, K);
  }

  /*
  hard part is to understand the problem: https://leetcode.com/problems/license-key-formatting/discuss/96512/Java-5-lines-clean-solution/230723
  Intuition:
  traverse given S reverse order, use a sb to build ret string
  reverse, upper case

  TC: O(N)
  SC: O(N)
  */
  private String licenseKeyFormattingByTraverse(String str, int K){
    if(str == null || str.length() == 0){
      return str;
    }

    StringBuilder sb = new StringBuilder();
    int len = str.length();
    int i = len - 1;
    int count = 0;
    while(i >= 0){
      char ch = str.charAt(i);

      if(ch == '-'){
        i--;
        continue;
      }

      sb.append(ch);
      count++;
      if(count == K){
        sb.append('-');
        count = 0;
      }
      i--;
    }
    int sbLen = sb.length();
    if(sbLen > 0 && sb.charAt(sbLen - 1) == '-'){
      sb.delete(sbLen - 1, sbLen);
    }

    return sb.reverse().toString().toUpperCase();
  }
}