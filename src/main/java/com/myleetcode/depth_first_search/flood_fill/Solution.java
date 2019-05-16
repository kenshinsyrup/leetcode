package com.myleetcode.depth_first_search.flood_fill;

class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        return floodFillByDFS(image, sr, sc, newColor);
    }

    // TC: O(V), V is the pixel number, every vertice we just meet once
    // SC: O(V), recursion
    // intuition: from (sr, sc) do DFS to adjacent same color cells, use a boolean[][] visited to remember what cell we have visited, if reach a cell that has no more nonvisited same colors, mark it with new Color and return
    private int[][] floodFillByDFS(int[][] image, int sr, int sc, int newColor){
        if(image == null || image.length == 0 || image[0] == null || image[0].length == 0){
            return new int[0][0];
        }

        // pre check, if image[sr][sc] == newColor, means no need to change the image
        int color = image[sr][sc];
        if(color == newColor){
            return image;
        }

        boolean[][] visited = new boolean[image.length][image[0].length];
        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        dfs(image, direcs, visited, sr, sc, color, newColor);

        return image;
    }

    private void dfs(int[][] image, int[][] direcs, boolean[][] visited, int rowIdx, int colIdx, int color, int newColor){
        // mark as visited
        visited[rowIdx][colIdx] = true;

        // explore children, find the leafs
        for(int[] direc: direcs){
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];

            // check
            if(isValid(image, nextRowIdx, nextColIdx, visited, color)){
                dfs(image, direcs, visited, nextRowIdx, nextColIdx, color, newColor);
            }
        }

        // after processed children, change self's color
        if(image[rowIdx][colIdx] == color){
            image[rowIdx][colIdx] = newColor;
        }
    }

    private boolean isValid(int[][] image, int rowIdx, int colIdx, boolean[][] visited, int color){
        if(rowIdx < 0 || rowIdx >= image.length || colIdx < 0 || colIdx >= image[0].length ){
            return false;
        }

        if(visited[rowIdx][colIdx]){
            return false;
        }

        if(image[rowIdx][colIdx] != color){
            return false;
        }

        return true;
    }
}
