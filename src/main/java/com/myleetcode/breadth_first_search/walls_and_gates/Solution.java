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
  // optimize:
  // in the original sol, we have visited rooms every time for each gate. To optmize this, we could process like this, we do BFS on all gates and update all closest rooms connected with those gates, then we do BFS on those rooms and update all closest rooms connected with those rooms... This is like every time we step one outter of the core gates
  // To approach this:
  // 1 we could push all gates to queue at start, this is the very fisrt level, the core gates
  // 2 do BFS with node in queue, if we meet a room, this room must be nearest with current level, then we offer this room into queue(it is in the next level, so its distance is current level distance + 1), then next, by this way, we could find all nearest rooms of all gates
  private void wallsAndGatesByBFSII(int[][] rooms) {
    if (rooms == null || rooms.length == 0 || rooms[0] == null || rooms[0].length == 0) {
      return;
    }

    int rowLen = rooms.length;
    int colLen = rooms[0].length;

    // four directions
    int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    Deque<Point> nodeQueue = new ArrayDeque<>();

    for (int i = 0; i < rowLen; i++) {
      for (int j = 0; j < colLen; j++) {
        // first enqueue all gates, this means we put all gates in the very first level
        if (rooms[i][j] == 0) {
          nodeQueue.offer(new Point(i, j));
        }
      }
    }

    // then BFS with the queue, by this way, we update all rooms level by level(although we dont use the queue.size and a for loop, it still not changes the property, actually), since we are update rooms by level, so we have no need to update a encountered room again
    while (!nodeQueue.isEmpty()) {
      Point curNode = nodeQueue.poll();

      // process children
      for (int[] dire : directions) {
        int childNodeX = curNode.x + dire[0];
        int childNodeY = curNode.y + dire[1];

        // not out of range, process
        if (childNodeX >= 0 && childNodeX < rowLen && childNodeY >= 0 && childNodeY < colLen) {
          // if INF, means an empty room, so we update it's distance to gate to get the shortest distance
          if (rooms[childNodeX][childNodeY] == Integer.MAX_VALUE) {
            // update distance
            rooms[childNodeX][childNodeY] = rooms[curNode.x][curNode.y] + 1;
            // enqueue
            nodeQueue.offer(new Point(childNodeX, childNodeY));
          }
        }
      }
    }
  }

  // intuition: we need to fill each room with shortest distance to a gate, so we could use BFS to do this. Since we dont know there are more rooms than gates or otherwise, we could assume gates are less and we could use gates as start nodes to do BFS to update the distance of rooms to this gate. We do BFS on every gate and if we meet a room then update its shortest distance

  // TC: O(R * C * M), where M is the num of gates, because for each gate, we visited all room that's reachable and update its shortest distance
  // SC: O(1)
  private void wallsAndGatesByBFS(int[][] rooms) {
    if (rooms == null || rooms.length == 0 || rooms[0] == null || rooms[0].length == 0) {
      return;
    }

    int rowLen = rooms.length;
    int colLen = rooms[0].length;

    // four directions
    int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    //visited
    boolean[][] visited = new boolean[rowLen][colLen];

    for (int i = 0; i < rowLen; i++) {
      for (int j = 0; j < colLen; j++) {
        // do BFS on every gate
        if (rooms[i][j] == 0) {
          visited = new boolean[rowLen][colLen];
          bfs(rooms, i, j, directions, visited);
        }
      }
    }
  }

  // do bfs with given cell
  private void bfs(int[][] rooms, int rowIdx, int colIdx, int[][] directions, boolean[][] visited) {
    Point rootNode = new Point(rowIdx, colIdx);

    Deque<Point> nodeQueue = new ArrayDeque<>();
    nodeQueue.offer(rootNode);

    int distance = 0;// distance to cur gate
    while (!nodeQueue.isEmpty()) {
      int size = nodeQueue.size();
      distance++;

      // scan by level
      for (int i = 0; i < size; i++) {
        Point curNode = nodeQueue.poll();

        // process children
        for (int[] dire : directions) {
          int childNodeX = curNode.x + dire[0];
          int childNodeY = curNode.y + dire[1];

          // not out of range, process
          if (childNodeX >= 0 && childNodeX < rooms.length && childNodeY >= 0
              && childNodeY < rooms[0].length && !visited[childNodeX][childNodeY]) {
            // visited
            visited[childNodeX][childNodeY] = true;

            // if -1 means wall; 0 menas a gate; other values not INF means have visited rooms. We dont care about this two cases so we just ignore them.
            // if not 0 or -1, means an empty room(INF) or a room that could be reahed by another gate, so we update it's distance to gate to get the shortest distance
            if (rooms[childNodeX][childNodeY] != 0 && rooms[childNodeX][childNodeY] != -1) {
              // update distance, keep the min
              rooms[childNodeX][childNodeY] = Math.min(rooms[childNodeX][childNodeY], distance);
              // enqueue
              nodeQueue.offer(new Point(childNodeX, childNodeY));
            }
          }
        }
      }
    }
  }


  class Point {

    int x;
    int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}