package com.myleetcode.linked_list.copy_list_with_random_pointer;

/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

import com.myleetcode.utils.node_with_next_and_random.Node;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public Node copyRandomList(Node head) {
        // return copyByRecursion(head); // recursive

        return copyByIterative(head); // Iterative
    }

    /*
    Iterative 方式。其实本质还是遍历linkedlist复制node，只不过多了一步要额外要考虑random指针。那么我们用一个map来保存node和newNode的对应关系来避免重复创建的问题，然后就是正常的遍历复制就好了。

    TC: O(N)
    SC: O(N)
    */
    private Node copyByIterative(Node head) {
        // special case
        if (head == null) {
            return head;
        }

        // Keep record the <original node, new node> pair.
        Map<Node, Node> nodeCopyMap = new HashMap<>();

        // Init the new head node.
        Node newNode = new Node(head.val);
        nodeCopyMap.put(head, newNode);

        // For each node, complete its next and random node.
        Node curNode = head;
        while (curNode != null) {
            // construct newNode's next and random
            newNode.next = copyNode(curNode.next, nodeCopyMap);
            newNode.random = copyNode(curNode.random, nodeCopyMap);

            // move on
            curNode = curNode.next;
            newNode = newNode.next;
        }

        // return the new linkedlist's head
        return nodeCopyMap.get(head);
    }

    // copy the input node and return it, if did not exist in nodeCopyMap, put it.
    private Node copyNode(Node node, Map<Node, Node> nodeCopyMap) {
        if (node == null) {
            return node;
        }

        if (nodeCopyMap.containsKey(node)) {
            return nodeCopyMap.get(node);
        }

        Node newNode = new Node(node.val);

        nodeCopyMap.put(node, newNode);

        return newNode;
    }

    /*
    Recursive 方式。为了解决环带来的问题，需要一个map来保存已经访问过的node和newNode。从某一个node开始，我们要做的就是创建一个新的newNode,复制node的值给newNode，再复制node的next和random指向的nextNode和randomNode给newNode,注意这里的nextNode和randomNode也需要被复制后赋值给newNode，也就是这里是两个递归调用。所以递归的出口就是返回newNode, newNode只存在两个情况：1 已经被保存在了map 2 新建的。

    TC: O(N)
    SC: O(N)
    */
    private Node copyByRecursion(Node head) {
        // special case
        if (head == null) {
            return head;
        }

        // Keep record the <original node, new node> pair.
        Map<Node, Node> nodeCopyMap = new HashMap<>();

        return recursiveCopy(head, nodeCopyMap);
    }

    private Node recursiveCopy(Node curNode, Map<Node, Node> nodeCopyMap) {
        if (curNode == null) {
            return null;
        }

        if (nodeCopyMap.containsKey(curNode)) {
            return nodeCopyMap.get(curNode);
        }

        Node newNode = new Node(curNode.val);
        // 记录。记录操作必须在递归调用之前，这是尝试，否则无法达到去环的效果啊。递归开始了才会因为环导致死循环，所以要先记录，再开始递归。
        nodeCopyMap.put(curNode, newNode);

        newNode.next = recursiveCopy(curNode.next, nodeCopyMap);
        newNode.random = recursiveCopy(curNode.random, nodeCopyMap);

        return newNode;
    }
}