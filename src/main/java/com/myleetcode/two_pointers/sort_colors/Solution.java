package com.myleetcode.two_pointers.sort_colors;

class Solution {
    public void sortColors(int[] nums) {
        sortColorsByThreePointers(nums);
    }

    // solution:  Dutch National Flag Problem
    // TC: O(N)
    // SC: O(1)
    // intuition: this problem looks no need to sort, because we only has 3 states in all elems, so we could use the thoughts in partition: we use two pointers startP and endP to points the first not know if it's 0 value and not know if it's 2 value respectively, use curP to traverse the array from startP to endP
    // startP = 0; endP = len-1; curP = 1;
    // while curP <= endP
    // 1 curP is 0, swap with startP, startP++, curP++
    // 2 curP is 2, swap with endP, endP--
    // 3 curP is 1, curP++
    private void sortColorsByThreePointers(int[] nums){
        if(nums == null || nums.length <= 1){
            return;
        }

        /*
!!! curP is start at startP, startP has 3 conditions: 0, 1, 2, if 0 then swap with self and we should startP++ and curP++ no proble; if 1 then curP++ no proble; if 2 then curP swap with endP, but we dont know endP's value so we should not curP++, only do endP--
        */
        int len = nums.length;
        int start = 0;
        int end = len - 1;
        int cur = start;
        while(cur <= end){
            if(nums[cur] == 0){
                nums[cur] = nums[start];
                nums[start] = 0;

                start++;
                cur++;//!!!
            }else if(nums[cur] == 2){
                nums[cur] = nums[end];
                nums[end] = 2;

                end--;
            }else{
                cur++;
            }
        }
    }

}
