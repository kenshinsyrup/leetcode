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
    public int read(char[] buf, int n) {
        // TC: O(N), N is the given n that we want to read
        // SC: O(1), no extra space needed
        // https://leetcode.com/problems/read-n-characters-given-read4/discuss/49496/Another-accepted-Java-solution

        int total = 0;
        boolean eof = false;;
        char[] tempBuf = new char[4]; // temp buffer
        while(!eof && (total < n)){
            int readNum = read4(tempBuf);

            // end of file?
            if(readNum < 4){
                eof = true;
            }

            // how many we need to copy
            int copyNum = Math.min(readNum, n - total);

            // copy buf
            for(int i = 0; i < copyNum; i++){
                buf[total + i] = tempBuf[i];
            }

            // update total use the really copied num
            total += copyNum;
        }

        return total;
    }
}

// just to avoid the warnning from IDEA
class Reader4{
    int read4(char[] buf){
        return 0;
    }
}
