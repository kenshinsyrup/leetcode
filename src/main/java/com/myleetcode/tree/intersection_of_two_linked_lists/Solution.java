package com.myleetcode.tree.intersection_of_two_linked_lists;

import com.myleetcode.utils.list_node.ListNode;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // return getIntersection(headA, headB);
        return getIntersectionByLength(headA, headB);
    }

    // 这个题的重点是看明白题意
    // 主要是看eg1，我们发现intersection的定义和node.val没有关系，这个intersection是客观存在的，只是需要我们去找，也就是去找到第一个LinkedList A和LinkedList B交叉的点，这个交叉的点的定义就是在linkedList A上的node A与在LinkedList B上的node b是相等，即nodeA==nodeB(引用相等，而不是val)

    // intuiton: 如果不考虑运行时间和空间，首先想到可以O(m*n)的双重遍历找相同；然后可以想到node存入set的方式找相同时间(Om + n),空间O(m + n)
    // 但是要求时间O(m + n),空间O(1)

    // 一个比较直白的方式，我们用PA遍历LA，用PB遍历LB，找PA==PB的node，如果有就是交叉点，如果没有就是null，但是我们需要让PA和PB同时到达交叉点，所以我们先获得LA和LB的长度，让PA和PB先移动到距离交叉点距离一样的位置再开始移动。怎么确定距离交叉点距离一样呢，如果存在交叉点，那么终点肯定也是一致的，那么如果LA和LB长度相同，PA和PB自然距离交叉点一样；如果长度不同，比如LA长，那么就应该移动PA一段距离，移动的距离为LA比LB长的距离
    // https://leetcode.com/problems/intersection-of-two-linked-lists/discuss/49792/Concise-JAVA-solution-O(1)-memory-O(n)-time
    private ListNode getIntersectionByLength(ListNode headA, ListNode headB){
        // special case
        if(headA == null || headB == null){
            return null;
        }

        int lenA = getLength(headA);
        int lenB = getLength(headB);

        ListNode pA = headA;
        ListNode pB = headB;

        int distance = 0;
        // LA长
        if(lenA > lenB){
            distance = lenA - lenB;
            while(distance != 0){
                pA = pA.next;
                distance--;
            }
        }else if(lenA < lenB){
            // 或者LB长
            distance = lenB - lenA;
            while(distance != 0){
                pB = pB.next;
                distance--;
            }
        }

        // pA和pB距离终点一样了，也就是距离可能存在的交叉点一样了，同步移动，找交叉
        while(pA != pB){
            pA = pA.next;
            pB = pB.next;
        }

        return pA;

    }

    private int getLength(ListNode head){
        ListNode dummy = head;
        int len = 0;
        while(dummy!=null){
            len++;
            dummy = dummy.next;
        }
        return len;
    }

    // https://leetcode.com/problems/intersection-of-two-linked-lists/discuss/49785/Java-solution-without-knowing-the-difference-in-len
    // 找第一个交叉的node，方法就是：两个指针一个PA遍历LinkedList A，一个PB遍历LinkedList B，并且每个指针每次只向后移动1步，然后，我们让PA遍历完LA之后去LB的头，继续遍历LB知道LB尾，同理PB遍历完LB后去到LA的头，继续遍历LA，直到LA尾. 在此过程中，一旦发生PA==PB即二者指向同一个node，这个就是交叉点。如果到最后没有，那就是没有交叉点
    // proof：假设LA和LB存在交叉点，两个指针一个PA遍历LinkedList A，一个PB遍历LinkedList B，并且每个指针每次只向后移动1步，那么如果两个指针在交叉后的list上，二者永远不可能相遇，这个是基础。然后，我们让PA遍历完LA之后去LB的头，继续遍历LB知道LB尾，同理PB遍历完LB后去到LA的头，继续遍历LA，直到LA尾。从数学上，PA走了LA.length + LB.length, PB走了LB.length + LA.length，那么二者既然走的总路程一样，肯定到达的终点一样,这样以来，我们知道，PA和PB在LA/LB和LB/LA的时候二者永远不可能相遇，PA和PB在交叉点之后的共同list上时永远不可能相遇，但是既然最后到了同一个终点即为相遇状态，那么唯一可能相遇的点是那个交叉点，在这之后二者一直是相遇状态到达的终点。
    // 另一个情况就是LA和LB不存在交叉点，那么PA和PB最后各自到达的终点不同(null之前)，自然也不存在交叉点让他们在任何时刻能指向同一个node

    // 注意。LA和LB无非有是否交叉两个情况，这两个情况又细分为LA.length>LB.length, LA.length==LB.length,LA.length<LB.length三个情况，也就是总共6个情况。我们可以考虑：
    // 比如存在交叉时：根据上面的算法，如果1 LA.length>LB.length,那么PB第一次指向null时，PA肯定不指向null，然后PB开始去遍历LA，PA继续直到自己开始遍历LB，然后PA与PB在交叉点相遇，return；2 如果LA.length==LB.length,那么肯定PA在遍历LA时，PB在遍历LB时，还没遍历完，就已经相遇了，return；3 情况同1
    // 比如不存在交叉时：根据上面的算法，如果1 LA.length > LB.length,那么PB第一次指向null时，PA肯定不指向null，然后PB开始遍历LA，然后PA继续直到自己开始遍历LB，然后最后PA==PB==null，不交叉； 2 LA.length == LB.length，那么当PA遍历完LA指向null时，PB也遍历完LB指向null，此时PA==PB==null，不交叉
    private ListNode getIntersection(ListNode headA, ListNode headB){
        // https://leetcode.com/problems/intersection-of-two-linked-lists/discuss/49785/Java-solution-without-knowing-the-difference-in-len
        ListNode pA = headA;
        ListNode pB = headB;

        while(pA != pB){
            if(pA != null){
                pA = pA.next;
            }else{
                pA = headB;
            }

            if(pB != null){
                pB = pB.next;
            }else{
                pB = headA;
            }
        }

        return pA; // 如果有交叉点，pA==pB==交叉点；如果没有交叉点pA==pB==null

    }


}
