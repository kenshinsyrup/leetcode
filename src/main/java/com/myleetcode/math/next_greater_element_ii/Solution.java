package com.myleetcode.math.next_greater_element_ii;

class Solution {
    public int nextGreaterElement(int n) {
        return nextGreaterElementByTraverse(n);
    }

    // TC: O(N), 这里容易错误认为是O(N^2)因为步骤2在for循环里嵌套了一个O(N)操作的reverse，但是实际上for循环里这个嵌套只执行了一次，在for运行到某一个值时。所以最坏的情况下，也只是相当于并列的两个N cost的traverse，所以是O(N)
    // SC: O(N)
    // https://leetcode.com/problems/next-greater-element-iii/discuss/101824/Simple-Java-solution-(4ms)-with-explanation.
    // intuition: this problem should be in the Catagory of "31. Next Permutation" rather than in the Catagory of "Next Greater Element"
    // and, this is a reading problem, we should know the "given positive 32-bit integer n" is in the form of "12", not in the form of "01010101"; and we should know the "digits" in "has exactly the same digits" means "1" and "2", not "0","1","0","1"...and so on. (actually "0","1" these stuff called bit)
    // then, here's how we transform the problem to 31.
    /*
Here is one simple example.
index: 012345
given: 124651
ans : 125146
procedure:
Starting from the rightmost digit, going to left. Find the first digit which is smaller than the previous digit(here previous are the digits in its right because we are traversing from back).
In this example, 4 is smaller than 6. Remember 4 and its index 2.
Going from rightmost again. This time, find the first digit which is bigger than 4. It is 5 here.
Swap 4 and 5. The number becomes 125641.
Reverse all the digits which are right to 4's original index (That is 2), 641 should be reversed to 146 here.
And the answer is reached which is 125146.
    */
    // 额外注意的点是，为了防止越界错误，我们用long保存要output的值，返回前先检查是否大于Integer.MAX_VALUE, 大于则-1
    private int nextGreaterElementByTraverse(int n){
        if(n <= 0){
            return -1;
        }

        // for convience we use string and char to process n
        String nStr = "" + n;
        char[] nCharArray = nStr.toCharArray();

        // 1, find the firt pair that right digit bigger than left digit
        int len = nCharArray.length;
        int idx = len;
        for(int i = len - 1; i >= 1; i--){
            if(nCharArray[i] > nCharArray[i - 1]){
                idx = i - 1;
                break;
            }
        }
        // if idx is still len, so not find such pair, return -1
        if(idx == len){
            return -1;
        }

        // 2, find the first digit bigger than digit in idx and swap
        for(int i = len - 1; i > idx; i--){
            if(nCharArray[i] > nCharArray[idx]){
                // swap
                char temp = nCharArray[i];
                nCharArray[i] = nCharArray[idx];
                nCharArray[idx] = temp;

                // 3, sort the digits behind idx in ascending order
                // Arrays.sort(nCharArray, idx + 1, len);
                // 3. actually, we know here is the first char that bigger than idx char, so after we swap, this subarray is still descending. so we could just to reverse this subarray instead of sort, this way we could make this operation TC from nlogn to n
                reverse(nCharArray, idx + 1, len - 1);

                break;
            }
        }

        // 3, convert to long, compare to Integer.MAX_VALUE
        long ret = Long.valueOf(new String(nCharArray));
        if(ret > Integer.MAX_VALUE){
            return -1;
        }

        return (int)ret;

    }

    private void reverse(char[] charArray, int start, int end){
        while(start <= end){
            char temp = charArray[start];
            charArray[start] = charArray[end];
            charArray[end] = temp;

            start++;
            end--;
        }
    }



}
