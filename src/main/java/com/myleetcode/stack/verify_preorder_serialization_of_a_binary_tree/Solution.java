package com.myleetcode.stack.verify_preorder_serialization_of_a_binary_tree;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public boolean isValidSerialization(String preorder) {
        return isValidSerializationByStack(preorder);
    }

    // and, there's a solution using the "indegree and outdegree", that's more easy but use stack is more normal and easy to understand and easy to remember: https://leetcode.com/problems/verify-preorder-serialization-of-a-binary-tree/discuss/78552/JAVA-Counting-Indegree-and-Outdegree-SIMPLE-and-CLEAR!

    // TC: O(N)
    // SC: O(N)
    // intuition: Stack
    // https://leetcode.com/problems/verify-preorder-serialization-of-a-binary-tree/discuss/78667/My-concise-Java-Stack-Solution.
    // since all null nodes are represnet as #, so every leaf node should have two "#" child. we could maintain a stack, push every num str we meet in the stack,
    // then if we meet a "#", and if the stack top is also a "#", means we find a pair null node, so the node before the stack top "#" is the father node of this two "#", so we pop the top "#" out, then pop the father node, ie the new stack top node out, this way, we just verify a sub tree: father node and its two null child. !!! At this time, we push the "#" into the stack to represent the verified sub tree.
    // if we meet a "#" and the stack top is not a "#", menas the top is the father of the "#"(means a veririfed subtree) and we are looking for another child subtree, so we push the "#" in.
    private boolean isValidSerializationByStack(String preorder){
        if(preorder == null || preorder.length() == 0){
            return true;
        }

        String[] nodeArr = preorder.split(",");
        Deque<String> nodeStack = new ArrayDeque<>();

        for(int i = 0; i < nodeArr.length; i++){
            if(!nodeArr[i].equals("#")){
                nodeStack.push(nodeArr[i]);
            }else{
                while(!nodeStack.isEmpty() && nodeStack.peek().equals("#")){
                    // pop the child "#" already in stack
                    nodeStack.pop();

                    // here we must do a check, if empty, means no father, false (and if dont check, may encounter runtime error)
                    if(nodeStack.isEmpty()){
                        return false;
                    }

                    // pop the corresponding father node
                    nodeStack.pop();
                }

                // push a "#" in as we just veriried a subtree which is a child of a father node
                nodeStack.push("#");
            }
        }

        // at last, only a "#" should be in the stack
        return nodeStack.size() == 1 && nodeStack.pop().equals("#");
    }
}
