package com.myleetcode.hash_table.longest_harmonious_subsequence;

class Solution {
    public int findLHS(int[] nums) {
//         1 最初看起来很像是subset问题：dfs找到所有的subset，再从找到是harmonious的，再从中找个最长的。但是这样很明显有点太过于复杂了对这个题目来说，而且确实会超时。
//         2 对于数组问题，经常要考虑的就是，如果我把他排序了会不会好点。这个题目就是这样，看一下harmonious的定义，很明显如果排序了，对于找harmonious的subset来说就容易多了，而且题目只要求输出个数。先排序，然后从0开始遍历，用pre_count记录获得的相同的值比，然后当不同了，并且差值为1的时候用count来记录这个值，当再次不同的时候，我们就得到了第一个harmonious的长度就是precount + count，然后这一次的查找完成了。那么结下来从第三个值处开始遍历，同时此时的precount被赋值为count。
//         3 用hashmap，也就是该题目的归类。既然只要长度，那么我们把数组中的数值作为key，然后遍历数组，数出来的数值的个数作为value。然后遍历map的key，如果某个key，其key+1也是一个key，那么这两个key当然就组成来一个harmonious，这样选一个最大的出来就可以

        // special case
        if(nums == null || nums.length == 0){
            return 0;
        }

        return findLHSByMap(nums);
    }

    private int findLHSByMap(int[] nums){
        Map<Integer, Integer> valueNums = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            if(valueNums.containsKey(nums[i])){
                int num = valueNums.get(nums[i]);
                num++;
                valueNums.put(nums[i], num);
            }else{
                valueNums.put(nums[i], 1);
            }
        }

        int max = Integer.MIN_VALUE;
        int count = 0;
        for(int k: valueNums.keySet()){
            if(valueNums.containsKey(k+1)){
                count = valueNums.get(k) + valueNums.get(k + 1);
            }

            max = Math.max(max, count);
        }

        return max;
    }

}