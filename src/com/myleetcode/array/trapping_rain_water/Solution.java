package com.myleetcode.array.trapping_rain_water;

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
        
        // 终极的优化，使用双指针
        return trapByTwoPointers(height);
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