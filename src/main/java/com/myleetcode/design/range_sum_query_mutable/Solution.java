package com.myleetcode.design.range_sum_query_mutable;

class NumArray {

    /*
    // sol1 Binay Indexed Tree: with BIT like: 308. Range Sum Query 2D - Mutable

    int[] nums;
    int[] bITree;

    public NumArray(int[] nums) {
        if(nums == null || nums.length == 0){
            return;
        }

        int len = nums.length;
        this.nums = new int[len];
        this.bITree = new int[len + 1];

        // build the BITree is simpliy update all
        for(int i = 0; i < len; i++){
            update(i, nums[i]);
        }
    }

    // update the elem at index arrIdx in nums to val
    // update the corresponding elem in BITree
    // BITree elems at index arrIdx+1 to len that has relation with the elem in nums at index arrIdx
    // update, nodeIdx downwards to leaf
    public void update(int arrIdx, int val) {
        int len = this.nums.length;

        // 1 get diff, diff is the update val in BITree
        int diff = val - this.nums[arrIdx];

        // 2 update nums array to val
        this.nums[arrIdx] = val;

        // 3 update BIT with diff
        int nodeIdx = arrIdx + 1;
        while(nodeIdx <= len){
            // add the diff to node at nodeIdx
            this.bITree[nodeIdx] += diff;

            // next nodeIdx that has relation with index arrIdx
            nodeIdx += nodeIdx & (-nodeIdx);
        }
    }

    public int sumRange(int i, int j) {
        return getSum(j) - getSum(i - 1); // here, be aware, leetcode need us sum the nums[i]
    }

    // get sum of [0:arrIdx] in nums
    // in BITree, this range sum is sum all nodes values that has relation with the arrIdx, ie the nodeIdx that in 1 to arrIdx+1 that has relation with the arrIdx
    // sum, nodeIdx upwards to root
    private int getSum(int arrIdx){
        int sum = 0;

        int nodeIdx = arrIdx + 1;
        while(nodeIdx >= 1){
            // sum up the nodeIdx values that has relation with arrIdx
            sum += bITree[nodeIdx];

            // next corresponding nodeIdx
            nodeIdx -= nodeIdx & (-nodeIdx);
        }

        return sum;
    }
    */

    // https://leetcode.com/problems/range-sum-query-mutable/discuss/75724/17-ms-Java-solution-with-segment-tree
    // Build a real Segment Tree
    // sol3 Segment Tree
    class SegmentTreeNode{
        SegmentTreeNode left;
        SegmentTreeNode right;

        int sum;
        int minIdx;
        int maxIdx;
        public SegmentTreeNode(int minIdx, int maxIdx){
            this.minIdx = minIdx;
            this.maxIdx = maxIdx;
        }
    }

    private SegmentTreeNode buildSegmentTree(int[] nums, int start, int end){
        if(start > end){
            return null;
        }

        SegmentTreeNode curNode = new SegmentTreeNode(start, end);
        if(start == end){
            curNode.sum = nums[start];
            return curNode;
        }

        int mid = start + (end - start) / 2;
        SegmentTreeNode left = buildSegmentTree(nums, start, mid);
        SegmentTreeNode right = buildSegmentTree(nums, mid + 1, end);
        curNode.left = left;
        curNode.right = right;
        curNode.sum = left.sum + right.sum;

        return curNode;
    }

    // logN
    private int getRangeSum(SegmentTreeNode node, int queryStart, int queryEnd){
        // null node
        if(node == null){
            return 0;
        }

        // node's range totally out of query range
        if(node.minIdx > queryEnd || node.maxIdx < queryStart){
            return 0;
        }

        // node's range is in the query range
        if(node.minIdx >= queryStart && node.maxIdx <= queryEnd){
            return node.sum;
        }

        // intersection
        return getRangeSum(node.left, queryStart, queryEnd) + getRangeSum(node.right, queryStart, queryEnd);
    }

