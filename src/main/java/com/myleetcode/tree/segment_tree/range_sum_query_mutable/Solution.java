package com.myleetcode.tree.segment_tree.range_sum_query_mutable;

class NumArray {

    int[] numArray;
    int[] segTreeArray; // 使用segmentTree来解，可以达到sum和update均为O(logn)的TC. 这里我们使用array implementation，因为比较好操作，比自己建一个TreeNode去做要方便的多

    // 总体思路来源于： https://leetcode.com/problems/range-sum-query-mutable/discuss/229167/Java-implementation-of-segment-tree-using-array
    // 以及 这里很详细的解释和图：https://www.geeksforgeeks.org/segment-tree-set-1-sum-of-given-range/

    // 让segment tree root node 的index在segTreeArray中为1，这样和segment tree root node在树上index保持一致比较好计算子的index，这样的话，假设当前的node的index为i，那么其左子的index为2*i,其右子的index为左子inde+1也就是2*i + 1.
    // 关于setment tree的高度，我们设nums的长度为n，那么也就是segment tree的叶节点的个数为n，segment tree一定是full binary tree也就是一个node要么有两个子，要么没有子（注意和heap的不同在于segment tree不一定是complete的，也就是node之间可以有gap）
    // 对于full binary tree，特点之一是： leaf nodes num - 1 == non leaf nodes num. 所以我们有n个叶节点，那么就有n-1个非叶节点，那么总共就有2n-1个节点,但是这个并不是我们要给segment tree array分配的空间，为了防止越界问题也为了简化写法，我们要分配给他的空间要大，至于具体分配多少，下面有计算
    public NumArray(int[] nums) {
        // special case, for some leetcode input like
        /*
        ["NumArray"]
        [[[]]]
        */
        if(nums == null || nums.length == 0){
            return;
        }

        numArray = nums;
        int len = numArray.length;

        // 关于给定了n个叶节点，制作一个segment tree，需要的空间,标准写法
        // https://www.geeksforgeeks.org/segment-tree-set-1-sum-of-given-range/
        /*
         //Height of segment tree is log2(n), n is the leaf nodes number
        int height = (int) (Math.ceil(Math.log(len) / Math.log(2)));

        //Maximum size of segment tree is 2*(2^h) - 1
        int max_size = 2 * (int) Math.pow(2, height) - 1;

        segTreeArray = new int[max_size]; // Memory allocation
        */
        // 推导下近似值：
        // https://stackoverflow.com/questions/28470692/how-is-the-memory-of-the-array-of-segment-tree-2-2-ceillogn-1
        // https://www.hackerearth.com/zh/practice/data-structures/advanced-data-structures/segment-trees/tutorial/
        // ceil(log2(n)) < log2(n) + 1 =>
        // 2^(height) < 2^(log2(n) + 1) == 2*n =>
        // 2 * (int) Math.pow(2, height) - 1 == 2*(2*n) -1 == 4*n - 1 < 4n
        // 所以可以直接用4*n来分配空间，segment tree array不会大于4*n。（实际上，在一种所谓的efficient segment tree写法中，直接使用了极大的内存值来初始化segment tree array来避免越界问题）。

        segTreeArray = new int[4*len];

        // segment tree root node index is 1
        buildSegTree(0, len - 1, 1);

    }

    private int buildSegTree(int start, int end, int nodeIdx){

        if(start == end){
            segTreeArray[nodeIdx] = numArray[start];
            return segTreeArray[nodeIdx];
        }

        int mid = start + (end - start) / 2;
        int leftChild = buildSegTree(start, mid, 2*nodeIdx);
        int rightChild = buildSegTree(mid + 1, end, 2*nodeIdx + 1);

        segTreeArray[nodeIdx] = leftChild + rightChild;

        return  segTreeArray[nodeIdx];
    }

    public void update(int i, int val) {
        int len = numArray.length;
        if(len == 0){
            return;
        }

        // 从segTree的root节点开始，也就是segTreeArray中0位置的元素
        // segTree的root节点，表达的范围是numArray的[0, n-1]
        updateUtil(1, 0, len - 1, i, val);

    }

    private void updateUtil(int nodeIdx, int segStart, int segEnd, int updateIdx, int updateVal){
        // 要更新的叶节点找到了，numArray的元素，都在segTree的叶节点
        if(segStart == segEnd){
            numArray[updateIdx] = updateVal; // update numArray
            segTreeArray[nodeIdx] = updateVal; // 也要update segTree也就是segTreeArray
        }else{
            // 向下递归寻找要更新的叶节点
            int mid = segStart + (segEnd - segStart) / 2;
            if(updateIdx <= mid){ // 要update的叶节点在当前node的左子树
                updateUtil(2*nodeIdx, segStart, mid, updateIdx, updateVal);
            }else{
                updateUtil(2*nodeIdx + 1, mid + 1, segEnd, updateIdx, updateVal);
            }

            // 找到后，我们更新用更新后的子更新自己
            segTreeArray[nodeIdx] = segTreeArray[2*nodeIdx] + segTreeArray[2*nodeIdx + 1];
        }
    }

    public int sumRange(int i, int j) {
        int len = numArray.length;
        if(len == 0){
            return 0;
        }

        return getSum(1, 0, len - 1, i, j);
    }

    private int getSum(int nodeIdx, int segStart, int segEnd, int queryStart, int queryEnd){
        // 如果查询的范围[queryStart, queryEnd]不在当前node所代表的范围[segStart, segEnd]，return0
        if(queryEnd < segStart || queryStart > segEnd){
            return 0;
        }

        // 如果查询的范围 包含了 当前node所代表的范围，说明当前的node所代表的值我们需要
        if(queryStart <= segStart && queryEnd >= segEnd){
            return segTreeArray[nodeIdx];
        }

        // 如果不是前两者，就是存在交叉，那么分割向下，直到满足上面两个条件
        // 计算出当前node的两个（或0个）子的node index，segStart和segEnd
        int mid = segStart + (segEnd - segStart) / 2;
        int leftChild = getSum(2*nodeIdx, segStart, mid, queryStart, queryEnd);
        int rightChild = getSum(2*nodeIdx + 1, mid + 1, segEnd, queryStart, queryEnd);

        return leftChild + rightChild;
    }
}

/*
class NumArray {

    int[] numArray;
    int[] segmentArray;

    public NumArray(int[] nums) {
        if(nums == null){
            return;
        }

        numArray = nums;
        segmentArray = new int[numArray.length + 1];
        for(int i = 0; i < nums.length; i++){
            segmentArray[i + 1] = segmentArray[i] + nums[i];
        }
    }

    public void update(int i, int val) {
        if(i < 0 || i >= numArray.length){
            return;
        }

        numArray[i] = val;
        for(int j = i; j < numArray.length; j++){
            segmentArray[j + 1] = segmentArray[j] + numArray[j];
        }

    }

    public int sumRange(int i, int j) {
        if(j < i || i < 0 || j >= numArray.length){
            return -1;
        }

        return segmentArray[j + 1] - segmentArray[i];
    }
}
*/
/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */
