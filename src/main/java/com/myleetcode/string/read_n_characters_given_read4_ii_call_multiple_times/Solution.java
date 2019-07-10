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
        if(n <= 0){
            return 0;
        }

        int i = 0;
        while(i < n){
            if(read4BuffPtr == 0){
                read4BuffCount = read4(read4Buff); // 0-4
            }

            while(i < n && read4BuffPtr < read4BuffCount){
                buf[i] = read4Buff[read4BuffPtr];

                i++;
                read4BuffPtr++;
            }
            if(read4BuffPtr == read4BuffCount){
                read4BuffPtr = 0;
            }

            if(read4BuffCount < 4){
                break;
            }
        }

        return i;
    }
}

// just to avoid the warnning from IDEA
class Reader4{
    int read4(char[] buf){
        return 0;
    }
}