    // logN
    private void updateSegmentTree(SegmentTreeNode node, int idx, int val){
        if(node == null){
            return;
        }

        if(node.minIdx == node.maxIdx){ // find this leaf node
            node.sum = val;
            this.nums[idx] = val;
        }else{
            // find which child tree has the range including the idx need to be updated
            int mid = node.minIdx + (node.maxIdx - node.minIdx) / 2;
            if(idx <= mid){
                updateSegmentTree(node.left, idx, val);
            }else{
                updateSegmentTree(node.right, idx, val);
            }

            // !!! after update children nodes, remember to update self
            node.sum = node.left.sum + node.right.sum;
        }
    }

    SegmentTreeNode segTreeRoot = null;
    int[] nums;
    public NumArray(int[] nums) {
        if(nums == null || nums.length == 0){
            return;
        }

        this.nums = nums;
        this.segTreeRoot = buildSegmentTree(nums, 0, nums.length - 1);
    }

    public void update(int arrIdx, int val){
        updateSegmentTree(this.segTreeRoot, arrIdx, val);
    }

    public int sumRange(int i, int j){
        if(i > j){
            return 0;
        }

        return getRangeSum(this.segTreeRoot, i, j);
    }

//     // sol2 Segment Tree:
//     int[] nums;
//     int[] segTree; // 使用segmentTree来解，可以达到sum和update均为O(logn)的TC. 这里我们使用array implementation，因为比较好操作，比自己建一个TreeNode去做要方便的多

//     // 总体思路来源于：
// // https://leetcode.com/articles/a-recursive-approach-to-segment-trees-range-sum-queries-lazy-propagation/
// // https://leetcode.com/problems/range-sum-query-mutable/discuss/229167/Java-implementation-of-segment-tree-using-array
//     // 以及 这里很详细的解释和图：https://www.geeksforgeeks.org/segment-tree-set-1-sum-of-given-range/

//     // 让segment tree root node 的index在segTreeArray中为1，这样和segment tree root node在树上index保持一致比较好计算子的index，这样的话，假设当前的node的index为i，那么其左子的index为2*i,其右子的index为左子inde+1也就是2*i + 1.
//     // 关于setment tree的高度，我们设nums的长度为n，那么也就是segment tree的叶节点的个数为n，segment tree一定是full binary tree也就是一个node要么有两个子，要么没有子（注意和heap的不同在于segment tree不一定是complete的，也就是node之间可以有gap）
//     // 对于full binary tree，特点之一是： leaf nodes num - 1 == non leaf nodes num. 所以我们有n个叶节点，那么就有n-1个非叶节点，那么总共就有2n-1个节点,但是这个并不是我们要给segment tree array分配的空间，为了防止越界问题也为了简化写法，我们要分配给他的空间要大，至于具体分配多少，下面有计算
//     public NumArray(int[] nums) {
//         // special case, for some leetcode input like
//         /*
//         ["NumArray"]
//         [[[]]]
//         */
//         if(nums == null || nums.length == 0){
//             return;
//         }

//         this.nums = nums;
//         int len = this.nums.length;

//         // 关于给定了n长度的数据数组，为其制作一个segment tree，需要的空间,标准写法
//         // https://www.geeksforgeeks.org/segment-tree-set-1-sum-of-given-range/
//         /*
//          //Height of segment tree is log2(n), n is the leaf nodes number
//         int height = (int) (Math.ceil(Math.log(len) / Math.log(2)));

//         //Maximum size of segment tree is 2*(2^h) - 1
//         int max_size = 2 * (int) Math.pow(2, height) - 1;

//         segTree = new int[max_size]; // Memory allocation
//         */
//         // 推导下近似值：
//         // https://stackoverflow.com/questions/28470692/how-is-the-memory-of-the-array-of-segment-tree-2-2-ceillogn-1
//         // https://www.hackerearth.com/zh/practice/data-structures/advanced-data-structures/segment-trees/tutorial/
//         // ceil(log2(n)) < log2(n) + 1 =>
//         // 2^(height) < 2^(log2(n) + 1) == 2*n =>
//         // 2 * (int) Math.pow(2, height) - 1 == 2*(2*n) -1 == 4*n - 1 < 4n
//         // 所以一般可以直接用4*n来分配空间，segment tree array不会大于4*n。（实际上，在一种所谓的efficient segment tree写法中，直接使用了极大的内存值来初始化segment tree array来避免越界问题）。

//         this.segTree = new int[4 * len];

//         // segment tree root node index is 1
//         buildSegTree(1, 0, len - 1);

//     }

//     // nodeIdx 为 node 在 segTree上的idx, 其表达的和的范围为nums[start, end]
//     // segTree nodeIdx => nums [start,end]
//     private int buildSegTree(int nodeIdx, int start, int end){
//         // leaf node of Segment Tree contains the data of original given nums
//         if(start == end){
//             this.segTree[nodeIdx] = this.nums[start];
//             return this.segTree[nodeIdx];
//         }

//         // non leaf nodes contains the sum of children nodes
//         int mid = start + (end - start) / 2;
//         int leftChild = buildSegTree(2 * nodeIdx, start, mid);
//         int rightChild = buildSegTree(2 * nodeIdx + 1, mid + 1, end);

//         this.segTree[nodeIdx] = leftChild + rightChild;

//         return  this.segTree[nodeIdx];
//     }

//     public void update(int i, int val) {
//         int len = this.nums.length;
//         if(len == 0){
//             return;
//         }

//         // 从segTree的root节点开始，root node idx is 1 in Segment Tree
//         // segTree的root节点，代表的是nums中sum of [0, n-1]
//         updateUtil(1, 0, len - 1, i, val);

//     }
//     // arrayIdx is the idx in nums that should be updated, 只用来更新nums
//     // val is the new value
//     // nodeIdx is the corresponding idx in segTree to be updated because of the nums update
//     // segStart and segEnd is the start idx and end idx of the range in nums that the current node represents
//     // segTree nodeIdx => nums [start, end]
//     private void updateUtil(int nodeIdx, int start, int end, int arrIdx, int val){
//         // 要更新的叶节点找到了，numArray的元素，都在segTree的叶节点
//         if(start == end){
//             this.nums[arrIdx] = val; // update numArray
//             this.segTree[nodeIdx] = val; // 也要update segTree也就是segTreeArray
//             return;
//         }

//         // 递归, 寻找要更新的叶节点
//         int mid = start + (end - start) / 2;
//         if(arrIdx <= mid){ // 要update的叶节点在当前node的左子树, 当前node的左子node idx为2 * nodeIdx, 左子node代表的范围为[segStart, mid]
//             updateUtil(2 * nodeIdx, start, mid, arrIdx, val);
//         }else{ // 在右子树, 当前node的右子node idx为2 * nodeIdx + 1, 右子node代表的范围为[mid+1, segEnd]
//              updateUtil(2 * nodeIdx + 1, mid + 1, end, arrIdx, val);
//         }

//         // 找到后，我们用 更新后的子 更新自己
//         this.segTree[nodeIdx] = this.segTree[2 * nodeIdx] + this.segTree[2 * nodeIdx + 1];
//     }

//     public int sumRange(int i, int j) {
//         int len = this.nums.length;
//         if(len == 0){
//             return 0;
//         }

//         return getSum(1, 0, len - 1, i, j);
//     }

//     // segTree nodeIdx => nums [start,end]
//     private int getSum(int nodeIdx, int start, int end, int queryStart, int queryEnd){
//         // 如果查询的范围[queryStart, queryEnd]不在当前node所代表的范围[segStart, segEnd]，return0
//         if(queryEnd < start || queryStart > end){
//             return 0;
//         }

//         // 如果查询的范围 包含了 当前node所代表的范围，说明当前的node所代表的值我们需要
//         if(queryStart <= start && queryEnd >= end){
//             return this.segTree[nodeIdx];
//         }

//         // 如果不是前两者，就是存在交叉，那么分割向下，直到满足上面两个条件
//         // 计算出当前node的两个（或0个）子的node index，segStart和segEnd
//         int mid = start + (end - start) / 2;
//         int leftChild = getSum(2 * nodeIdx, start, mid, queryStart, queryEnd);
//         int rightChild = getSum(2 * nodeIdx + 1, mid + 1, end, queryStart, queryEnd);

//         return leftChild + rightChild;
//     }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */