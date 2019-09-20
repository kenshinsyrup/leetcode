package com.myleetcode.solid_code.repeated_string_match;

class Solution {
  public int repeatedStringMatch(String A, String B) {
    return repeatedStringMatchByTry(A, B);
  }

  /*
  suppose q is the least number for which len(B) <= len(A * q). We only need to check whether B is a substring of A * q or A * (q+1). If we try k < q, then B has larger length than A * q and therefore can't be a substring. When k = q+1, A * k is already big enough to try all positions for B; namely, S[i:i+len(B)] == B for i = 0, 1, ..., len(A) - 1.

  Say N is length of A, M is length of B
  TC: (M * (N + M)), N+M is the sb boundary length, search substring B in sb cost lenB*lenSB
  SC: (N + M)
  */
  private int repeatedStringMatchByTry(String A, String B){
    if(B == null || B.length() == 0){
      return 0;
    }
    if(A == null || A.length() == 0){
      return -1;
    }

    StringBuilder sb = new StringBuilder();
    int count = 0;
    // build sb until its not smaller than length of B, it the basic requirement to make B is substirng of sb
    while(sb.length() < B.length()){
      sb.append(A);
      count++;
    }

    // check if B is substring of sb
    if(sb.toString().indexOf(B) != -1){
      return count;
    }

    // if not, then append A one more time to check, the boundary length is lenB+lenA-1 to make sure  there's no new pattern occurs if we continue append
    sb.append(A);
    count++;
    if(sb.toString().indexOf(B) != -1){
      return count;
    }

    return -1;
  }
}
