package com.myleetcode.hash_table.find_anagram_mappings;

import java.util.*;

class Solution {
    public int[] anagramMappings(int[] A, int[] B) {
        if(A == null || B == null){
            return new int[0];
        }

        // return anagramMappingByHashSet(A, B);
        return anagramMappingByHashMap(A, B);
    }

    // 关于这个题目：These lists A and B may contain duplicates. If there are multiple answers, output any of them.注意这行话，这行话导致我们其实不需要处理duplicates in B，所以完全不需要Map<Integer, List<Integer>>去存B.value和B.index的list，可以直接用Map<Integer, Integer>去存B.value和B.index，只存一个index，输出时多个同样的value输出同一个index，也是满足题意的，愚蠢的题目，不过这样的话运行速度比现在这个一一对应解决duplicate的方式要快

    // optimazation: store B in a map, B index is value(list), B value is key. then loop A and find index list of B in map, then build ret
    // O(n)
    private int[] anagramMappingByHashMap(int[] A, int[] B){
        Map<Integer, List<Integer>> valueIndex = new HashMap<>();

        int lenA = A.length;
        int lenB = B.length;
        int[] ret = new int[lenA];
        for(int i = 0; i < lenB; i++){
            valueIndex.putIfAbsent(B[i], new ArrayList<>());
            valueIndex.get(B[i]).add(i);
        }

        // List<Integer> indexList;
        for(int i = 0; i < lenA; i++){
            // indexList = valueIndex.get(A[i]);
            // ret[i] = indexList.get(0);
            // indexList.remove(0);
            ret[i] = valueIndex.get(A[i]).remove(0); // remove会返回remove掉的内容，所以注释掉的代码可以被这行取代，比较好看
        }

        return ret;
    }


    // intuition: O(n^2), nested loop. when find the same value in A and B, store this value's index of B in a hashSet then we dont chekc it again
    private int[] anagramMappingByHashSet(int[] A, int[] B){
        Set<Integer> store = new HashSet<>();

        int lenA = A.length;
        int lenB = B.length;
        int[] ret = new int[lenA];
        for(int i = 0; i < lenA; i++){
            for(int j = 0; j < lenB; j++){
                if(!store.contains(B[j]) && A[i] == B[j]){
                    ret[i] = j;
                }
            }
        }

        return ret;

    }
}
