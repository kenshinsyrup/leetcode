package com.myleetcode.dynamic_program.minimum_window_subsequence;

public class Solution {

  public String minWindow(String S, String T) {
    // return minWindowString(S, T);
    return minWindowStringByRecusrionWithMemo(S, T);
  }

  // https://leetcode.com/problems/minimum-window-subsequence/discuss/396411/Java-recursion-%2B-memo
  // TC: O(MN)
  // SC: O(MN)
  private String minWindowStringByRecusrionWithMemo(String strS, String strT) {
    if (strS == null || strS.length() == 0 || strT == null || strT.length() == 0) {
      return "";
    }

    int lenS = strS.length();
    int lenT = strT.length();
    int[][] memo = new int[lenS][lenT];

    String ret = "";
    for (int i = 0; i < lenS; i++) {
      if (strS.charAt(i) != strT.charAt(0)) {
        continue;
      }

      int substrLen = topdownWithMemo(strS, i, strT, 0, memo);
      if (substrLen == -1 || (ret.length() != 0 && ret.length() <= substrLen)) {
        continue;
      }

      ret = strS.substring(i, i + substrLen);
    }

    return ret;
  }

  private int topdownWithMemo(String strS, int idxS, String strT, int idxT, int[][] memo) {
    int lenT = strT.length();
    if (idxT >= lenT) {
      return 0;
    }

    int lenS = strS.length();
    if (idxS >= lenS) {
      return -1;
    }

    if (memo[idxS][idxT] != 0) {
      return memo[idxS][idxT];
    }

    int len = 0;
    if (strS.charAt(idxS) == strT.charAt(idxT)) {
      len = topdownWithMemo(strS, idxS + 1, strT, idxT + 1, memo);
    } else {
      len = topdownWithMemo(strS, idxS + 1, strT, idxT, memo);
    }
    if (len == -1) {
      memo[idxS][idxT] = -1;
    } else {
      memo[idxS][idxT] = len + 1;
    }

    return memo[idxS][idxT];

  }

  // https://leetcode.com/problems/minimum-window-subsequence/discuss/109356/JAVA-two-pointer-solution-(12ms-beat-100)-with-explaination/235584
  private String minWindowString(String strS, String strT) {
    if (strS == null || strS.length() == 0 || strT == null || strT.length() == 0) {
      return "";
    }

    String ret = "";
    int minLen = Integer.MAX_VALUE;

    int lenS = strS.length();
    int lenT = strT.length();
    int rightP = 0;
    while (rightP < lenS) {
      int idxT = 0;
      while (rightP < lenS) {
        if (strS.charAt(rightP) == strT.charAt(idxT)) {
          idxT++;
        }
        if (idxT == lenT) {
          break;
        }

        rightP++;
      }
      if (rightP >= lenS) {
        break;
      }

      int leftP = rightP;
      idxT = lenT - 1;
      while (leftP >= 0) {
        if (strS.charAt(leftP) == strT.charAt(idxT)) {
          idxT--;
        }
        if (idxT < 0) {
          break;
        }

        leftP--;
      }

      if (minLen > rightP - leftP + 1) {
        minLen = rightP - leftP + 1;
        ret = strS.substring(leftP, rightP + 1);
      }

      rightP = leftP + 1;
    }

    return ret;

  }
}
