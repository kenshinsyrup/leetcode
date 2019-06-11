package com.myleetcode.priority_queue.find_k_pairs_with_smallest_sums;

import java.util.*;

class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        // return kSmallestPairsByBS(nums1, nums2, k);
        return kSmallestPairsByPQ(nums1, nums2, k);
    }

    // solution 1: with BS
    // failed to solve this with BS based on this discussion: https://leetcode.com/problems/find-k-th-smallest-pair-distance/discuss/109082/Approach-the-problem-using-the-%22trial-and-error%22-algorithm

    // solution 2: with Heap
    // TC: O(k*log(k))
    // SC: O(M*N)
    // this is more intuitive
    // just like 378. Kth Smallest Element in a Sorted Matrix
    private class Point{
        int x;
        int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private List<List<Integer>> kSmallestPairsByPQ(int[] nums1, int[] nums2, int k){
        List<List<Integer>> ret = new ArrayList<>();

        if(nums1 == null || nums1.length == 0 | nums2 == null || nums2.length == 0){
            return ret;
        }
        if(k <= 0){
            return ret;
        }

        PriorityQueue<Point> pointPQ = new PriorityQueue<>(new Comparator<Point>(){
            // ascending by sum
            public int compare(Point p1, Point p2){
                return (nums1[p1.x] + nums2[p1.y]) - (nums1[p2.x] + nums2[p2.y]);
            }
        });

        // this problem, different with 378, we should use a boolean[][] to record which pairs we have processed
        boolean[][] visited = new boolean[nums1.length][nums2.length];

        pointPQ.offer(new Point(0, 0));
        visited[0][0] = true;

        while(k >= 1 && !pointPQ.isEmpty()){
            Point curPoint = pointPQ.poll();
            k--;

            // add to ret
            ret.add(new ArrayList<>(Arrays.asList(nums1[curPoint.x], nums2[curPoint.y])));

            // offer two next neighbors:
            if(curPoint.x + 1 < nums1.length && !visited[curPoint.x + 1][curPoint.y]){
                pointPQ.offer(new Point(curPoint.x + 1, curPoint.y));
                visited[curPoint.x + 1][curPoint.y] = true;
            }
            if(curPoint.y + 1 < nums2.length && !visited[curPoint.x][curPoint.y + 1]){
                pointPQ.offer(new Point(curPoint.x, curPoint.y + 1));
                visited[curPoint.x][curPoint.y + 1] = true;
            }
        }

        return ret;
    }
}
