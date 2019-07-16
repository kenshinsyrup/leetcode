package com.myleetcode.stack.asteroid_collision;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        return asteroidCollisionByStack(asteroids);
    }

    // intuition: Stack
    // TC: O(N), every asteroid at most in stack once and out stack once
    // SC: O(N)
    private int[] asteroidCollisionByStack(int[] asteroids){
        // special case
        if(asteroids == null || asteroids.length < 2){
            return  asteroids;
        }

        Deque<Integer> asteroidStack = new ArrayDeque<>();
        for(int i = 0; i < asteroids.length; i++){
            int asteroid = asteroids[i];

            // case 1, asteroid not negative
            if(asteroid >= 0){
                asteroidStack.push(asteroid);
                continue;
            }

            // case 2, empty stack
            if(asteroidStack.isEmpty()){
                asteroidStack.push(asteroid);
                continue;
            }

            // case 3, otherwise:
            while(!asteroidStack.isEmpty()){
                if(asteroidStack.peek() > 0){
                    // top is positive and larger than -asteroid
                    if(asteroidStack.peek() > -asteroid){
                        break;
                    }

                    // equal
                    if(asteroidStack.peek() == -asteroid){
                        asteroidStack.pop();
                        break;
                    }

                    // smaller
                    asteroidStack.pop();
                    // !!! after pop here, we must check if the stack is empty, if empty, the asteroid now should be push to stack because it's the winner, then break
                    if(asteroidStack.isEmpty()){
                        asteroidStack.push(asteroid);
                        break;
                    }
                }else{
                    asteroidStack.push(asteroid);
                    break;
                }
            }
        }

        int num = asteroidStack.size();
        int[] ret = new int[num];
        int i = num - 1;
        while(!asteroidStack.isEmpty()){
            ret[i] = asteroidStack.pop();
            i--;
        }

        return ret;
    }
}
