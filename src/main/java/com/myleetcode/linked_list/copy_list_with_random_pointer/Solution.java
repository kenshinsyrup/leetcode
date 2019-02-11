package com.myleetcode.linked_list.copy_list_with_random_pointer;

/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        // 审题。这里题目描述的说是一个linkedlist但是有一个random的指针，最初没有搞明白这个random和next是否是共存的，这时候应该看Definition，第5行写的很明确，有两个指针，next和random。
        
        // special case
        if(head == null){
            return head;
        }
        
        Map<RandomListNode, RandomListNode> visited = new HashMap<>();
        
        
        // recursive
        // return copyByRecursion(head, visited);
        
        // Iterative
        return copyByIterative(head, visited);
        
    }
    
    // Iterative 方式。其实本质还是遍历linkedlist复制node，只不过多了一步要额外要考虑random指针。那么我们用一个map来保存node和newNode的对应关系来避免重复创建的问题，然后就是正常的遍历复制就好了。
    private RandomListNode copyByIterative(RandomListNode node, Map<RandomListNode, RandomListNode> visited){
        
        // keep the old linkedlist's first node
        RandomListNode head = node;
        
        RandomListNode newNode = new RandomListNode(node.label);
        visited.put(node, newNode);
        
        while(node != null){
            
            // construct newNode's next and random 
            newNode.next = copyNode(node.next, visited);
            newNode.random = copyNode(node.random, visited);
            
            // move on
            node = node.next;
            newNode = newNode.next;
        }
        
        // return the new linkedlist's head
        return visited.get(head);
    }
    
    // copy the input node and return it, if did not exist in visited, put it.
    // 这个辅助方法很重要，可以避免重复创建已经创建过的newNode。
    private RandomListNode copyNode(RandomListNode node, Map<RandomListNode, RandomListNode> visited){
        if(node == null){
            return node;
        }
        
        if(visited.containsKey(node)){
            return visited.get(node);
        }
        
        RandomListNode newNode = new RandomListNode(node.label);
        
        visited.put(node, newNode);
        
        return newNode;
    }
    
    // Recursive 方式。为了解决环带来的问题，需要一个map来保存已经访问过的node和newNode。从某一个node开始，我们要做的就是创建一个新的newNode,复制node的值给newNode，再复制node的next和random指向的nextNode和randomNode给newNode,注意这里的nextNode和randomNode也需要被复制后赋值给newNode，也就是这里是两个递归调用。所以递归的出口就是返回newNode, newNode只存在两个情况：1 已经被保存在了map 2 新建的。
    
    private RandomListNode copyByRecursion(RandomListNode node, Map<RandomListNode, RandomListNode> visited){
        
        // exit。重要！！！
        if(node == null){
            return null;
        }
        
        // 当前node存在于visited，返回其对应的newNode
        if(visited.containsKey(node)){
            return visited.get(node);
        }
        
        // 新建newNode
        RandomListNode newNode = new RandomListNode(node.label);
        
        // 记录。记录操作必须在递归调用之前，这是尝试，否则无法达到去环的效果啊。递归开始了才会因为环导致死循环，所以要先记录，再开始递归。
        visited.put(node, newNode);
        
        newNode.next = copyByRecursion(node.next, visited);
        newNode.random = copyByRecursion(node.random, visited);
        
        // 记录
      //  visited.put(node, newNode); wrong
        
        return newNode;
        
    }
}