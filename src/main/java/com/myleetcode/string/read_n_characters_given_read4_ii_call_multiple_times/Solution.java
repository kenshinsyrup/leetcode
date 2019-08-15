package com.myleetcode.string.read_n_characters_given_read4_ii_call_multiple_times;

/**
 * The read4 API is defined in the parent class Reader4.
 *     int read4(char[] buf);
 */
public class Solution extends Reader4 {

    // https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/discuss/49598/A-simple-Java-code/49831

    // need some golbal variables to implement
    // buffread4BuffPtrPtr to record the current pos we at in the read4Buff array. if its not 0, means we should not read4 more
    int read4BuffPtr = 0;
    // read4BuffCount is the data num of the read4
    int read4BuffCount = 0;
    // store data we have got through read4
    char[] read4Buff = new char[4];

    /**
     * @param buf Destination buffer
     * @param n   Number of characters to read
     * @return    The number of actual characters read
     */
    public int read(char[] buf, int n) {
        if(buf == null || buf.length == 0 || n <= 0){
            return 0;
        }

        int idx = 0;
        while(idx < n){
            if(read4BuffPtr == 0){
                read4BuffCount = read4(read4Buff); // 0-4
            }

            while(idx < n && read4BuffPtr < read4BuffCount){
                buf[idx] = read4Buff[read4BuffPtr];

                idx++;
                read4BuffPtr++;
            }

            // if cur read4 get less than 4 chars, means EOF
            if(read4BuffCount < 4){
                break;
            }

            // if exhausted read4BuffCount, reset read4BuffPtr to 0
            if(read4BuffPtr == read4BuffCount){
                read4BuffPtr = 0;
            }
        }

        return idx; // idx-0+1 - 1 is char total num in buf
    }
}

// just to avoid the warnning from IDEA
class Reader4{
    int read4(char[] buf){
        return 0;
    }
}

