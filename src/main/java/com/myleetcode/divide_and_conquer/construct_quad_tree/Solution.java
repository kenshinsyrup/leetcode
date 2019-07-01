package com.myleetcode.divide_and_conquer.construct_quad_tree;

import com.myleetcode.utils.construct_quad_tree.Node;

/*
// Definition for a QuadTree node.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;

    public Node() {}

    public Node(boolean _val,boolean _isLeaf,Node _topLeft,Node _topRight,Node _bottomLeft,Node _bottomRight) {
        val = _val;
        isLeaf = _isLeaf;
        topLeft = _topLeft;
        topRight = _topRight;
        bottomLeft = _bottomLeft;
        bottomRight = _bottomRight;
    }
};
*/
class Solution {
    public Node construct(int[][] grid) {
        return constructByDivideAndConquer(grid);
    }

    private Node constructByDivideAndConquer(int[][] grid){
        if(grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0){
            return new Node(true, true, null, null, null, null);
        }

        return buildQuadNode(grid, 0, 0, grid.length -1, grid[0].length - 1);
    }

    // 出错点: 第一次做的时候思路无误，出错点在于在求取subgrid的时候，rightUp的区域的右下角，应该为(midByRow, rightBottomColIdx), 但是错误的写成了(midByRow, leftUpColIdx)

    // intuition: kind of D&D, recursion to build quad node
    // for a input sub area of the grid, its a sub-grid:
    // 1 if only one elem, then (true, val), val is true for 1 and false for 0
    // 2 if not only one elem, then must could be divided to 4 sub-grids, get the quad node of them, leftUp, rightUp, leftBottom, rightBottom. if all four sub-grids have same val and val is not *, then current quad node is (true, val) and it represents the combined big quad for the four sub-grid quad node; if not, add the four quad node to current quad node and set the current node as (false, *), return current node
    // TC: O(log4(N)) => O(logN), N is the total num of cells. 每次函数递归4次，每次函数计算部分为O(1)
    // SC: O(logN)
    private Node buildQuadNode(int[][] grid, int leftUpRowIdx, int leftUpColIdx, int rightBottomRowIdx, int rightBottomColIdx){
        // base case, only one cell
        if(leftUpRowIdx == rightBottomRowIdx && leftUpColIdx == rightBottomColIdx){
            boolean val = false;
            if(grid[leftUpRowIdx][leftUpColIdx] == 1){
                val = true;
            }
            return new Node(val, true, null, null, null, null);
        }

        // 1 divide to four sub-grid, get their quad node
        // lfetUp subgrid is [(leftUpRowIdx, leftUpColIdx), (midByRow, midByCol)]
        // rightUp subgrid is [(leftUpRowIdx, midByCol + 1), (midByRow, rightBottomColIdx)]
        // leftBottom is [(midByRow + 1, leftUpColIdx), (rightBottomRowIdx, midByCol)]
        // rightBottom is [(midByRow + 1, midByCol + 1), (rightBottomRowIdx, rightBottomColIdx)]
        int midByRow = leftUpRowIdx + (rightBottomRowIdx - leftUpRowIdx) / 2;
        int midByCol = leftUpColIdx + (rightBottomColIdx - leftUpColIdx) / 2;

        Node leftUpNode = buildQuadNode(grid, leftUpRowIdx, leftUpColIdx, midByRow, midByCol);
        Node rightUpNode = buildQuadNode(grid, leftUpRowIdx, midByCol + 1, midByRow, rightBottomColIdx);
        Node leftBottomNode = buildQuadNode(grid, midByRow + 1, leftUpColIdx, rightBottomRowIdx, midByCol);
        Node rightBOttomNode = buildQuadNode(grid, midByRow + 1, midByCol + 1, rightBottomRowIdx, rightBottomColIdx);

        // 2 build current node
        // 2.1 if four subgrid quad nodes are all leaf and has same val, then combine to a big leaf
        if(leftUpNode.isLeaf && rightUpNode.isLeaf && leftBottomNode.isLeaf && rightBOttomNode.isLeaf){
            // if the leaf has same val and not *(actually, leaf node wont has * as val)
            if(leftUpNode.val == rightUpNode.val && rightUpNode.val == leftBottomNode.val && leftBottomNode.val == rightBOttomNode.val){
                return new Node(leftUpNode.val, true, null, null, null, null);
            }
        }

        // 2.2 otherwise
        return new Node(true, false, leftUpNode, rightUpNode, leftBottomNode, rightBOttomNode);

    }
}
