package com.myleetcode.union_find.couples_holding_hands;

class Solution {
    public int minSwapsCouples(int[] row) {
        return minSwapsCouplesByUnionFind(row);
    }

    /*
    https://leetcode.com/problems/couples-holding-hands/discuss/117520/Java-union-find-easy-to-understand-5-ms
    Think about each couple as a vertex in the graph. So if there are N couples, there are N vertices. Now if in position 2i and 2i +1 there are person from couple u and couple v sitting there, that means that the permutations are going to involve u and v. So we add an edge to connect u and v. The min number of swaps = N - number of connected components. This follows directly from the theory of permutations. Any permutation can be decomposed into a composition of cyclic permutations. If the cyclic permutation involve k elements, we need k -1 swaps. You can think about each swap as reducing the size of the cyclic permutation by 1. So in the end, if the graph has k connected components, we need N - k swaps to reduce it back to N disjoint vertices.

    Hardest part is to figure out the formula described above.

Take the example of a permutation
Numbers : 2,0,1,4,5,3,6
Index :   0,1,2,3,4,5,6
This can be broken as:
2(val 2 at idx 0(default start), , act as idx to get next num)->1(val 1 at idx 2, act as idx to get next num)->0(val 0 at idx 1, act as idx to get next num)->2(val 2 at idx 0, cycle complete),
4->5->3->4,
6->6
All of them are cyclical. Start with an index and go to the index specified by the number at the index until you form a cycle.

For the another permutation 0,1,2,3,4,5,6
we have 0->0, 1->1, 2->2, 3->3, 4->4, 5->5, 6->6
    */
    // Template
    class UnionFind {
        private int count;
        private int[] roots;
        private int[] ranks;

        public UnionFind(int n) {
            this.count = n;
            this.roots = new int[n];
            this.ranks = new int[n];

            for (int i = 0; i < n; i++) {
                this.roots[i] = i;
            }
        }

        public int find(int node) {
            if (node != this.roots[node]) {
                this.roots[node] = find(this.roots[node]);
            }

            return this.roots[node];
        }

        public void union(int nodeI, int nodeJ) {
            int nodeIRoot = find(nodeI);
            int nodeJRoot = find(nodeJ);
            if (nodeIRoot == nodeJRoot) {
                return;
            }

            // Need do a union, union by ranks.
            if (this.ranks[nodeIRoot] > this.ranks[nodeJRoot]) {
                this.roots[nodeJRoot] = nodeIRoot;
            } else if (this.ranks[nodeIRoot] < this.ranks[nodeJRoot]) {
                this.roots[nodeIRoot] = nodeJRoot;
            } else {
                this.roots[nodeIRoot] = nodeJRoot;
                this.ranks[nodeJRoot]++;
            }
            this.count--;
        }

        public int getCount() {
            return this.count;
        }
    }

    private int minSwapsCouplesByUnionFind(int[] row) {
        if (row == null || row.length == 0) {
            return 0;
        }

        int len = row.length;
        int couples = len / 2;
        UnionFind uf = new UnionFind(couples);
        for (int i = 0; i < couples; i++) {
            int person1 = row[2 * i]; // person1 is person at row[2*i]
            int person2 = row[2 * i + 1]; // person2 is next person of person1

            uf.union(person1 / 2, person2 / 2); // person belongs to person/2 couple
        }

        return couples - uf.getCount();
    }

}
