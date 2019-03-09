package com.myleetcode.backtracking.word_search;

class Solution {
    public boolean exist(char[][] board, String word) {
        // special case
        if(word == null){
            return true;
        }
        if(board == null){
            return false;
        }

        // return existByBacktracking(board, word);
        return existByBacktrackingOpt(board, word);
    }

    // 优化
    // 在原始的写法中，directions有五个方向，实际上，只需要四个，对于那个{0, 0}，board[i][j]，而对于第一个char，我们是不需要经过backtrack去找其是否可以构成word的，board[i][j] == word.charAt(0).这个是很重要的特点，board[i][j]是否可以作为合法的开头。
    private boolean existByBacktrackingOpt(char[][] board, String word){
        int[][] direction = new int[][]{{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
        boolean[][] visiting = new boolean[board.length][board[0].length];
        StringBuilder sb = new StringBuilder();
        int wordIdx = 0;

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == word.charAt(0) && backtrackingOpt(board, word, visiting, sb, direction, i, j, wordIdx)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean backtrackingOpt(char[][] board, String word, boolean[][] visiting, StringBuilder sb, int[][] direction, int i, int j, int wordIdx){
        // ans
        if(sb.length() == word.length()){
            return true;
        }

        // check
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length){
            return false;
        }
        if(board[i][j] != word.charAt(wordIdx)){
            return false;
        }
        if(visiting[i][j]){
            return false;
        }

        // 循环所有节点时，注意能查找的下一个节点只能是当前节点的四个方向之一
        for(int k = 0; k < direction.length; k++){
            int nextI = i + direction[k][0];
            int nextJ = j + direction[k][1];

            sb.append(board[i][j]);
            wordIdx++;
            visiting[i][j] = true;

            if(backtrackingOpt(board, word, visiting, sb, direction, nextI, nextJ, wordIdx)){
                return true;
            }

            sb.deleteCharAt(sb.length() - 1);
            wordIdx--;
            visiting[i][j] = false;
        }

        return false;
    }

    // intuition:
    // backtracking
    private boolean existByBacktracking(char[][] board, String word){

        // we have source list char[][] board, we have target String word
        // we need a temp StringBuilder to sotre mid-result
        // we need a idx to tell us we are finding which char in word
        // according to eg 3, dont use the same char more than once

        int[][] direction = new int[][]{{0, 0}, {0, -1}, {0, 1}, {1, 0}, {-1, 0}};
        boolean[][] visiting = new boolean[board.length][board[0].length];
        StringBuilder sb = new StringBuilder();
        int wordIdx = 0;

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(backtracking(board, word, visiting, sb, direction, i, j, wordIdx)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean backtracking(char[][] board, String word, boolean[][] visiting, StringBuilder sb, int[][] direction, int i, int j, int wordIdx){
        if(sb.length() == word.length()){
            return true;
        }

        // 循环所有节点时，注意能查找的下一个节点只能是当前节点的四个方向之一
        for(int k = 0; k < direction.length; k++){
            int nextI = i + direction[k][0];
            int nextJ = j + direction[k][1];

            if(nextI < 0 || nextI > board.length - 1 || nextJ < 0 || nextJ > board[0].length - 1){
                continue;
            }

            // check
            if(!visiting[nextI][nextJ] && board[nextI][nextJ] == word.charAt(wordIdx)){
                sb.append(board[nextI][nextJ]);
                wordIdx++;
                visiting[nextI][nextJ] = true;

                if(backtracking(board, word, visiting, sb, direction, nextI, nextJ, wordIdx)){
                    return true;
                }

                sb.deleteCharAt(sb.length() - 1);
                wordIdx--;
                visiting[nextI][nextJ] = false;
            }
        }

        return false;
    }

}