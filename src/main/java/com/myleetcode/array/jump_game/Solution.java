package com.myleetcode.array.jump_game;

class Solution {
    public boolean canJump(int[] nums) {
        // return canJumpByTraverse(nums);
        return canJumpByMaxReachable(nums);
    }

    // TC: O(N)
    // SC: O(1)
    // another O(N) solution: https://leetcode.com/problems/jump-game/discuss/20932/6-line-java-solution-in-O(n)
    // basic ieda: at each step, we keep track of the furthest reachable index
    private boolean canJumpByMaxReachable(int[] nums){
        if(nums == null || nums.length == 0){
            return false;
        }
        if(nums.length == 1){
            return true;
        }

        int maxReachable = 0;
        for(int i = 0; i < nums.length; i++){
            if(i > maxReachable){
                return false;
            }

            maxReachable = Math.max(maxReachable, i + nums[i]);
        }

        return true;
    }

    // TC: O(N ^ 2)
    // SC: O(1)
    // intuition: seems a trick problem, according to the eg2, we know the only impossible condition is we have 0 val that no vals before it could skip it to reach its following elems.
    // so we could traverse the array from back, if we encounter a 0, then we caculate the vals before it if the gap between them are smaller than the difference(差) between them, ie the val's value.
    private boolean canJumpByTraverse(int[] nums){
        if(nums == null || nums.length == 0){
            return false;
        }
        if(nums.length == 1){
            return true;
        }
        if(nums[0] == 0){
            return false;
        }

        for(int i = nums.length - 1; i >= 1; i--){
            // if nums[i] is 0, and we need to reach its back ie it's not the last elem
            if(nums[i] == 0 && i != nums.length - 1){
                int j = i - 1;
                while(j >= 0){
                    if(nums[j] > (i - j)){
                        break;
                    }

                    j--;
                }
                if(j < 0){
                    return false;
                }
            }
        }

        return true;
    }
}
