package com.myleetcode.array.first_missing_positive;

class Solution {
    public int firstMissingPositive(int[] nums) {
        return firstMissingPositiveBySwapSort(nums);
    }
    // TC: O(N), 虽然有for-while的嵌套循环，粗略说O(N^2)，但是实际上，由于while的逻辑判断，我们只会每次把一个需要的数字放到需要的位置，之后我们就再也不会再操作他，所以实际上我们至多只操作n个数字就结束了for-while循环，所以精确来说是O(N),这样满足要求的线性时间
    // SC: O(1)
    // https://leetcode.com/problems/first-missing-positive/discuss/17083/O(1)-space-Java-Solution
    //https://leetcode.windliang.cc/leetCode-41-First-Missing-Positive.html
    // 思路比较神奇：首先我们希望能把nums排序，但是不能用Arrays.sort()因为这个是O(N*log(N)).这个nums数组本身比较特殊，假设长度是n,那么index就是0:n-1,里面存储的数字正常情况下是1:n,只不过现在其中一个数字现在变成了一个其他的数字比如0或者-1或者2(也就是2多了一个)等
    // 那么利用nums数组中index和value的这个性质，我们可以把所有的value放到和index+1相同的位置，就是把value 1放到index 0，value 2放到 index 1。。。这样，最后放完之后，我们再额外循环判定一个nums中每个index与value的对应性，如果遇到任何一个value不等于index+1的，那么这个index+1就是缺少的
    // 上面的思路如果用额外的一个数组来处理很容易，但是这个题目要求的是O(1)空间，那么我们就需要在原数组上做文章，那么我们可以做交换，我们遍历数组for(i=0:n-1)，每发现一个数字nums[i]>=1且nums[i]<=n,也就是说nums[i]是1:n之间的数字，说明他不是乱入的数字，我们就把他放到他该去的地方，由于value == index+1，那么value nums[i]该去的index就是nums[i] -1,但这个地方肯定有其他的数字，那么我门对他做交换就好了，这样我们每做一次交换，我们的nums中就至少有一个数字处在自己正确的位置上，而处在正确位置上的数字肯定不会再被别人交换了
    // 这里有一个需要注意的点是，由于nums中可能存在1:n之间的重复数字，也就是乱入的数字也在1:n之间，那么上面的判断条件还需要额外处理以下value nums[i]与要去的index nums[i] -1中的数字也就是nums[index]也就是nums[nums[i] - 1]相同的情况，如果相同，我们就不做交换了。如果没有这个判断，那么会陷入死循环
    private int firstMissingPositiveBySwapSort(int[] nums){
        if(nums == null){
            return 0;
        }
        if(nums.length == 0){ //!!!空数组，说明index 0处无值，缺1
            return 1;
        }

        int len = nums.length;
        int n = len;
        // 现有的数字是0到n-1
        for(int i = 0; i < n; i++){
            // 我们要排序的数字是1:n
            // 我们要交换的两个数字就是 nums[nums[i] - 1] 和 nums[i]
            while(nums[i] >=1 && nums[i] <= n && (nums[i] != nums[nums[i] - 1])){
                // ！！！注意直接这样交换是错误的，因为第二行的时候已经把nums[i]改变了，第三行的nums[i]取的已经是改变后的值而不是原始的值了
                // int temp = nums[i];
                // nums[i] = nums[nums[i] - 1];
                // nums[nums[i] - 1] = temp;

                // 这样，保持住要交换的两个index，然后再交换，这里sourceIdx是不必要的，只是看起来清晰
                int sourceIdx = i;
                int targetIdx = nums[i] -1;
                int temp = nums[sourceIdx];
                nums[sourceIdx] = nums[targetIdx];
                nums[targetIdx] = temp;
            }
        }

        // 找nums[i]不等于i+1的就是缺少的数字
        for(int i = 0; i < n; i++){
            if(nums[i] != i + 1){
                return i + 1;
            }
        }

        // 针对形如[1]或者[1,2,3]这样看起来已经很完美的nums，那么"缺少的"第一个数字就是长度+1的数字
        return n + 1;
    }
}
