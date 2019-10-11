package com.myleetcode.breadth_first_search.cut_off_trees_for_golf_event;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;

class Solution {

  public int cutOffTree(List<List<Integer>> forest) {
    return cutOffTreeByBFS(forest);
  }

  /*
  https://leetcode.com/problems/cut-off-trees-for-golf-event/discuss/107404/Java-solution-PriorityQueue-%2B-BFS
  intuition:
  1. get all Tree to a list and sort ascending by height, Tree includes x,y and height
  2. every time we choose a Tree from list, it's the target. BFS from lastPos(at first is [0,0]) to find the target Tree pos, if not reachable return-1, otherwise we know the shortest step to reach here, we mark here with 1 then

  M is tree number
  TC: O(M * R*C)
  SC: O(R*C)
  */
  private int cutOffTreeByBFS(List<List<Integer>> forest) {
    if (forest == null || forest.size() == 0 || forest.get(0) == null
        || forest.get(0).size() == 0) {
      return -1;
    }

    // get all trees and sort
    List<Tree> treeList = new ArrayList<>();
    int rowLen = forest.size();
    int colLen = forest.get(0).size();
    for (int i = 0; i < rowLen; i++) {
      for (int j = 0; j < colLen; j++) {
        if (forest.get(i).get(j) > 1) {
          treeList.add(new Tree(i, j, forest.get(i).get(j)));
        }
      }
    }
    Collections.sort(treeList, new Comparator<Tree>() {
      @Override
      public int compare(Tree fir, Tree sec) {
        return fir.height - sec.height;
      }
    });

    // 4 directions
    int[][] direcs = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    int totalSteps = 0;
    Node prevNode = new Node(0, 0);
    for (int idx = 0; idx < treeList.size(); idx++) {
      Tree tree = treeList.get(idx);

      int steps = bfs(forest, direcs, tree, prevNode);
      if (steps == -1) {
        return -1;
      }
      totalSteps += steps;

      prevNode = new Node(tree.rowIdx, tree.colIdx);
    }

    return totalSteps;
  }

  private int bfs(List<List<Integer>> forest, int[][] direcs, Tree tree, Node startNode) {
    int rowLen = forest.size();
    int colLen = forest.get(0).size();
    boolean[][] visited = new boolean[rowLen][colLen];

    int steps = 0;

    Deque<Node> nodeQueue = new ArrayDeque<>();
    nodeQueue.offer(startNode);
    while (!nodeQueue.isEmpty()) {
      int size = nodeQueue.size();

      for (int i = 0; i < size; i++) {
        Node curNode = nodeQueue.poll();
        if (curNode.rowIdx == tree.rowIdx && curNode.colIdx == tree.colIdx) {
          return steps;
        }
        visited[curNode.rowIdx][curNode.colIdx] = true;

        for (int[] direc : direcs) {
          int nextRowIdx = curNode.rowIdx + direc[0];
          int nextColIdx = curNode.colIdx + direc[1];

          // check valid
          if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
            continue;
          }
          if (forest.get(nextRowIdx).get(nextColIdx) == 0) {
            continue;
          }
          if (visited[nextRowIdx][nextColIdx]) {
            continue;
          }

          // put into queue
          nodeQueue.offer(new Node(nextRowIdx, nextColIdx));

          // if no this line, logic is correct, but will TLE
          visited[nextRowIdx][nextColIdx] = true;
        }
      }

      steps++;
    }

    return -1;
  }

  class Node {

    int rowIdx;
    int colIdx;

    public Node(int rowIdx, int colIdx) {
      this.rowIdx = rowIdx;
      this.colIdx = colIdx;
    }
  }

  class Tree {

    int height;
    int rowIdx;
    int colIdx;

    public Tree(int rowIdx, int colIdx, int height) {
      this.rowIdx = rowIdx;
      this.colIdx = colIdx;
      this.height = height;
    }
  }
}