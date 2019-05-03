package com.myleetcode.stack.simplify_path;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public String simplifyPath(String path) {
        return simplifyPathByStack(path);
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: https://leetcode.com/problems/simplify-path/discuss/25686/Java-10-lines-solution-with-stack
    // https://leetcode.com/problems/simplify-path/discuss/25686/Java-10-lines-solution-with-stack/256256
    // basic idea in this problem description is: we dont need '.'; we dont need the first before dir if we meet '..'
    // we could use stack to do this
    private String simplifyPathByStack(String path){
        if(path == null || path.length() == 0){
            return "/";
        }

        Deque<String> dirStack = new ArrayDeque<>();
        String[] dirs = path.split("/");// here, if path has "//", split by "/" give us a "" empty string between the "/" and "/"
        for(int i = 0; i < dirs.length; i++){
            if(dirs[i].length() == 0 || dirs[i].equals(".")){
                continue;
            }else if(dirs[i].equals("..")){
                if(!dirStack.isEmpty()){
                    dirStack.pop();
                }
            }else{
                dirStack.push(dirs[i]);
            }
        }

        if(dirStack.isEmpty()){
            return "/";
        }

        // build ret path, if we directly pop the stack, we will get reversed path, so we need get elem from the stack backward, here is the Deque's benifits.
        StringBuilder sb = new StringBuilder();
        while(!dirStack.isEmpty()){
            sb.append("/");
            sb.append(dirStack.removeLast());
        }

        return sb.toString();
    }
}
