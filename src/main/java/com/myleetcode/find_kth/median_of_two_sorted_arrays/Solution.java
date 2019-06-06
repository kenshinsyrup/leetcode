package com.myleetcode.find_kth.median_of_two_sorted_arrays;

public class Solution {


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
//        return findMedianSortedArraysI(nums1, nums2);
        return findMedianSortedArraysByKth(nums1, nums2);
    }

    // TC: O(log(M + N))
    // SC: O(log(M + N))
    // solution: https://leetcode.com/problems/median-of-two-sorted-arrays/discuss/2496/Concise-JAVA-solution-based-on-Binary-Search
    // this problem could be solved by the thought of: Find The Kth Smallest Number
    // according the Median Num definition: the median is used for dividing a set into two equal length subsets, that one subset is always greater than the other
    // so the Median Num is actually Find The Kth Smallest Num in Array problem, where k is the (nums1Len + nums2Len + 1) / 2 and the (nums1Len + nums2Len + 2) / 2, and the Median Num is the average of this two pos(not index, index is k-1 in kth problem)
    private double findMedianSortedArraysByKth(int[] nums1, int[] nums2){
        if(nums1 == null && nums2 == null || nums1.length == 0 && nums2.length == 0){
            return 0.0;
        }

        if(nums1 == null || nums1.length == 0){
            int len2 = nums2.length;
            if(len2 %2 == 0){
                return (nums2[(len2 - 1) / 2] + nums2[len2 / 2]) / 2.0;
            }else{
                return 1.0 * nums2[len2 / 2];
            }
        }

        if(nums2 == null || nums2.length == 0){
            int len1 = nums1.length;
            if(len1 %2 == 0){
                return (nums1[(len1 - 1) / 2] + nums1[len1 / 2]) / 2.0;
            }else{
                return 1.0 * nums1[len1 / 2];
            }
        }

        int len1 = nums1.length;
        int len2 = nums2.length;
        // leftK means the kth smallest num in left of median, rightK means the kth smallest num in right of median
        // if len1+len2 is odd, then leftK equals to rightK equals to the k equals to (len1+len2+1)/2, so the median is nums[k-1], and is (nums[leftK-1]+nums[rightK-1])/2
        // if len1+len2 is even, then leftK is the first num smaller than median, the rightK is the first num larger than median, the average of the leftK-th num and the rightK-th num is the median, so median is (nums[leftK-1]+nums[rightK-1])/2
        // by this way, use one formula, we dont need to consider the len1+len2 is odd or even
        int leftK = (len1 + len2 + 1) / 2;
        int rightK = (len1 + len2 + 2) / 2;

        return (findKth(nums1, 0, nums2, 0, leftK) + findKth(nums1, 0, nums2, 0, rightK)) / 2.0;
    }

    // nums1 and nums2 are sorted array, kth is the kth num in the combined and sorted array
    // this func find the kth elem in the array nums1+nums2
    private double findKth(int[] nums1, int start1, int[] nums2, int start2, int k){
        // If nums1 is exhausted, return kth number in nums2
        if(start1 >= nums1.length){
            return nums2[start2 + k - 1] * 1.0;
        }

        // If nums2 is exhausted, return kth number in nums1
        if(start2 >= nums2.length){
            return nums1[start1 + k - 1] * 1.0;
        }

        // if nums1 and nums2 are both not exhausted, if k is already 1, then the min value of nums1[start1] and nums2[start] is the kth(1th) num in the combined array
        if(k == 1){
            return Math.min(nums1[start1], nums2[start2]) * 1.0;
        }

        // otherwise, discard the invalid half
        // 1: get the middle value of nums1, get the middle value of nums2
        int nums1Mid = Integer.MAX_VALUE;
        int nums2Mid = Integer.MAX_VALUE;
        if(start1 + k / 2 - 1 < nums1.length){
            nums1Mid = nums1[start1 + k / 2 - 1];
        }
        if(start2 + k / 2 - 1 < nums2.length){
            nums2Mid = nums2[start2 + k / 2 - 1];
        }
        // 2: if middle1 < middle2, meand the left of middel1 is impossilbe to be the kth elem in the combined array; otherwise meand the left of middle2 is impossible
        if(nums1Mid < nums2Mid){
            // return findKth(nums1, start1 + k / 2 - 1 + 1, nums2, start2, k - k / 2);
            return findKth(nums1, start1 + k / 2, nums2, start2, k - k / 2);
        }else{
            return findKth(nums1, start1, nums2, start2 + k / 2, k - k / 2);
        }
    }


    // 重新做了一下这道题，虽然还是没有做出来233，但是在评论区找到了一个相对更容易想到的解
    // 首先，看到log(m+n)我们就知道这个肯定要用二分法做，所以，我们用二分法做
    // 注意，这个解法最困扰的有三点：
    // 1、medianLeft处len/2以及medianRight处len/2+1以及median处len/2+1，这是同一个困扰，总结就是，要知道findLastNumInMixedArray中k这个参数是什么意思
    // 可以查到的很多解法，findLastNumInMixedArray方法写作了类似findKthNum(, , , , int k)。这样导致了极大的困扰，表现为：
    // a.为什么在方法内部要一直让k-1如果k是index的话；
    // b.为什么求medianLeft时传入的是len/2而不是len/2-1,为什么求medianRight时传入的是len/2+1而不是len/2，因为我们知道后面这个才是真正的index
    // 所以，这里要明确一个点，参数k是混合部分的size，我们要找的是这个size中的最右边的数字
    // 2、相对没那么复杂，就是为什么最后我们排除掉了k/2个数字之后，剩下的混合数组长度是k-k/2。这个主要是整数除法地板除的问题，整数除法中,k/2不一定等于k-k/2
    private double findMedianSortedArraysI(int[] nums1, int[] nums2){
        double median = 0.0;

        //special case
        if(nums1 == null || nums2 == null){
            return median;
        }

        // nums1 != null && nums2 != null
        int len = nums1.length + nums2.length;

        if(len % 2 == 0){
            // len is even
            // 关于为什么要用len/2和len/2+1
            // 在len是偶数时，median一定是mixed array的中间的两个数的平均值。所以我们要找的是中间两个数字，就是medianLeft和medianRight
            // 我们的辅助函数可以帮我们找到一个指定size的数组中的最后一个数字，那么我们为了找到medianLeft和medianRight,就需要两个区间，一个区间最后一个元素是medianLeft(也就是[0,len/2-1]区间，所以数组size是len/2)，一个区间最后一个元素是medianRight(也就是[0,len/2]区间,所以数组size是len/2+1)
            double medianLeft = (double)findLastNumInMixedArray(nums1, nums2, 0, 0, len/2);
            double medianRight = (double)findLastNumInMixedArray(nums1, nums2, 0, 0, len/2 + 1);
            median = (double)(medianLeft + medianRight) / 2;
        }else{
            // len is odd
            // 同理，需要的区间是[0,len/2],所以size是len/2+1
            median = (double)findLastNumInMixedArray(nums1, nums2, 0, 0, len/2 + 1);
        }

        return median;
    }


    // k is size, is the number of elements, so the index of the last num in k-size array is k-1
    private int findLastNumInMixedArray(int[] nums1, int[] nums2, int startNums1, int startNums2, int k){
        
        int nums1Len = nums1.length;
        int nums2Len = nums2.length;
        
        // special case, out of recursion
        
        // k - 1 is the index of k-size array
        //if nums1 is exauthted, the last num in the mixed array must be in the nums2
        //the index of the last num in the mixed array, is startNums2 + (k -1)
        if(startNums1 > nums1Len - 1){
            return nums2[startNums2 + k - 1]; 
        }
        if(startNums2 > nums2Len - 1){
            return nums1[startNums1 + k - 1];
        }
        
        //if neither nums1 or nums2 is exausthed, but mixed array's size is 1, aka k is 1, then the number must be the minimum one of the nums1[startNums1] and nums2[startNums2]
            if (k == 1) {
            return Math.min(nums1[startNums1], nums2[startNums2]);
        }
        
        
        //如果nums1和nums2都还有内容，并且k也不是1，那么继续分
        // k等分成两部分也就是k/2，那么左半部分的size就是k/2，那么左半部分最后一个数的index就是k/2-1
        // 分别去在nums1和nums2中去找这个位置的数字
        int leftLastIndexInNums1 = startNums1 + k/2 - 1;
        int leftLastIndexInNums2 = startNums2 + k/2 - 1;
        
        //之所以让默认的值为最大，是为了保证，leftLastIndexInNums1超过了nums1的边界时，我们让他满足：这个数字不在nums1数组中这个条件。从而去让startNums2正确的移动。leftLastIndexInNums2同理
        int leftLastValueInNums1 = Integer.MAX_VALUE;
        int leftLastValueInNums2 = Integer.MAX_VALUE;
        
        // 如果leftLastIndex没有越过对应的数组的边界，那么取得该值
        if(leftLastIndexInNums1 <= nums1Len - 1){
                leftLastValueInNums1 = nums1[leftLastIndexInNums1];
        }
        if(leftLastIndexInNums2 <= nums2Len - 1){
            leftLastValueInNums2 = nums2[leftLastIndexInNums2];
        }
            
        // 如果leftLastValueInNums1 <= leftLastValueInNums2,说明中位数不可能在：nums1中该index以左。所以增加startNums1。由于当前的index是如果leftLastValueInNums1是左半部分最后的值，所以右半部分第一个index就是leftLastValueInNums1+1
        if (leftLastValueInNums1 <= leftLastValueInNums2){
            //混合数组的kth元素，不可能在nums1左边
            startNums1 =  leftLastIndexInNums1 + 1;
        }else{
            //混合数组的kth元素，不可能在nums2左边
            startNums2 =  leftLastIndexInNums2 + 1;
        }
        
        // 由于我们总共size是k，去除了k/2个错误答案，剩下的是k-k/2。注意，在整数除法的时候，k-k/2不一定等于k/2
        return findLastNumInMixedArray(nums1, nums2, startNums1, startNums2, k - k/2);
    }
    


 
    // 来自leetcode：https://leetcode.com/problems/median-of-two-sorted-arrays/
    /**
     j = ((m + n + 1) / 2) - i 的来源
    because:
    i + j = (m - i) + (m -j) if m + n is even, OR (m - i) + (n - j) + 1 if m + n is odd.(这里注意，该解在m+n为odd时采用这个等式
    同时说明了该解法采用的在m+n为odd时，多出来的数字在左边即i+j一边，所以中位数在左边)。
    => i + j = (m + n) / 2 if m + n is even, OR (m + n + 1) / 2 if m + n is odd.
    => i + j = (m + n + 1) / 2 for any m + n, since this is integer dividing (e.g. 4 / 2 = 2 and also (4 + 1) / 2 = 2).
    => j = (m + n + 1) / 2 - i for any m + n.
     */
 /*   public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 此题目要求的解：1、两个数组合并后的中位数；2、Big-O log(m+n)
        int m = nums1.length;
        int n = nums2.length;
        if (m > n) { // to ensure m<=n
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && nums2[j - 1] > nums1[i]) {
                iMin = i + 1; // i is too small
            } else if (i > iMin && nums1[i - 1] > nums2[j]) {
                iMax = i - 1; // i is too big
            } else { // i is perfect
                int maxLeft = 0;
                if (i == 0) {
                    maxLeft = nums2[j - 1];
                } else if (j == 0) {
                    maxLeft = nums1[i - 1];
                } else {
                    maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                }
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }

                int minRight = 0;
                if (i == m) {
                    minRight = nums2[j];
                } else if (j == n) {
                    minRight = nums1[i];
                } else {
                    minRight = Math.min(nums2[j], nums1[i]);
                }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }*/
}