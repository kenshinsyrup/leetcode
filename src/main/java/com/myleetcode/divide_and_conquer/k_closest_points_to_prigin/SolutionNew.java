package com.myleetcode.divide_and_conquer.k_closest_points_to_prigin;

import java.util.Random;

public class SolutionNew {
    public int[][] kClosest(int[][] points, int K) {
        //https://leetcode.com/problems/k-closest-points-to-origin/discuss/218691/O(N)-Java-using-Quick-Select(beats-100)
        // special case
        if (points == null || K < 1) {
            return null;
        }

        return kClosestByQuickSelect(points, K);

    }

    private int[][] kClosestByQuickSelect(int[][] points, int K) {
        // 这里的写法是采用不去更改每次findPivotIndexByPartition时的输入数组的方式，还有一种直接每次缩小points的范围的方式的写法，那样的话处理K还要额外考虑对k和begin的增减，比较麻烦了。
        int len = points.length;
        int begin = 0;
        int end = len - 1;

        // 注意，虽然pivotIndex是0based，K是1based，但是这样刚好，因为我们要找到的pivotIndex能只带的是 在pivotIndex之前的所有的元素 都小于等于 pivotIndex这个位置的元素，而pivotIndex之后的元素都大于pivotIndex这个位置的元素，这样一来，pivotIndex前面正好有pivotIndex个元素，比如pivotIndex为1，那么pivotIndex前面正好有一个元素，这个与K的含义相同，所以我们判断的就是K与pivotIndex的关系（而不是K-1与pivotIndex的关系）
        int pivotIndex = 0;
        while (begin <= end) {
            pivotIndex = findPivotIndexByPartition(points, begin, end);
            if (K == pivotIndex) {
                break;
            } else if (K < pivotIndex) {
                end = pivotIndex - 1;
            } else {
                begin = pivotIndex + 1;
            }
        }

        // construct ret
        int[][] ret = new int[K][2];
        for (int i = 0; i < K; i++) {
            ret[i] = points[i];
        }

        return ret;

    }

    private int findPivotIndexByPartition(int[][] points, int start, int end) {
        // 使用random来获取pivot避免总是直接使用end的不足之处
        Random random = new Random();
        int rd = start + random.nextInt(end - start + 1);
        int[] target = points[rd];
        swap(points, rd, end);// 使用random或者median确定pivot，都要和end进行swap，保证pivot必须在末尾

        int left = start, right = end - 1;// righti is end - 1 because end is pivot, we process it after while
        while (left <= right) {
            // '左边'的小于等于pivot
            while (left <= right && !isLarger(points[left], target)) left++;
            // '右边'的大于pivot
            while (left <= right && isLarger(points[right], target)) right--;
            // 注意这里要考虑left <= right, 不合格的数字要swap到正确的半边
            if (left <= right) {
                swap(points, left, right);
                left++;
                right--;
            }
        }

        // after while, we should swap the pivot at end with the 'left' index number because it's the first one 'not no larger than pivot', after swap, we know the part before 'left' index is no larger than pivot, the part after 'left' index is larger than pivot and the 'left' index equals to pivot
        swap(points, left, end);
        return left;
    }

    private void swap(int[][] points, int i1, int i2) {
        int[] temp = points[i1];
        points[i1] = points[i2];
        points[i2] = temp;
    }

    // return true if p1 dist is larger than p2
    private boolean isLarger(int[] p1, int[] p2) {
        return p1[0] * p1[0] + p1[1] * p1[1] > p2[0] * p2[0] + p2[1] * p2[1];
    }
}
