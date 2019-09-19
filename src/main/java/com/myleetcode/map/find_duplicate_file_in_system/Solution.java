package com.myleetcode.map.find_duplicate_file_in_system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
  public List<List<String>> findDuplicate(String[] paths) {
    // return findDuplicateByMap(paths);
    return findDuplicateByMapII(paths);
  }

  /*
  Intuition:
  Sol2 Optimization of the Sol1.
  We use Map, key is content, value is list of full path of file that has such content.
  After build the Map, for each content, we check if its value list size is more than or equal to 2, add to ret

  Say N is the total fullpath
  TC: O(N)
  SC: O(N)
  */
  private List<List<String>> findDuplicateByMapII(String[] paths){
    List<List<String>> ret = new ArrayList<>();
    if(paths == null || paths.length == 0){
      return ret;
    }

    Map<String, List<String>> contentMap = new HashMap<>();

    // build content map, O(N)
    int len = paths.length;
    for(int i = 0; i < len; i++){
      String[] pathDetail = paths[i].split(" ");
      String directory = pathDetail[0];
      String content = "";

      for(int j = 1; j < pathDetail.length; j++){
        String[] detail = pathDetail[j].split("\\(");
        String fileName = detail[0];
        content = detail[1];

        List<String> fullpathList = contentMap.getOrDefault(content, new ArrayList<>());
        fullpathList.add(directory + "/" + fileName);
        contentMap.put(content, fullpathList);
      }
    }

    // build ret, O(N)
    for(String content: contentMap.keySet()){
      if(contentMap.get(content).size() >= 2){
        ret.add(contentMap.get(content));
      }
    }

    return ret;

  }


  /*
  intuition:
  Sol1 Translate description to codes.
  A Map, key is content, value is another innterMap
  the innerMap, key is directory, value is list of file names
  traverse and process the paths to Map
  build ret with Map by group with content

  Say N is total path
  TC: O(N)
  SC: O(N)
  */
  private List<List<String>> findDuplicateByMap(String[] paths){
    List<List<String>> ret = new ArrayList<>();
    if(paths == null || paths.length == 0){
      return ret;
    }

    Map<String, Map<String, List<String>>> contentMap = new HashMap<>();
    Map<String, Integer> countMap = new HashMap<>();

    // build content map, O(N)
    int len = paths.length;
    for(int i = 0; i < len; i++){
      String[] pathDetail = paths[i].split(" ");
      String directory = pathDetail[0];

      for(int j = 1; j < pathDetail.length; j++){
        String[] detail = pathDetail[j].split("\\(");
        String fileName = detail[0];
        String content = detail[1];

        // put if not exist key content
        contentMap.putIfAbsent(content, new HashMap<>());
        // put directory to content, put file to directory
        List<String> fileNameList = contentMap.get(content).getOrDefault(directory, new ArrayList<>());
        fileNameList.add(fileName);
        contentMap.get(content).put(directory, fileNameList);

        // count
        countMap.put(content, countMap.getOrDefault(content, 0) + 1);
      }
    }

    // build ret, O(N)
    for(String content: contentMap.keySet()){
      if(countMap.get(content) <= 1){
        continue;
      }

      List<String> subRet = new ArrayList<>();

      for(String directory: contentMap.get(content).keySet()){

        for(String fileName: contentMap.get(content).get(directory)){
          String path = directory + "/" + fileName;
          subRet.add(path);
        }
      }

      ret.add(subRet);
    }

    return ret;

  }


}