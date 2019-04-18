package com.myleetcode.bit_manipulate.utf_8_validation;

class Solution {
    public boolean validUtf8(int[] data) {
        return validUtf8ByBitManipulation(data);
    }

    // Hard code "masks" array to find the index of the first appearance of 0 in the lower 8 bits of each integer.
    private int[] masks = new int[]{128, 64, 32, 16, 8};

    // TC: O(N)
    // SC: O(1)
    // https://leetcode.com/problems/utf-8-validation/discuss/87485/O(n)-JAVA-solution-with-detailed-explaination
    private boolean validUtf8ByBitManipulation(int[] data){
        for(int i = 0; i < data.length; i++){
            int curNum = data[i];
            int type = getType(curNum);

            // 0xxxxxxx 类型的，单独出现没问题，继续
            if(type == 0){
                continue;
            }

            // 非0xxxxxxx类型的，一定是type>=1的，我们先处理type>1的，因为type==1是特殊情况，他只能跟在type>1的之后出现,形如110xxxxx 10xxxxxx
            if(type > 1){
                // 需要的10开头的数，超过了data的总长度，说明不可能
                if(type + i > data.length){
                    return false;
                }

                // 我们找到了开头，那么把后面应该跟的type==1的都检查一遍，形如110xxxxx 10xxxxxx，那么type是2，那么其后面跟着type-1也就是1个形如10xxxxxx的数字，检查，如果非法则false
                type--;
                while(type > 0){
                    i++;
                    if(getType(data[i]) != 1){
                        return false;
                    }

                    type--;
                }
            }else{ // if curNum 是10开头，或者type不是0-4，那么false
                return false;
            }
        }

        return true;

    }

    private int getType(int num){
        for(int i = 0; i < masks.length; i++){
            // 高位与mask[i]不同说明小于，我们是从小到大尝试mask的，所以找到的是第一个比num大的mask
            if((masks[i] & num) == 0){
                return i;
            }
        }
        return -1;
    }
}

