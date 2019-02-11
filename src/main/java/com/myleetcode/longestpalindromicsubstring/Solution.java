package com.myleetcode.longestpalindromicsubstring;

class Solution {
    public String longestPalindrome(String s) {
        if(s == null || s.length() == 0){
            return "";
        }
        
        // longest palindrome substring start index in string
        int start = 0;
        // ... end index in string
        int end = 0;
        
//      以每一个字符开始，尝试以其为中心扩展看是否为回文   
        for(int i = 0; i < s.length() ; i++){
            // 单字母开始的expand，如bab中的a
            int len1 = expandAroundCenter(s, i, i);
            // 双字母开始单expand, 如baab中的a，a
            // 这里之所以i+1但是在for循环并没有使用s.length()-1来思考越界的问题，原因是expandAroundCenter自己在检查越界.这里我们必须遍历完整个字符串所以必须i < s.length()
            int len2 = expandAroundCenter(s, i, i+1);
            int len= Math.max(len1, len2);
            if(len >= end - start + 1){
                // 注意以i或i，i+1为中心时，如何计算得到回文的start和end index
                // 如果n为奇数，[0, n-1]这个区间中，i中心部分之外的，左半部分的数量为(n-1)/2,比如n == 3，在[0,2]区间中，i为1是中心，左半部分为0，中间部分为1,右半部分为2，左半部分的数量为(3-1)/2. (注意这里不是说只能这样算，是说这样算也是对的，理论上我们应该说n为奇数时，左半部分的数量为n/2是对的，这里是为了证明说(n-1)/2也是对的)。而i右边的部分的数量，n-1/2或者n/2都对
                // 如果n为偶数，[0,n-1]这个区间中，i和i+1中心部分之外的，左半部分的数量(n-1)/2,比如n==4,在[0,3]区间中，中心部分i为1，i+1为2，那么中心部分之外的左半部分是0，左半部分的数量为(4-1)/2，右半部分为3，右半部分的数量为(4-1)/2，而从i+1开始到末尾的数量是((n-1)/2 + 1)或者n/2
                // 也就是说，奇数-1再除以2，与其自己除以2，得到的值相同;偶数-1除以2，则不同，比自己除以2的值小1
                // 所以，start就是i-左半部分的长度
                // end就是i+i右边的长度，根据上面的结论，当回文为奇数时，i右边的数量可以表示为(n-1)/2或者n/2;当回文为偶数时，i右边的数量可以表示为((n-1)/2+1) 或者n/2.那么我们就用n/2来避免对回文的奇偶进行判断。
                start = i - (len-1)/2; 
                end = i + len/2;
            }
        }
        
        return s.substring(start, end + 1);// [start, end + 1)
    }
    
//     从left，right开始向两边扩展，直到不符合条件。当不符合条件时，left和right一定分别在“当前满足条件的回文的”左边一位和右边一位，所以当前满足条件的回文的长度就是right - left -1
    private int expandAroundCenter(String s, int left, int right){
        while(left >= 0 && right < s.length() && (s.charAt(left) == s.charAt(right))){
            left--;
            right++;
        }
        
        return right - left - 1;
    }       
}