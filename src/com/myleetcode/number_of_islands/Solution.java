package com.myleetcode.number_of_islands;

class Solution {
    public int numIslands(char[][] grid) {
        // 重点是理解题意，关于island的概念：island就是所有水平或者垂直有连接的1组成的一个联系。周围全是0.
        // 所以，example1中，所有的1都是相连的，所以island就是1
        // example2中，左上角4个1是相连的，1个island；中间的单独的一个1周围全是0，又是一个island；右下角两个1，又组成一个island。所以是3个island
        
        // special case
        if(grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }
        
//         关于坐标，把grid想象成二维数组即可，二维数组自身的长度就是grid的深度，就是y轴；二维数组中每一个数组的长度就是grid的宽度，就是x轴。所以一般cs中的坐标系都是和屏幕坐标系一样的。
        // vertical coordinate。
        int yLen = grid.length;
        // horizontal coordinate
        int xLen = grid[0].length;
        
        int count = 0;
        
        for(int i = 0; i < yLen; i++){
            for(int j = 0; j < xLen; j++){
                if(grid[i][j] == '1'){
                    // 如果是1，那么开始找所有相连的1，所有相连的1他们组成的就是岛.找到的这些1都被标记为visited，这里可以直接标记为0水
                    traverseByRecursion(grid, i, j);
                    
                    // 上面一步traverse完了所有与当前区块链接且为1的区域后，就标记好了一个岛，count islands
                    count++;
                }
            }
        }
        
        return count;
    }
    
    private void traverseByRecursion(char[][] grid, int i, int j){
        if(i >= 0 && j >= 0 && i < grid.length && j < grid[0].length && grid[i][j] == '1'){
            // 当前的区块是1，那么既然访问到了，mark as visited，这里可以很方便的标记为0
            grid[i][j] = '0';
            
            // traverseByRecursion connected zones, 题目要求只考虑左右上下四个
            traverseByRecursion(grid, i - 1, j);
            traverseByRecursion(grid, i + 1, j);
            traverseByRecursion(grid, i, j - 1);
            traverseByRecursion(grid, i, j + 1);
        }
    }
}