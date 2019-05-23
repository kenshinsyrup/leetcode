package com.myleetcode.string.largest_number;

import java.util.*;

class Solution {
    public String largestNumber(int[] nums) {
        // return largestNumberBySort(nums); Wrong
        // return largestNumberBySortII(nums);

        return largestNumberByArrayAndSort(nums);

        // return solutionCode(nums);
    }

    // optimize:
    // 0 the most important part, replace the Lambda Comparetor to Traditional Comparetor, reduce the RT in OJ from 35ms to 4ms
    // 1 dont use List, use Array, because List cost too much than Array.(not too important)
    // 2 check the first str's first char in numStrArr, if it's '0', early terminate. (not too important)
    // TC: O(L*N*logN), sort, every compare cost O(L)
    // SC: (N)
    private String largestNumberByArrayAndSort(int[] nums){
        if(nums == null || nums.length == 0){
            return "";
        }

        int len = nums.length;
        String[] numStrArr = new String[len];
        for(int i = 0; i < len; i++){
            numStrArr[i] = String.valueOf(nums[i]);
        }

        // !!! the key part is to get the larger str of combination
//         Arrays.sort(numStrArr, (str1, str2)->{
//             String order1 = str1 + str2;
//             String order2 = str2 + str1;

//             return order2.compareTo(order1); // TC: O(L)
//         });
        Arrays.sort(numStrArr, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                String s1 = a + b;
                String s2 = b + a;
                return s2.compareTo(s1);
            }
        });

        // early terminate, if after sort, the first str's first char is '0', means all '0' in numStrArr. with this, we could save the codes in sb checking
        if(numStrArr[0].charAt(0) == '0'){
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for(String str: numStrArr){
            sb.append(str);
        }

        return sb.toString();

    }

    // TC: O(L*N*logN), sort, every compare cost O(L)
    // SC: (N)
    private String largestNumberBySortII(int[] nums){
        if(nums == null || nums.length == 0){
            return "";
        }

        List<String> numStrList = new ArrayList<>();
        for(int num: nums){
            numStrList.add(String.valueOf(num));
        }

        // !!! the key part is to get the larger str of combination
        Collections.sort(numStrList, (str1, str2)->{
            String order1 = str1 + str2;
            String order2 = str2 + str1;

            return order2.compareTo(order1); // TC: O(L)
        });

        StringBuilder sb = new StringBuilder();
        for(String str: numStrList){
            // !!! omit leading 0
            if(str.equals("0") && sb.length() == 0){
                continue;
            }

            sb.append(str);
        }

        // if  numStrList are all 0, sb will be empty, return "0"
        if(sb.length() == 0){
            return "0";
        }

        return sb.toString();
    }


    // Wrong
    /*
    input: [824,938,1399,5607,6973,5703,9609,4398,8247]
    output: "9609938824782469735703560743981399"
    expect: "9609938824824769735703560743981399"
    */
    // this is wrong because we dont expect to get the "larger" numstr in str1 and str2, we are expected to get the "larger" numstr from str1+str2 and str2+str1. ie we want to make the combination larger, not necessary the larger str in front the smaller str (though most time these two conditions are the same, but check the above input)
    //intuition: sort numStr descending by char from head, if equal, by next char
    private String largestNumberBySort(int[] nums){
        if(nums == null || nums.length == 0){
            return "";
        }

        List<String> numStrList = new ArrayList<>();
        for(int num: nums){
            numStrList.add(String.valueOf(num));
        }

        Collections.sort(numStrList, (str1, str2)->{
            int start = 0;
            int len1 = str1.length();
            int len2 = str2.length();

            // 1 check the overlap parts
            int len = Math.min(len1, len2);
            while(start < len){
                if(str1.charAt(start) != str2.charAt(start)){
                    return str2.charAt(start) - str1.charAt(start);
                }else{
                    start++;
                }
            }

            // 2 check longer one's consecutive chars with the last char of shorter one's. for eg: 3 and 302, 3 should be first; 3 and 342, 342 shoudl be first
            if(len1 <len2){
                char lastC = str1.charAt(len1 - 1);
                while(start < len2){
                    if(str2.charAt(start) != lastC){
                        return str2.charAt(start) - lastC;
                    }else{
                        start++;
                    }
                }
            }else if(len1 > len2){
                char lastC = str2.charAt(len2 - 1);
                while(start < len1){
                    if(str1.charAt(start) != lastC){
                        return lastC - str1.charAt(start);
                    }else{
                        start++;
                    }
                }
            }

            // equal
            return 0;
        });

        StringBuilder sb = new StringBuilder();
        for(String str: numStrList){
            // !!! omit leading 0
            if(str.equals("0") && sb.length() == 0){
                continue;
            }

            sb.append(str);
        }

        // if  numStrList are all 0, sb will be empty, return "0"
        if(sb.length() == 0){
            return "0";
        }

        return sb.toString();
    }

}
