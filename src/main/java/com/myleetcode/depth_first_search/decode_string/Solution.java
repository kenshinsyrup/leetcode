package com.myleetcode.depth_first_search.decode_string;

class Solution {
    public String decodeString(String s) {

        // special case
        if(s == null || s.length() == 0){
            return "";
        }

        return decodeStringByDFS(s);
    }

    // intuition: 刚开始看到括号就想是不是stack，但是发现有多重嵌套[]情况下不知道该怎么处理内部的内容。
    // https://leetcode.com/problems/decode-string/discuss/87534/Simple-Java-Solution-using-Stack这里有一个stack的实现，这种问题不用递归太复杂了

    // 然后，用递归的思路来想一下, 那么将每一个[]中间的内容是做一个节点，那么就是一个DFS的样子
    // 我们知道输入string的格式为...数字[...]...数字[...]...
    // 1 那么就是从头到尾遍历这个字符串，对于在位于 数字[...] 之前和之后的纯英文字符，都是直接append
    // 2 然后以找到数字为标志，开始找[]节点, 节点内的内容需要递归去解. 首先就是解析数字(注意数字可以是多位)num, 数字后必紧跟[,称之为first [, 那么找到这个对应的]就找全了一个需要递归的组，那么我们既然有了这个组的边界([右边边开始，]左边停止)，那么递归他，得到的结果循环num次append到当前sb.
    //然后就是继续遍历当前string重复1和2的过程知道到达当前string末尾

    // https://leetcode.com/problems/decode-string/discuss/87567/Java-Simple-Recursive-solution
    private String decodeStringByDFS(String s){
        // every dfs need itself's sb
        StringBuilder sb = new StringBuilder();

        int len = s.length();

        // traverse current string
        for(int i = 0; i < len; i++){
            char curChar = s.charAt(i);

            // if head is digit
            if(Character.isDigit(curChar)){
                int digitBegin = i; // Caveat!!! charAt(i) is digit dont mean we need only loop charAt(i) times, consider 12[a].

                // find the first '[', then [digitBegin, i) is the digit num
                while(s.charAt(i) != '['){
                    i++;
                }

                // the times we need to loop
                int num = Integer.valueOf(s.substring(digitBegin, i));

                // current i points to the first '[', then string begins at i + 1
                int strBegin = i + 1;

                // count record the [ and ], [ for 1, and ] for -1
                int count = 1;
                // find current node's end
                while(count != 0){
                    i++; // i points to the first '[', so we first plus 1 to it to check the char after the first [
                    if(s.charAt(i) == '['){
                        count ++;
                    }else if(s.charAt(i) == ']'){
                        count--;
                    }
                }
                // then the i points to the end of current node, it's a ']' corresponding to the first '['.
                // so, we know, the child node is string at [strBegin, i)
                // and we recurse decode to get the result of child node
                String childRet = decodeStringByDFS(s.substring(strBegin, i));

                // and loop num times
                for(int j = 0; j < num; j++){
                    sb.append(childRet);
                }

            }else{
                // then must be chars like "ef" in eg 3
                sb.append(curChar);
            }
        }

        return sb.toString();
    }
}