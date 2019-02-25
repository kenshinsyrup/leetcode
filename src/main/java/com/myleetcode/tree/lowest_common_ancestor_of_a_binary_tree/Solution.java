package com.myleetcode.tree.lowest_common_ancestor_of_a_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.*;

public /**
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

        // 老师说这个是个典型的pre-order遍历问题，想想pre-order在做的事情，就是：1 visit root; 2 recurse left, recurse right. 所以，一旦我们在pre-order的时候找到了p那么return，一旦找到了q也return，这样如果p和q有公共ancestor，一定会在两次recurse之后得到两个非null的返回值，那么当前的root就是LCA

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
    // SC: 每一个单独的递归操作没有额外的空间操作，SC就是递归深度 O(h)
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

        //注意，我们在对左右子树进行了recurse之后，要处理这两个返回值，但这并不是post-order，原因在于我们对于node的合法性检测是基于p和q的，所以我们前面进行的那个检测是对于‘root node’的检测，所以我们这里时pre-order

        // 而接下来的操作，是在得到了左右子树的递归结果后，进行的额外处理，只不过正常情况下，我们不对左右子树的递归结果处理，这里我们要处理
        // then, when we get return left and right both exists, we know current node is the LCA
        if(left != null && right != null){
            return node;
        }

        // 如果并不是左右子树都非null，说明我们只找到p或者q或者都没有找到，分别返回即可
        if(left != null){
            return left;
        }

        if(right != null){
            return right;
        }

        return null;

    }

    // 还可以用非递归的方式来写, 正好复习用iterative方式来实现pre-order. pre-order的iterative就是最常用的写法
    // 这里的重点是如何追踪每个node的parent或者说ancestor, 这里我们看到是BST，所以每个node都是唯一的，那么用一个map来辅助，node作为key，其parent作为其value是可以的。
    private TreeNode lowestCommonAncestorByIterative(TreeNode root, TreeNode p, TreeNode q){
        // we need stack to help us, because pre-order, in-order, post-order are all DFS
        Stack<TreeNode> nodeS = new Stack<TreeNode>();
        Map<TreeNode, TreeNode> ancestors = new HashMap<TreeNode, TreeNode>();

        // init
        nodeS.push(root);
        ancestors.put(root, null);// root has no parent.

        // 基本写法是检查!nodeS.isEmpty()这个条件，但是我们并不是为了完整的遍历树，而是为了找LCA，所以我们检查的条件为是否找到了p和q的parent，也就是说ancestors是否存在了p和q key。
        while(!nodeS.isEmpty() && (!ancestors.containsKey(p) || !ancestors.containsKey(q))){
            // current node check if it's p or q
            TreeNode curNode = nodeS.pop();

            // treenode的特点是父知子而子不知父，所以在更新ancestors这个map时，用的是如下这种方式
            if(curNode.left != null){
                nodeS.push(curNode.left);
                ancestors.put(curNode.left, curNode);
            }
            if(curNode.right != null){
                nodeS.push(curNode.right);
                ancestors.put(curNode.right, curNode);
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
            // if(pAncestors.contains(ancestors.get(q))){
            //     return ancestors.get(q);
            // }
            if(pAncestors.contains(q)){
                return q;
            }
            q = ancestors.get(q);
        }

        return null;

    }
}
