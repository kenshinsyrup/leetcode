package com.myleetcode.array.reveal_cards_in_increasing_order;

class Solution {
    public int[] deckRevealedIncreasing(int[] deck) {
        
        // https://leetcode.com/problems/reveal-cards-in-increasing-order/discuss/200526/Java-Queue-Simulation-Step-by-Step-Explanation
        
        // 这种题目主要是思路的问题，把题目转换成数学上的问题
//         从题目的要求来反推：题目是每次取一个最上面的，然后将下一个最上面的放到最后，然后以此循环，继续取最上面的，再把下一个最上面的放到最后。。。
//         解法就是：我们先排序deck，这个顺序就是我们最后取完所有的牌后，应该的顺序。然后，既然最终的顺序有了，我们就把他们放到res中应该在的位置去，让按照上面的顺序从res中取牌时，能最终得到排序好的deck这样的顺序。这里使用queue来辅助，我们先准备好一个和deck长度相同的queue，这个queue就是最终的顺序，然后按照上面说的取牌方式：如果要取牌，那么就是把deck[i]赋值给当前queue的第一个元素在res中的位置；如果是向牌堆最后放牌，就是把当前queue最上面的这个index放到queue最后面去。也就是用queu来完整的模拟整个取牌过程，并适当的将最终的顺序中取得的牌放给对应的位置到res中。
     
        // special case{
        if(deck == null){
            return deck;
        }
        
        
        int len = deck.length;
        
        // 结果数组，题目中描述的取牌放牌方式就是操作这里
        int[] res = new int[len];
        
        // 排序deck，这个就是我们最终按照取牌方式，从res中取牌，得到的最终牌序
        Arrays.sort(deck);
        
        // 把0到len-1这些数字放入queue中，这些数字指代的是index，实际指代的是res中的位置。我们要用题目中描述的方式来对这个queue来取和放来模拟真实的取牌情况，从而将index排好顺序。并把牌放到这些index对应的位置在res中。
        Queue<Integer> indexQ = new LinkedList<>();
        for(int i = 0; i < len; i++){
            indexQ.add(i);
        }
        
        int revealIndex = 0;
        int toBottomIndex = 0;
        for(int i = 0; i < len; i++){
            // reveal当前最上面的牌,那么在组成res时，就是要给这个位置的res放牌
            revealIndex = indexQ.poll();
            res[revealIndex] = deck[i];

            // 重要！！由于最后一张牌时，只reveal，不会再有需要放到牌堆底的，所以需要检查queue的非空。
            if(!indexQ.isEmpty()){
                // 把当前最上面的牌放到牌堆底部，那么在组成res时，就是把目前这个index放到queue尾部，直到要reveal的时候才给牌
                toBottomIndex = indexQ.poll();
                indexQ.add(toBottomIndex);
            }
        }
            
        return res;
    }
    
}