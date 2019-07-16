package com.myleetcode.tree.lowest_common_ancestor_of_a_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.*;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/discuss/65236/JavaPython-iterative-solution
        // special case
        if(root == null || p == null || q == null){
            return null;
        }

        // recursive
        // return lowestCommonAncestorByRecursion(root, p, q);

        // iterative
        return lowestCommonAncestorByIterative(root, p, q);

    }

    // TC: 遍历了整个树，没有额外费时的操作 O(n)
    // SC: 每一个单独的递归操作没有额外的空间操作，SC就是递归深度 O(N)
    /*
DFS recursion函数
查找P和Q，一旦发现则返回，否则递归左右子树。我们知道如果找到了P或者Q，则从整棵树最上层root到P和Q的路径就在递归栈中，当我们处于P或者Q节点然后从此开始return，意思就是根据这条路径返回到到整棵树最上层root
在此过程中，我们如何确定我们发现了P或者Q呢？我们可以在遇到P或者Q的时候返回的时候，将该节点返回，然后递归左右子树时获取left和right。 那么，如果curNode的left和right均非空，说明在返回到该节点时第一次出现(也是唯一一次出现)左右子树各发现了P和Q的情况，则该节点为LCA, 向上return该LCA节点；如果left和right只有一个非空，说明要么1 LCA在非空的那个子树中, 要么2 非空的那个子树发现了P或者Q，我们在这里return非空者, 一来告诉上层我们这颗子树发现了信息，二来与left和right均非null的情况区分，我们并不是LCA，我们的子树中可能有,如果子树中有LCA，那么我们return的没错(如果return自己就错了)，如果子树是发现了P或者Q，我们return的也没错(这种情况return自己也没错，但结合另一个情况，这里不应该return自己)；如果均为null，说明该curNode的左右子树不存在P和Q的LCA也没有发现P和Q，return null
    */
    private TreeNode lowestCommonAncestorByRecursion(TreeNode node, TreeNode p, TreeNode q){
        // base case
        if(node == null){
            return null;
        }

        // check if p or q is found
        if(node.val == p.val || node.val == q.val){
            return node;
        }

        // if p or q not found, recurse to subtree
        TreeNode left = lowestCommonAncestorByRecursion(node.left, p, q);
        TreeNode right = lowestCommonAncestorByRecursion(node.right, p, q);

        // 检查LCA
        // case 1, LCA
        if(left != null && right != null){
            return node;
        }

        // case 2, 子树有LCA或者P或者Q，return对应子树
        // 如果并不是左右子树都非null，说明我们只找到p或者q或者都没有找到，分别返回即可
        if(left != null){
            return left;
        }
        if(right != null){
            return right;
        }

        // case 3, null
        return null;

    }

    // 还可以用非递归的方式来写
    // 这里的重点是如何追踪每个node的parent或者说ancestor
    // 我们用stack辅助dfs，过程中用一个Map来把“所有的”node作为key，其parent作为value存储起来。然后，这个map就类似于路径数组parent[]，我们利用这个map来找出p的路径上的所有的节点放入pPathSet，然后利用这个map找出q的路径上的所有节点但在此过程中我们同时检查节点在pPathSet的存在性，第一个存在的就是LCA
    // by the way, pPathSet之所以用set是为了能O(1)时间查找存在性。一般正常情况下我们想要获得按照顺序的路径node时，会使用List将map中找到的p的路径节点存储，就是path了
    // TC: O(N)
    // SC: O(N)
    private TreeNode lowestCommonAncestorByIterative(TreeNode root, TreeNode p, TreeNode q){
        // we need stack to help us do DFS Iteratively
        Stack<TreeNode> nodeS = new Stack<TreeNode>();
        Map<TreeNode, TreeNode> ancestors = new HashMap<TreeNode, TreeNode>();

        // init
        nodeS.push(root);
        ancestors.put(root, null);// root has no parent.

        while(!nodeS.isEmpty()){
            // current node check if it's p or q
            TreeNode curNode = nodeS.pop();

            // treenode的特点是父知子而子不知父，更新ancestors这个map
            if(curNode.left != null){
                nodeS.push(curNode.left);
                ancestors.put(curNode.left, curNode);
            }
            if(curNode.right != null){
                nodeS.push(curNode.right);
                ancestors.put(curNode.right, curNode);
            }

            // check, if we have found both p and q
            if(ancestors.containsKey(p) && ancestors.containsKey(q)){
                break;
            }
        }

        // 顺序排列 p 的ancestor们
        Set<TreeNode> pAncestors = new HashSet<TreeNode>();
        while(p != null){
            // add p's parent to pAncestors. 这里注意，注释掉的是最初的写法，但是考虑到其实p和q其实应该也是自己的parent，所以改成非注释的写法
            // pAncestors.add(ancestors.get(p));
            pAncestors.add(p);
            // update 'p' until we reach root's parent, ie, null
            p = ancestors.get(p);
        }

        // 同样，遍历q的ancestor们，第一个 也存在于pAncestors中的， 即为LCA
        while(q != null){
            if(pAncestors.contains(q)){
                return q;
            }
            q = ancestors.get(q);
        }

        return null;

    }
}
