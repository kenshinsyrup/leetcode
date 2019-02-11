package com.myleetcode.divide_and_conquer.k_closest_points_to_prigin;

class Solution {
    public int[][] kClosest(int[][] points, int K) {
        // 参考：https://leetcode.com/problems/k-closest-points-to-origin/discuss/220235/Java-Three-solutions-to-this-classical-K-th-problem.
        // https://leetcode.com/problems/k-closest-points-to-origin/discuss/220326/Python3-Solution-using-QuickSelect-O(NlogN)

//         k closest points to origin(0, 0)
//         这个的divide and conquer思路不是很容易想到。思路的本意是借助quick sort的思路，基于pivot的partition来实现。
//         只不过，quick sort是由pivot开始分成两部分，左边小于等于pivot，右边大于pivot。然后divide左右两边分别继续同样的过程来conquer直到结束。
//         这道题用divide and conquer的思路的话，可以不用这样排序好再取（那就变成了排序了而且复杂度是nlogn），只需要部分排序
//         首先，题目相当于让我们找距离升序的前K个元素。
//         然后，用quick sort的思路，我们随便取一个pivot（最好用median法，不要random），然后将当前数组分为两部分，[0, i] 和 [i+1, len - 1]，第一部分是小于等于pivot的，第二部分是大于pivot的。
//         然后，我们可以看看[0, i]这里的元素数量是否大于K。如果大于等于，那么我们继续排序当前pivot（也就是从i开始）左边的内容。如果小于，那么当前pivot左边的已经可以直接作为结果的一部分了（因为不要去顺序），我们只需要排序当前pivot右边的一部分（凑够K个元素）。
//         最后，取前K个元素即可
        
        // special case
        if(points == null || points.length == 0 || points[0].length == 0){
            return null;
        }
        
        // 这段注释是记录下犯得错误：在没有看清楚题目要求的函数签名下开始写代码，把points转成了distances，结果最后发现题目要求的是返回points相关的内容。
//         // int[][] points converts to int[] distances
//         int len = points.length;
//         int[] distances = new int[len];
//         for(int i = 0; i < len; i++){
//             distances[i] = points[i][0] * points[i][0] + points[i][1]*points[i][1];
//         }
//         partiallyQuickSort(distances, 0, len - 1, K);
//         return Arrays.copyOfRange(points, 0, K);
        
        partiallyQuickSort(points, 0, points.length - 1, K);
        return Arrays.copyOfRange(points, 0, K);
    }
    
    private int distance(int[][] points, int i){
        return points[i][0] * points[i][0] + points[i][1]*points[i][1];
    }
        
    // partially quick sort by d&c
    private void partiallyQuickSort(int[][] points, int start, int end, int K){
        // exit
        if(start >= end){
            return;
        }
        
        // median as pivot
        int mid = start + (end - start) / 2;
        int pivot = findMedian(distance(points, start), distance(points, mid), distance(points, end));
        
        // conquer, 根据pivot进行partition
        int i = start;
        int j = end;
        int temp = 0;
        while(i < j){
            while(i < j && distance(points, i) < pivot){
                i++;
            }
            while(i < j && distance(points, j) > pivot){
                j--;
            }
            swapPoints(points, i, j);
        }
        
        // divide
        // pivot左边不少于K个元素，那么继续处理左边[0, i], 要找的个数还是K
        if(K <= i - start + 1){
            partiallyQuickSort(points, start, i, K);
        }else{
            // pivot左边少于K个元素，那么左边的全要不用再处理了（因为不要求结果排序）。处理右边，同时要找的个数变成了K-左边个数，也就是K-(i - start + 1)个。
            partiallyQuickSort(points, i + 1, end,  K - (i - start + 1));
        }
                               
    }
    
    private void swapPoints(int[][] points, int i, int j){
        int p0 = points[i][0];
        int p1 = points[i][1];
        points[i][0] = points[j][0];
        points[i][1] = points[j][1];
        points[j][0] = p0;
        points[j][1] = p1;
    }
    
    
    private int findMedian(int left, int mid, int right){
        int temp = 0;
        if(left > mid){
            temp = mid;
            mid = left;
            left = temp;
        }
        if(left > right){
            temp = right;
            right = left;
            left = temp;
        }
        if(mid > right){
            temp = right;
            right = mid;
            mid = temp;
        }
        return mid;
    }
}