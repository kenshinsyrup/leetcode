package com.myleetcode.divide_and_conquer.kth_largest_element_in_an_array;

import java.util.Random;

public class SolutionNew {
    public int findKthLargest(int[] nums, int k) {
        // 和K-closest同样的思路
        // 区别只在于，因为要找的是严格的第K个大值，那么我们相当于找到K-1个大值，Kth是pivot，然后剩下的是小于pivot的值，那么pivot就是我们要的。注意这里的K是1based的而pivotIndex是0based，而我们要得到的是pivotIndex这个值，那么就需要K-1，也就是说找第2个大值，那么我们要找的是pivotIndex为1的情况

        // special case
        if (nums == null || nums.length == 0 || k < 1) {
            return 0;
        }

        return findFirstKLargestByQuickSelect(nums, k);

    }

    private int findFirstKLargestByQuickSelect(int[] nums, int k) {
        int len = nums.length;
        int begin = 0;
        int end = len - 1;

        int pivotIndex = 0;
        k -= 1;
        while (begin <= end) {
            pivotIndex = findPivotIndexByPartition(nums, begin, end);
            if (pivotIndex == k) {
                break;
            } else if (pivotIndex > k) {
                end = pivotIndex - 1;
            } else {
                begin = pivotIndex + 1;
            }
        }

        return nums[k];

    }

    private int findPivotIndexByPartition(int[] nums, int begin, int end) {
        // choose pivot and make it at end
        Random random = new Random();
        int pivotIndex = begin + random.nextInt(end - begin + 1);
        swap(nums, pivotIndex, end);
        int pivot = nums[end];

        int left = begin;
        int right = end - 1;
        while (left <= right) {
            // 符合条件者不管
            while (left <= right && nums[left] >= pivot) {
                left++;
            }
            while (left <= right && nums[right] < pivot) {
                right--;
            }

            //不符合条件者，swap到正确的分区, 然后继续处理
            if (left <= right) {
                swap(nums, left, right);
                left++;
                right--;
            }
        }

        // 最后让pivot去自己该去的位置, 也就是left最后的位置，因为这时候left就是pivotIndex的位置，left从自己开始到end-1都是小于pivot的，从0到left不包括left自己的情况下的值都是大于等于pivot的，那么left和end交换值，就可以保证left这个index就是pivotindex了。那么pivotIndex左边都大于等于pivot，右边都小于pivot，pivotIndex自己的值等于pivot，正好
        swap(nums, left, end);

        return left;

    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
