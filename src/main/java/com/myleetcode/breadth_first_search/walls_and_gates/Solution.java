package com.myleetcode.breadth_first_search.walls_and_gates;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public void wallsAndGates(int[][] rooms) {
        // wallsAndGatesByBFS(rooms);
        wallsAndGatesByBFSII(rooms);
    }

    // TC: O(R*C) or(V+E), V are the # of gates, E are all edges
    // SC: O(R*C)
    // optimize: we could push all gates to queue at start, then do BFS with node in queue, if we meet a room, this room must be nearest with curNode, then we offer this room into queue, then next, by this way, we could find all nearest rooms of all gates
    private void wallsAndGatesByBFSII(int[][] rooms){
        if(rooms == null || rooms.length == 0 || rooms[0] == null || rooms[0].length == 0){
            return;
        }

        int rowLen = rooms.length;
        int colLen = rooms[0].length;

        // four directions
        int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        Deque<Point> nodeQueue = new ArrayDeque<>();

        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                // first enqueue all gates, this means we put all gates in the very first level
                if(rooms[i][j] == 0){
                    nodeQueue.offer(new Point(i, j));
                }
            }
        }

        // then BFS with the queue, by this way, we update all rooms level by level(although we dont use the queue.size and a for loop, it still not changes the property, actually), since we are update rooms by level, so we have no need to update a encountered room again
        while(!nodeQueue.isEmpty()){
            Point curNode = nodeQueue.poll();

            // process children
            for(int[] dire: directions){
                int childNodeX = curNode.x + dire[0];
                int childNodeY = curNode.y + dire[1];

                // not out of range, process
                if(childNodeX >= 0 && childNodeX < rowLen && childNodeY >= 0 && childNodeY < colLen){
                    // if INF, means an empty room, so we update it's distance to gate to get the shortest distance
                    if(rooms[childNodeX][childNodeY] == Integer.MAX_VALUE){
                        // update distance
                        rooms[childNodeX][childNodeY] = rooms[curNode.x][curNode.y] + 1;
                        // enqueue
                        nodeQueue.offer(new Point(childNodeX, childNodeY));
                    }
                }
            }
        }
    }

    // intuition: we need to fill each room with shortest distance to a gate, so we could use BFS to do this. Since we dont know there are more rooms than gates or otherwise, we could assume gates are less and we could use gates as start nodes to do BFS to update the distance of rooms to this gate. We do BFS on every gate and keep the min distance for every room

    // slow, because with every gate, we visits all rooms
    // TC: O((R*C)^M), M is the num of gates.
    // SC: O(1)
    private void wallsAndGatesByBFS(int[][] rooms){
        if(rooms == null || rooms.length == 0 || rooms[0] == null || rooms[0].length == 0){
            return;
        }

        int rowLen = rooms.length;
        int colLen = rooms[0].length;

        // four directions
        int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //visited
        boolean[][] visited = new boolean[rowLen][colLen];

        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                // do BFS on every gate
                if(rooms[i][j] == 0){
                    visited = new boolean[rowLen][colLen];
                    bfs(rooms, i, j, directions, visited);
                }
            }
        }
    }

    // do bfs with given cell
    private void bfs(int[][] rooms, int rowIdx, int colIdx, int[][] directions, boolean[][] visited){
        Point rootNode = new Point(rowIdx, colIdx);

        Deque<Point> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(rootNode);

        int distance = 1;// distance to cur gate, start with 1
        while(!nodeQueue.isEmpty()){
            int size = nodeQueue.size();

            // scan by level
            for(int i = 0; i < size; i++){
                Point curNode = nodeQueue.poll();

                // process children
                for(int[] dire: directions){
                    int childNodeX = curNode.x + dire[0];
                    int childNodeY = curNode.y + dire[1];

                    // not out of range, process
                    if(childNodeX >= 0 && childNodeX < rooms.length && childNodeY >= 0 && childNodeY < rooms[0].length && !visited[childNodeX][childNodeY]){
                        // visited
                        visited[childNodeX][childNodeY] = true;

                        // if -1 means wall; 0 menas a gate. We dont care about this two cases so we just ignore them.

                        // if INF, means an empty room, so we update it's distance to gate to get the shortest distance
                        if(rooms[childNodeX][childNodeY] == Integer.MAX_VALUE){
                            // case 3, INF ie empty room
                            // update distance, keep the min
                            rooms[childNodeX][childNodeY] = Math.min(rooms[childNodeX][childNodeY], distance);
                            // enqueue
                            nodeQueue.offer(new Point(childNodeX, childNodeY));
                        }
                    }
                }
            }

            // after a level processed, increase the distance by 1
            distance++;
        }
    }


    class Point{
        int x;
        int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
