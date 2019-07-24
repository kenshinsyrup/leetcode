package com.myleetcode.divide_and_conquer.count_of_range_sum;

class Solution {
    public int countRangeSum(int[] nums, int lower, int upper) {
        // return countRangeSumByPresum(nums, lower, upper); // O(N^2), not good, need better than O(N^2)
        // return countRangeSumBySegmentTree(nums, lower, upper);// dont use this sol

        return countRangeSumByDnD(nums, lower, upper);
    }

    /*
    出错点:
    1. 重要,代码中
    不要试图初始化upperIdx为end
    //  int upperIdx = end;
    然后在loop中用这个判断去找第一个合法的uppIdx
    //  find the correct upperIdx
        while(upperIdx >= mid + 1 && presums[upperIdx] - presums[leftIdx] > upper){
            upperIdx--;
        }
    然后在最后这样计算结果
    //  ret.count += upperIdx - lowerIdx + 1;
    
    这个写法看起来和现在初始化为mid+1然后upperIdx++去查找第一个不满足<=upper的索引一样，但实际是不一样的。原因是我们的upperIdx在整个for对i的loop中，只初始化那一次，由于left 和right part都是升序，我们的presums[upperIdx]-presums[i]>upper时将upperIdx--,那么当下一次for loop中leftIdx增加导致presums[leftIdx]增加时，我们是有可能错过了对于这个leftIdx来说合法当upperIdx因为我们将他在上一轮for loop变得过小了
    */

    // Divide and Conquer
    // similar: 315. Count of Smaller Numbers After Self
    // https://leetcode.com/problems/count-of-range-sum/discuss/77990/Share-my-solution
    // readable codes: https://leetcode.com/problems/count-of-range-sum/discuss/77990/Share-my-solution/82556
    // TC: O(N * logN), logN recursion depth, N to merge
    // SC: O(N), logN recursion depth, N to store presums and sorted presums
    private class Result{
        int count;

        public Result(int count){
            this.count = count;
        }
    }

    private int countRangeSumByDnD(int[] nums, int lower, int upper){
        if(nums == null || nums.length == 0){
            return 0;
        }

        // get the presums array
        int len = nums.length;
        long[] presums = new long[len + 1];
        for(int i = 1; i < presums.length; i++){
            presums[i] = presums[i - 1] + nums[i - 1];
        }

        Result ret = new Result(0);
        mergesort(presums, 0, presums.length - 1, new long[len + 1], lower, upper, ret);

        return ret.count;
    }

    private void mergesort(long[] presums, int start, int end, long[] sortedPresums, int lower, int upper, Result ret){
        if(start >= end){
            return;
        }

        int mid = start + (end - start) / 2;
        mergesort(presums, start, mid, sortedPresums, lower, upper, ret);
        mergesort(presums, mid + 1, end, sortedPresums, lower, upper, ret);

        merge(presums, start, mid, end, sortedPresums, lower, upper, ret);
    }

    private void merge(long[] presums, int start, int mid, int end, long[] sortedPresums, int lower, int upper, Result ret){
        // right part idx
        int rightIdx = mid + 1;
        // sortedPresums idx
        int idx = start;

        // to find the range of [lowerIdx: upperIdx-1] for i that satisfy for j in this range, we have presums[j]-presums[i]>=lower&&<=uuper
        int lowerIdx = mid + 1;
        int upperIdx = mid + 1;

        // leftIdx is left part idx
        for(int leftIdx = start; leftIdx <= mid; leftIdx++){
            // find the correct lowIdx
            while(lowerIdx <= end && presums[lowerIdx] - presums[leftIdx] < lower){
                lowerIdx++;
            }

            // find the correct upperIdx
            while(upperIdx <= end && presums[upperIdx] - presums[leftIdx] <= upper){
                upperIdx++;
            }

            // store presums by ascending order to sortedPresums
            while(rightIdx <= end && presums[rightIdx] < presums[leftIdx]){
                sortedPresums[idx] = presums[rightIdx];

                rightIdx++;
                idx++;
            }
            sortedPresums[idx] = presums[leftIdx];
            idx++;

            // [lowerIdx:upperIdx-1] is the valid range: sums[j] - sums[i] <= upper && sums[j] - sums[i] >= lower, j > i
            if(lowerIdx < upperIdx){
                ret.count += (upperIdx - 1) - lowerIdx + 1;
            }
        }
        // remember to check if rightIdx exhausted end
        while(rightIdx <= end){
            sortedPresums[idx] = presums[rightIdx];

            rightIdx++;
            idx++;
        }

        // after process current range, make the current range presums array sorted
        for(int i = start; i <= end; i++){
            presums[i] = sortedPresums[i];
        }
    }

