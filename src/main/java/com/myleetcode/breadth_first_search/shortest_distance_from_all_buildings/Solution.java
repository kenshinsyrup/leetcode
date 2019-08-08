package com.myleetcode.breadth_first_search.shortest_distance_from_all_buildings;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int shortestDistance(int[][] grid) {
        // return shortestDistanceByBFSAll(grid); // TLE
        return shortestDistanceByBFSAllWithBuilding(grid);
    }

    /*
    About do with 0 or 1:
    Q: Does anyone want to ask Why don't we start from '0'? This is also what I am thinking. At the first glance, the time complexity of starting from buildings O(B*M*N) (B: # of buildings) and starting from empty places O(E*M*N) (E: # of empty places) might be the same. If in an interview, I think we can ask for clarification. If the empty places are far more than buildings, ex. we have 1 million empty places and only 1 building, starting from building is better. So it depends on how many empty places and buildings that we have. We are not going to say this way or that way is better, but it's a kind of trade-off.
    A: If this is a valid grid, then start with 1 is better than 0, because 1 cannot walk through
    */

    // smae thought, but do BFS on 1s, ie buildings, then whenever reach a 0 we update the dist from this 1 to this 0, after process all 1s, we check every 0s to get the shortest dist to all 1s
    // https://leetcode.com/problems/shortest-distance-from-all-buildings/discuss/76891/Java-solution-with-explanation-and-time-complexity-analysis/184960
    // TC: O((R*C)^2)
    private int shortestDistanceByBFSAllWithBuilding(int[][] grid){
        if(grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0){
            return -1;
        }

        int rowLen = grid.length;
        int colLen = grid[0].length;

        int buildingNum = 0;
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(grid[i][j] == 1){
                    buildingNum++;
                }
            }
        }
        if(buildingNum == 0){
            return 0;
        }

        boolean[][] visited = new boolean[rowLen][colLen]; // we need remember the visited nodes because this is a undirected graph
        int[][] reachedBuilding = new int[rowLen][colLen]; // remember if a building coordination has been reached form cur pos(1 reach this pos, means this pos reach 1)
        int[][] distToBuilding = new int[rowLen][colLen];
        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int dist = Integer.MAX_VALUE;

        // build the data
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(grid[i][j] != 1){
                    continue;
                }

                visited = new boolean[rowLen][colLen];
                bfsWithBuilding(grid, i, j, direcs, visited, reachedBuilding, distToBuilding);
            }
        }

        // get the shortest dist
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(grid[i][j] == 0 && reachedBuilding[i][j] == buildingNum){
                    dist = Math.min(dist, distToBuilding[i][j]);
                }
            }
        }

        if(dist == Integer.MAX_VALUE){
            return -1;
        }
        return dist;
    }

    private void bfsWithBuilding(int[][] grid, int rowIdx, int colIdx, int[][] direcs, boolean[][] visited, int[][] reachedBuilding, int[][] distToBuilding){
        Deque<Point> pointQueue = new ArrayDeque<>();
        pointQueue.offer(new Point(rowIdx, colIdx));

        int level = 0;
        while(!pointQueue.isEmpty()){
            int size = pointQueue.size();
            level++;

            for(int i = 0; i < size; i++){
                Point curP = pointQueue.poll();

                for(int[] direc: direcs){
                    int nextX = curP.x + direc[0];
                    int nextY = curP.y + direc[1];
                    // out of boundary
                    if(nextX < 0 || nextX >= grid.length || nextY < 0 || nextY >= grid[0].length){
                        continue;
                    }
                    // cannot walk through
                    if(visited[nextX][nextY] || grid[nextX][nextY] != 0){
                        continue;
                    }

                    // for all empty lands:
                    distToBuilding[nextX][nextY] += level;
                    reachedBuilding[nextX][nextY]++;
                    visited[nextX][nextY] = true;
                    pointQueue.offer(new Point(nextX, nextY));
                }
            }
        }
    }


    // TLE
    // intuition: do BFSAll at all nodes 0. every BFS, when reach a 1, count current distance, the final distance is shortest distance to all buildings with BFS from this node. choose the smallest one in BFSAll
    // TC: O((R*C)^2)
    // SC: O(R * C)
    private int shortestDistanceByBFSAll(int[][] grid){
        if(grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0){
            return -1;
        }

        int rowLen = grid.length;
        int colLen = grid[0].length;

        int buildingNum = 0;
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(grid[i][j] == 1){
                    buildingNum++;
                }
            }
        }
        if(buildingNum == 0){
            return 0;
        }

        boolean[][] visited = new boolean[rowLen][colLen]; // we need remember the visited nodes because this is a undirected graph
        boolean[][] beenCount = new boolean[rowLen][colLen]; // remember if a building coordination has been counted
        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int dist = Integer.MAX_VALUE;
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(grid[i][j] != 0){
                    continue;
                }

                visited = new boolean[rowLen][colLen];
                beenCount = new boolean[rowLen][colLen];
                dist = Math.min(dist, bfs(grid, i, j, direcs, visited, beenCount, buildingNum));
            }
        }

        if(dist == Integer.MAX_VALUE){
            return -1;
        }
        return dist;
    }

    // classic BFS
    // TC: O(R * C)
    private int bfs(int[][] grid, int rowIdx, int colIdx, int[][] direcs, boolean[][] visited, boolean[][] beenCount, int buildingNum){
        Deque<Point> pointQueue = new ArrayDeque<>();
        pointQueue.offer(new Point(rowIdx, colIdx));
        int dist = 0;
        int level = 0;
        while(!pointQueue.isEmpty()){
            int size = pointQueue.size();
            level++;

            for(int i = 0; i < size; i++){
                Point curP = pointQueue.poll();

                visited[curP.x][curP.y] = true;

                // if obstacle
                if(grid[curP.x][curP.y] == 2){
                    continue;
                }

                // if building, update dist
                // 出错点: write as (if(grid[curP.x][curP.y] == 1 && !beenCount[curP.x][curP.y])) will not continue correctly when we meet a 1 but has been count
                if(grid[curP.x][curP.y] == 1){
                    if(!beenCount[curP.x][curP.y]){
                        dist += (level - 1); // level contains curP itself

                        buildingNum--;
                        beenCount[curP.x][curP.y] = true;
                    }

                    continue;
                }

                // if lands
                for(int[] direc: direcs){
                    int nextX = curP.x + direc[0];
                    int nextY = curP.y + direc[1];
                    if(nextX < 0 || nextX >= grid.length || nextY < 0 || nextY >= grid[0].length ||  visited[nextX][nextY]){
                        continue;
                    }

                    pointQueue.offer(new Point(nextX, nextY));
                }
            }
        }

        // if not reach any building
        if(dist == 0 || buildingNum != 0){
            return Integer.MAX_VALUE;
        }
        return dist;

    }

    private class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
