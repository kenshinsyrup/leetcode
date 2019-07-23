package com.myleetcode.monotonic_deque.online_stock_span;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class StockSpanner {
    // optimization: save the List, we only need another stack to keep the idx of every price, if empty means its PGE idx is -1. BY this way, reduce the SC(but worst case is still O(N), normally is better). See the solution section.

    // intuition: Mono Deque to get PGE
    // 用mono deque维护一个descending order的deque，对于当前price，获取第一个大于他的num所在idx的紧挨着的下一个idx这个就是该price的span的左边界
    // 用lst存储所有的price

    // TC: O(N)
    // SC: O(N)
    private Deque<Integer> pleDeque;
    private int curIdx;
    private List<Integer> numList;
    public StockSpanner() {
        curIdx = 0;
        pleDeque = new ArrayDeque<>();
        numList = new ArrayList<>();
    }

    public int next(int price) {
        int ret = 0;

        while(!pleDeque.isEmpty() && numList.get(pleDeque.peekLast()) <= price){ // get num, here use <= because this problem LC thinks that equals value is not PGE
            pleDeque.pollLast();
        }
        if(!pleDeque.isEmpty()){
            ret = curIdx - (pleDeque.peekLast() + 1) + 1; // 第一个大于curIdx的idx后面一个idx是这个span的左边界
        }else{
            ret = curIdx - (-1 + 1) + 1;
        }

        pleDeque.offer(curIdx);
        numList.add(price);

        curIdx++;

        return ret;
    }
}

/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */
