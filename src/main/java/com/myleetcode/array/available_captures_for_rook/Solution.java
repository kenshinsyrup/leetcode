package com.myleetcode.array.available_captures_for_rook;

public class Solution {
    public int numRookCaptures(char[][] board) {
        // https://leetcode.com/problems/available-captures-for-rook/discuss/244142/Java-dfs-easy-1ms
        // 脑筋急转弯类型的问题
        // 首先找到R，然后R可以有四个移动方向，那么有一个for循环来让R四向移动。在移动过程中，只要：x和y没有越界（左右均不能越界），那么 当遇到的是空白square '.'那么应该一直移动，所以这是一个嵌套的while循环, 当我们遇到了字符为'B'那么应该停止移动，也就是当前while循环break结束. 如果字符为'p', ret++, 停止while
        // R只能向一个方向移动到停止，停止之后就是一个case了，不会再在此基础再次移动，所以每次都是从原始位置向四个方向的其中之一移动

        // special case
        if(board == null){
            return 0;
        }

        return numRookCapturesByArray(board);

    }

    private int numRookCapturesByArray(char[][] board){
        // result
        int ret = 0;
        int len = board.length; // board is square, then board.length == board[0].length

        // first to find R
        int iR = 0;
        int jR = 0;
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                if(board[i][j] == 'R'){
                    iR = i;
                    jR = j;
                    break;// only has one R
                }
            }
        }

        // R can move in four directions
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}};// 右，左，上，下

        for(int i = 0; i < directions.length; i++){
            int iRMove = iR;
            int jRMove = jR;
            while((iRMove + directions[i][0]) >= 0 &&
                    (iRMove + directions[i][0]) < len &&
                    (jRMove + directions[i][1]) >= 0 &&
                    (jRMove + directions[i][1]) < len){
                // move R
                iRMove += directions[i][0];
                jRMove += directions[i][1];

                if(board[iRMove][jRMove] == '.'){
                    continue;
                }else if(board[iRMove][jRMove] == 'B'){
                    break;
                }else{
                    // not . not B then is p, so ret++. and break. // 注意，遇到p也是会导致stop，所以不会再继续向同方向移动，刚开始的时候想错了，以为会继续移动
                    ret++;
                    break;
                }
            }
        }

        return ret;
    }
}
