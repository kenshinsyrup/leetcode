package com.myleetcode.stack.longest_absolute_file_path;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
  public int lengthLongestPath(String input) {
    return lengthLongestPathByStack(input);
  }

  /*
  Intuition:
  Stack
  https://leetcode.com/problems/longest-absolute-file-path/discuss/86666/Java-O(n)-Solution-Using-Stack

  Say N is total num of directory and file
  TC: O(N)
  SC: O(N)
  */
  private int lengthLongestPathByStack(String input){
    if(input == null || input.length() == 0){
      return 0;
    }

    String[] pathDetail = input.split("\n");

    Deque<String> direcStack = new ArrayDeque<>();
    int maxLen = 0;
    int curLen = 0;
    for(String path: pathDetail){
      int tabs = getNumOfTab(path);

      // reach the level of parent directory
      while(tabs < direcStack.size()){
        String discardDirec = direcStack.pop();

        // !!! when remove a direc from stack, we should know the direc len is discardDirec.length() - getNumOfTab(discardDirec), and since when we push direc to stack we need add 1 meanning "/" to curLen, so we need addionally minus 1 from curLen
        curLen -= (discardDirec.length() - getNumOfTab(discardDirec));
        curLen -= 1;
      }

      // get new curLen with cur path
      curLen += (path.length() - tabs);

      // if directory, push into stack
      if(isDirectory(path)){
        direcStack.push(path);

        curLen += 1; // !!! add a "/" after each directory
      }else{
        // otherwise update maxLen
        maxLen = Math.max(maxLen, curLen);

        // !!! remove filename len from curLen
        curLen -= (path.length() - tabs);
      }
    }

    return maxLen;

  }

  private int getNumOfTab(String str){
    int i = 0;
    int len = str.length();
    while(i < len && str.charAt(i) == '\t'){
      i++;
    }
    return i;
  }

  private boolean isDirectory(String str){
    return str.indexOf(".") == -1;
  }

}
