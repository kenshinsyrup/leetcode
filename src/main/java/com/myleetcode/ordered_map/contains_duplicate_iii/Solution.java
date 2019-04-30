package com.myleetcode.ordered_map.contains_duplicate_iii;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        // return containsNearbyAlmostDuplicateByTraverse(nums, k, t);
        return containsNearbyAlmostDuplicateByBST(nums, k, t);
        // return containsNearbyAlmostDuplicateByBucketSort(nums, k, t);
    }

    // use Bucket Sort to reach O(N) TC
    // https://leetcode.com/problems/contains-duplicate-iii/discuss/61645/AC-O(N)-solution-in-Java-using-buckets-with-explanation
    private boolean containsNearbyAlmostDuplicateByBucketSort(int[] nums, int k, int t){
        if(nums == null || nums.length == 0){
            return false;
        }
        if(k < 1 || t < 0){
            return false;
        }

        // buckets
        Map<Long, Long> bucketsMap = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            // process the num to avoid negative num
            long processedNum = (long)nums[i] - Integer.MIN_VALUE;
            long bucketID = processedNum / ((long)t + 1); // bucketsMap, every bucket contains the values that distance is less than t+1

            // already have elem in this bucket, so we have get two elems that are valid
            if(bucketsMap.containsKey(bucketID)){
                return true;
            }
            // checkt the left adjacent bucket, this is the closest bucket in left
            if(bucketsMap.containsKey(bucketID - 1) && processedNum - t <= bucketsMap.get(bucketID - 1)){
                return true;
            }
            // check the right adjacent bucket, this is the closest bucket in right
            if(bucketsMap.containsKey(bucketID + 1) && processedNum + t >= bucketsMap.get(bucketID + 1)){
                return true;
            }

            // put current bucket in bucketsMap, if bucketsMap size reach k, remove the remotest bucket cause we only need check k range
            bucketsMap.put(bucketID, processedNum);
            if(bucketsMap.size() > k){
                long remotestBucketID = ((long)nums[i - k] - Integer.MIN_VALUE) / ((long)t + 1);
                bucketsMap.remove(remotestBucketID);
            }
        }

        return false;

    }

    // use BST we could reach O(N*log(min(N,K))) TC
    // https://leetcode.com/problems/contains-duplicate-iii/discuss/61655/Java-O(N-lg-K)-solution
    private boolean containsNearbyAlmostDuplicateByBST(int[] nums, int k, int t){
        if(nums == null || nums.length == 0){
            return false;
        }

        TreeSet<Long> BSTree = new TreeSet<>();
        for(int i = 0; i < nums.length; i++){
            // check the biggest elem in left subtree, ie the predecessor
            Long predecessor = BSTree.floor((long)nums[i]);
            // if in the t range, true
            if(predecessor != null && ((long)nums[i] - t) <= predecessor){
                return true;
            }
            // check the smallest elem in right subtree, ie the successor
            Long successor = BSTree.ceiling((long)nums[i]);
            // if in the t range, true
            if(successor != null && ((long)nums[i] + t) >= successor){
                return true;
            }

            // add to BST
            BSTree.add((long)nums[i]);

            // keep the BST in k range
            if(BSTree.size() > k){
                BSTree.remove((long)nums[i - k]);
            }
        }

        return false;
    }


    // TC: O(N^2)
    // SC: O(1)
    // intuition: traverse the nums, i from 0, then nest a for loop start with i+1 to i+k, and chekc the nums[i] and nums[j] if less than t return true
    // distance should use long to avoid overflow
    private boolean containsNearbyAlmostDuplicateByTraverse(int[] nums, int k, int t){
        if(nums == null || nums.length == 0){
            return false;
        }

        for(int i = 0; i < nums.length; i++){
            for(int j = i + 1; j <= i + k; j ++){
                if(j > nums.length - 1){
                    break;
                }
                long dist = Math.abs((long)nums[i] - (long)nums[j]);
                if(dist <= t){
                    return true;
                }
            }
        }

        return false;
    }
}
