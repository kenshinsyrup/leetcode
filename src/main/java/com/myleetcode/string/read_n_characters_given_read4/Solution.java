package com.myleetcode.string.read_n_characters_given_read4;

/**
 * The read4 API is defined in the parent class Reader4.
 *     int read4(char[] buf);
 */
public class Solution extends Reader4 {
    /**
     * @param buf Destination buffer
     * @param n   Number of characters to read
     * @return    The number of actual characters read
     */

    // relevent problem: 158. Read N Characters Given Read4 II - Call multiple times
    // https://leetcode.com/problems/read-n-characters-given-read4/discuss/49496/Another-accepted-Java-solution
    // TC: O(N), N is the given n that we want to read
    // SC: O(1), no extra space needed
    public int read(char[] buf, int n){
        if(buf == null || buf.length == 0 || n <= 0){
            return 0;
        }

        char[] read4Buf = new char[4];
        int idx = 0;
        while(idx < n){
            int read4Num = read4(read4Buf);

            int read4Ptr = 0;
            while(idx < n && read4Ptr < read4Num){
                buf[idx] = read4Buf[read4Ptr];

                idx++;
                read4Ptr++;
            }

            // this time, read4 get less than 4 chars, means EOF
            if(read4Num < 4){
                break;
            }
        }

        // n is exhausted or file is exhausted, idx-0+1-1 is the total num
        return idx;
    }
}

// just to avoid the warnning from IDEA
class Reader4{
    int read4(char[] buf){
        return 0;
    }
}
