package com.myleetcode.keys_and_rooms;

class Solution {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        
//         special case
        if(rooms == null || rooms.size() == 0){
            return true;
        }
        
       return canVisitAllRoomsByDFS(rooms);
        
    }
    
    private boolean canVisitAllRoomsByDFS(List<List<Integer>> rooms){
//         stack 存储可以被访问的room的号码也就是room index。从中可以拿到其中藏着的钥匙，从而能补充stack
        Stack<Integer> roomNums = new Stack<>();
//         seen来记录我们看过的房间号. 这里也可以直接用一个Set来存储，最后比较Set与输入的rooms的size就可以
        boolean[] seen = new boolean[rooms.size()];
        
        // 从0号房间开始
        roomNums.push(0);
        seen[0] = true;
        
        while(!roomNums.isEmpty()){
            int roomNum = roomNums.pop();
            List<Integer> room = rooms.get(roomNum);
            for(int key : room){
                // room中的钥匙对应房间，如果一个key对应的房间没有seen，那么将该key也就是房间号roomNum入栈，同时更新seen。
                if(!seen[key]){
                    roomNums.push(key);
                    seen[key] = true;
                }
            }
        }
        
        for(boolean v : seen){
            if(v == false){
                return false;
            }
        }
        
        return true;

    }
}