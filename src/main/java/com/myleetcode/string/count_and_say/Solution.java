package com.myleetcode.string.count_and_say;

class Solution {
    public String countAndSay(int n) {
        // special case
        if(n <= 0){
            return "";
        }

        return countAndSayByTranslate(n);
    }

    // intuition: just translate the description to code
    // 写了半天，一直搞不定如何计算遍历到的char的个数，伤感

    // 题意https://leetcode.com/problems/count-and-say/discuss/16015/Please-change-the-misleading-description
    // solution:https://leetcode.com/problems/count-and-say/discuss/16040/Straightforward-Java-Solution
    private String countAndSayByTranslate(int n){
        String str = "1";

        // count and say来生成下一行字符串，假设当前str 是11，那么下一行生成就是21，在下一行是1211
        for(int i = 1; i < n; i++){
            str = countNextStr(str);
        }

        return str;
    }

    private String countNextStr(String s){
        StringBuilder sb = new StringBuilder();

        char c = s.charAt(0); // 记住第一个char，用于接下来count和比较
        int count = 1;// 从1开始，s长度至少是1，所以肯定存在charAt(0)
        for(int i = 1; i < s.length(); i++){
            if(c == s.charAt(i)){ // 比较char i是否相同于c，相同则计数
                count++;
            }else{
                // 当前char i不同于c，那么数完了一批，生成这一批。
                sb.append(count);
                sb.append(c);

                // 重新分配count，和c
                count = 1;
                c = s.charAt(i);
            }
        }

        // 重要！！！收尾，根据上面的for循环，最后一次循环i==s.length()-1时，有两种情况：1 c和char i相同，那么会进入if，那么我们得到了一个count，应该把这个count和c生成；2 c和char i不同，说明最后一个char i和前面的不同，那么会进入else，那么i之前的会被正确的生成，但是最后一个char i并没有，然后既然count和c刚好是正确的需要生成的最后一个char i的个数和字符，所以这里用count和c生成是正确的。
        sb.append(count);
        sb.append(c);

        return sb.toString();
    }
}