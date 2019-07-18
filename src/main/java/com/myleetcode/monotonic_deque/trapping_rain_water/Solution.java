package com.myleetcode.monotonic_deque.trapping_rain_water;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int trap(int[] height) {
        
        // 思路: 每个index对应一根柱子，如果这个柱子两边（不一定相邻,而且包括自己）存在两根柱子，这两根柱子的都比它高，那么这个柱子就能存水，存水的量就是：左右两边的比它高的柱子里面，((左边最高的和右边最高的之间的较低的一根的高度 * 柱子的面积) - (当前柱子的高度 * 柱子的面积))
        
        // special case
        if(height == null || height.length <= 1){
            return 0;
        }
        
        // 最原始的写法: RT O(n^2)
        // return trapByBruteForce(height);
        
        // 用DP来优化: RT O(n), ST O(1)
        // return trapByDP(height);

        // 使用双指针的优化
        // return trapByTwoPointers(height);

        // with Monotonic Stack
        return trapWithMonotonicStack(height);
    }

    // https://leetcode.com/problems/trapping-rain-water/discuss/17414/A-stack-based-solution-for-reference-inspired-by-Histogram
    // intuition: we could use Monotonic Stack to solve it, same as 84. Largest Rectangle in Histogram.
    // the defference is 84 is looking for the first lower at left and right, this problem is looking for the first higher at left and right, so this uses the Decreasing Monotonic Stack.
    // Because this problem wants us find the first higher bar in left and first higher bar in right, then the pool height is (min(left, right) - the bar.height) * width. because we are keeping a decreasing Monotonic Stack, so after pop the non valid bar out, the left pos is the stack top, and the right pos is cur idx.
    private int trapWithMonotonicStack(int[] height){
        if(height == null || height.length <= 1){
            return 0;
        }

        Deque<Integer> barStack = new ArrayDeque<>();
        int idx = 0;
        int len = height.length;
        int ret = 0;
        // build stack, during this caculate the popped bar trapped water
        while(idx < len){
            // if stack empty of height[idx] put into stack could kepp stack decreasing
            if(barStack.isEmpty() || height[barStack.peek()] >= height[idx]){
                barStack.push(idx);

                idx++;
            }else{
                // else, pop out one bye one that is higher than height[idx], caculate the trapped water use the popped one as pool base
                int curHeight = height[barStack.pop()];
                // here is a difference with 84, this problem, is only one bar, couldnot trap water
                if(!barStack.isEmpty()){
                    int leftBoundary = barStack.peek();
                    int rightBoundary = idx;
                    int waterHeight = Math.min(height[leftBoundary], height[rightBoundary]) - curHeight;
                    ret += waterHeight * (rightBoundary - leftBoundary - 1);
                }
            }
        }

        return ret;
    }

    // 使用双指针。主要是思路是，基于DP的版本，我们发现，如果index的 左边的柱子最大值 < 右边的柱子的最大值，那么面积与二者中的小者有关，所以我们可以尝试用双指针，从height左右两边开始，如果leftP < rightP，那么面积与leftP有关而与rightP无关，那么具体的面积就是 [0, leftP]中的最大高度 * 1 - height[leftP] * 1。反之同理。
    private int trapByTwoPointers(int[] height){
        int res = 0;
        
        int len = height.length;
        
        // 双指针，左边指针
        int leftP = 0;
        // 双指针，右边指针
        int rightP = len - 1;
        
        // 暂存左边的最大高度
        int leftMax = 0;
        // 暂存右边的最大高度
        int rightMax = height[len - 1];
        while(leftP < rightP){
            // 暂存当前柱子能存贮的水
            int curArea = 0;
            
            // 如果左边的当前小于右边的当前，则当前柱子能保存的水，与自身和自己左边的leftMax有关。
            if(height[leftP] < height[rightP]){
                leftMax = Math.max(leftMax, height[leftP]);
                curArea = leftMax * 1 - height[leftP] * 1;
                leftP++; //记得移动
            }else{
                rightMax = Math.max(rightMax, height[rightP]);
                curArea = rightMax * 1 - height[rightP] * 1;
                rightP--;
            }
            
            res += curArea;
        }
        
        return res;
    }
    
    
    // 用DP来优化trapByBruteForce。主要思路来源是：我们在算每个index柱子能存的水时，都遍历了一遍整个height数组，但是其实这些只遍历一遍然后记录下来就可以
    private int trapByDP(int[] height){
        
        int res = 0;
        
        int len = height.length;
        
        int[] leftMaxs = new int[len];
        int[] rightMaxs = new int[len];
        
        // leftMax来保存当前index柱子之前的最大的一个高度。初始是从height[0]开始
        int leftMax = height[0];
        for(int i = 0; i < len; i++){
            leftMax = Math.max(leftMax, height[i]);
            leftMaxs[i] = leftMax; // 遍历整个数组一遍，就可以通过 当前柱子前面的最大高度 和 当前柱子的高度 进行比较，得到当前柱子对应的[0, index]中最大的高度，存入leftMax[i]，同时更新leftMax
        }
        
        // rightMax来保存当前index柱子之后的最高的一个高度。初始是从height[len-1]开始。
        int rightMax = height[len - 1];
        for(int i = len - 1; i >=0; i--){ // 注意是倒序，i--
            rightMax = Math.max(rightMax, height[i]);
            rightMaxs[i] = rightMax;
        }
        
        // 遍历整个数组，计算每个柱子能保存的水，存入res
        for(int i = 0; i < len; i++){
            res += Math.min(leftMaxs[i], rightMaxs[i]) * 1 - height[i] * 1;
        }
        
        return res;
    }
    
    //原始点的code写法，就是将思路代码化
    private int trapByBruteForce(int[] height){
        int res = 0;
        
        int len = height.length;
        
        // 根据题意和图，最左边和最右边的柱子不可能存水. 所以其实可以让 i 的范围是 int i = 1; i < len -1; i++
        for(int i = 0; i < len; i++){
            int leftMax = 0;
            int rightMax = 0;
            int curTrapArea = 0;
            
            // 找左边到当前index最高的柱子
            for(int j = 0; j <= i; j++){
                leftMax = Math.max(leftMax, height[j]); // 注意这里是height[j]
            }
            // 找右边到当前index最高的柱子
            for(int j = len - 1; j >= i; j--){
                rightMax = Math.max(rightMax, height[j]);// 注意这里是height[j]
            }
            
            // 当前柱子能保存住的水
            curTrapArea = Math.min(leftMax, rightMax) * 1 - height[i] * 1;// 注意这里是height[i]
            
            // 加到总体能加入的水中
            res += curTrapArea;   
        }
        
        return res;
    }
}