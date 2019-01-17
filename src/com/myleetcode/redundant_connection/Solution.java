package com.myleetcode.redundant_connection;

class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        // 边画图，边找是否已经画好的部分是否有环，在某个属于edges的edge的u和v深度搜索的时候，从u开始，如果最后又找到了u，那么就是有环，该edge就是重复的
        
        // special case
        if(edges == null || edges.length == 0 || edges[0] == null || edges[0].length == 0){
            return null;
        }
        
        // store visited
        Set<Integer> seen = new HashSet();
        
        // graph, 通过edge提供的u和v，画图。先初始化一个空的graph出来，即一个足够大的二维数组
        List<List<Integer>> graph = new ArrayList<>();
        for(int i = 0; i <= 1000; i++){
            graph.add(i, new ArrayList<>());
        }
        
//         遍历给定的edges，每一个edge都有一个起点u和一个终点v
//         有两种情况：
        // 情况1 u和v在graph中作为起点都不存在（因为是无向图，一个edge带来的两个u和v实际二者都可以作为起点，我们前面是为了好描述说u是起点v是终点），都不存在，则在graph中添加该边，比如u为1，v1为2时，graph中添加了进去；当u为1，v2为3时，graph添加了进去
//         情况2 u和v在graph中作为起点均已经存在了，也就是graph中已经存在了一条u-x边和v-y边比如1-2和1-3边，那么这时候又来了一个edge是u-v比如2-3我们就需要检查一下，u-x,v-y,v-u是否组成了环，如果组成了，那么当前这个新来的edge就是重复边
        for(int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            
            // 如果图中分别以u和v为起点的子图，则开始递归找环也就是找重复边
            if(graph.get(u) != null && graph.get(u).size() != 0 && graph.get(v) != null && graph.get(v).size() != 0){
            // 开始搜索之前，清空seen
                seen.clear();
                
                if(hasCircleByRecursion(graph, u, v, seen)){
                    return edge;
                }
            }
            
            // 如果不满足上个if，则说明：1、graph中没有以u为起点的子图（边）；2graph中没有以v为起点的子图；3、graph中既没有u为起点，也没用v为起点的子图
            // 那么添加该边，这个是无向边
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        
        return null;
    }

    
    private boolean hasCircleByRecursion(List<List<Integer>> graph, int u, int v, Set<Integer> seen){
        if(!seen.contains(u)){
            
            //visited
            seen.add(u);
            
            // 递归出口
            if(u == v){
                return true;
            }
            
            // dfs 递归, 在最初，graph是空的，然后我们逐渐在使用edges提供的egde提供的u和v补完这ggraph，每次都是把新出现的u和v链接起来。那么如果在以后又发现我们可以从u找到v，那么这个graph就有环了，那么当前提供u和v的这个edge就有问题。从u能找到v，就是说，graph中存储的所有从u能达到的targtes们，其中一个是v。
            // 比如一个合法的edges可以是{[1,2],[1,3]}，那么我们得到的图也有两条合法无向边2 - 1 - 3
            // 这时候，如果edges里面还有一个比如[2,3]，那么开始时u为2,v为3，我们就会找到一个u为2的一个v叫做1，又将1作为u可以找到一个v叫做3，又以该3作为u时发现u和v相同了。那么说明新增的这个2-3的边是重复的.
            for(int ue: graph.get(u)){
                if(hasCircleByRecursion(graph, ue, v, seen)){
                    return true;
                }
            }
        }
        
        return false;
    }
    
}