    /*
    This is a Segment Tree Sol but the TC is not necessary O(NlogN), it should be considered as O(N^2) I think. Dont use this.
    // thought:
    // https://leetcode.com/problems/count-of-range-sum/discuss/77987/Java-SegmentTree-Solution-36ms/82526
    // https://leetcode.com/problems/count-of-range-sum/discuss/77987/Java-SegmentTree-Solution-36ms
    private int countRangeSumBySegmentTree(int[] nums, int lower, int upper){
        if(nums == null || nums.length == 0){
            return 0;
        }

        Set<Long> presumSet = new HashSet<>();
        long presum = 0;
        presumSet.add(presum);
        for(int i = 0; i < nums.length; i++) {
            presum += nums[i];

            presumSet.add(presum);
        }

        int len = presumSet.size();
        long[] presums = new long[len];
        int i = 0;
        for(long v: presumSet){
            presums[i] = v;

            i++;
        }
        Arrays.sort(presums);

        int ret = 0;
        SegmentTreeNode segTreeRootNode = buildSegmentTree(presums, 0, len - 1);

// Cause we need to find previous prefixSum that is:

// lower <= currentPrefixSum - previousPrefixSum <= upper
// ===>
// currentPrefixSum-upper <= previousPrefixSum <= currentPrefixSum-lower

// updateSegmentTree(root, sum); will add previousPrefixSum into counts.

// Then query the segment tree to get how many previousPrefixSums that are within the required range.

        presum = 0;
        for(int j = 0; j < nums.length; j++){
            updateSegmentTreeNode(segTreeRootNode, presum);

            presum += nums[j];
            ret += getCount(segTreeRootNode, (long)presum - (long)upper, (long)presum - (long)lower);
        }

        return ret;

    }

    // Segment Tree
    private class SegmentTreeNode{
        SegmentTreeNode left;
        SegmentTreeNode right;
        long minVal;
        long maxVal;

        int count; // marks how many sub ranges under this node.

        public SegmentTreeNode(long minVal, long maxVal){
            this.minVal = minVal;
            this.maxVal = maxVal;
        }
    }

    // key part, update count variable of segTreeNode
    private void updateSegmentTreeNode(SegmentTreeNode node, long val){
        if(node == null){
            return;
        }

        if(node.minVal <= val && node.maxVal >= val){
            node.count++;

            updateSegmentTreeNode(node.left, val);
            updateSegmentTreeNode(node.right, val);
        }
    }

    // same like getRangeSum, this problem we need the count
    private int getCount(SegmentTreeNode node, long minVal, long maxVal){
        // node totally out of query range
        if(node.minVal > maxVal || node.maxVal < minVal){
            return 0;
        }

        // node in query range
        if(node.minVal >= minVal && node.maxVal <= maxVal){
            return node.count;
        }

        // node cover query range, recursion
        int leftCount = getCount(node.left, minVal, maxVal);
        int rightCount = getCount(node.right, minVal, maxVal);
        return leftCount + rightCount;
    }

    // build the segment tree, return root node
    private SegmentTreeNode buildSegmentTree(long[] nums, int start, int end){
        // null node
        if(start > end){
            return null;
        }

        // leaf node
        if(start == end){
            return new SegmentTreeNode(nums[start], nums[end]);
        }

        // normal node
        SegmentTreeNode segTreeNode = new SegmentTreeNode(nums[start], nums[end]);
        int mid = start + (end - start) / 2;
        SegmentTreeNode left = buildSegmentTree(nums, start, mid);
        SegmentTreeNode right = buildSegmentTree(nums, mid + 1, end);
        segTreeNode.left = left;
        segTreeNode.right = right;

        return segTreeNode;
    }
*/
    // naive sol, this problem need better sol
    // intuition: Presum with nested loop
    // TC: O(N^2)
    // SC: O(N)
    private int countRangeSumByPresum(int[] nums, int lower, int upper){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        long[] presums = new long[len + 1]; // long to avoid overflow
        for(int i = 1; i < presums.length; i++){
            presums[i] = presums[i - 1] + nums[i - 1];
        }

        int count = 0;
        for(int i = 0; i < presums.length; i++){
            for(int j = 0; j < i; j++){
                if(presums[i] - presums[j] >= (long)lower && presums[i] - presums[j] <= (long)upper){
                    count++;
                }
            }
        }

        return count;
    }
}
