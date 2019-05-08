package com.myleetcode.design.design_snake_game;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    class SnakeGame {

        // intuition:
        // use a class Point to store the cell's coordinate that is occupied by the snake
        // put Points in a DoubleEndQueue so we could conviently access the head and tail of the snake
        // be aware of the below two solutions, they are not very good, just for thought. and the one with HashSet to store Point is wrong because he didnot implement the hashCode() function so it's HashSet could not check if we occupied a cell.
        // in this solution, we use the naive way, for check eat body, we just traverse the whole snake queue to find if the new heas have exact same coordinate with one of the snake occupied cells.
        // and, leetcode think, new head is legal to be in old tail's position
        // https://leetcode.com/problems/design-snake-game/discuss/82668/Java-Deque-and-HashSet-design-with-detailed-comments
        // https://leetcode.com/problems/design-snake-game/discuss/82668/Java-Deque-and-HashSet-design-with-detailed-comments/185647

        class Point {
            int x;
            int y;

            Point(int x, int y){
                this.x = x;
                this.y = y;
            }
        }

        Deque<Point> snakeQueue;
        int[][] food;
        int foodIdx;
        int width;
        int height;
        int score;

        /** Initialize your data structure here.
         @param width - screen width
         @param height - screen height
         @param food - A list of food positions
         E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
        public SnakeGame(int width, int height, int[][] food) {
            this.width = width;
            this.height = height;
            this.food = food;
            this.foodIdx = 0;
            this.score = 0;

            this.snakeQueue = new ArrayDeque<>();

            // init, head is (0, 0), put into snakeQueue
            Point head = new Point(0, 0);
            this.snakeQueue.offerFirst(head);
        }

        /** Moves the snake.
         @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
         @return The game's score after the move. Return -1 if game over.
         Game over when snake crosses the screen boundary or bites its body. */
        public int move(String direction) {
            // case 0: already end
            if(this.score == -1){
                return this.score;
            }

            // get a dummy new head of snake, then try to move to the next pos, check if it's valid
            Point head = this.snakeQueue.peek();
            Point newHead = new Point(head.x, head.y);

            if(direction.equals("U")){
                newHead.x--;
            }else if(direction.equals("D")){
                newHead.x++;
            }else if(direction.equals("L")){
                newHead.y--;
            }else if(direction.equals("R")){
                newHead.y++;
            }

            //case 1: out of boundary or eating body

            // !!! new head is legal to be in old tail's position, remove from set temporarily
            Point tail = this.snakeQueue.pollLast();

            // out of board
            if(newHead.x < 0 || newHead.x >= this.height || newHead.y < 0 || newHead.y >= width){
                return -1;
            }
            // if newHead is the same pos with one cell that is occupied by the snake, means eat body, fail
            for(Point p: this.snakeQueue){
                if(p.x == newHead.x && p.y == newHead.y){
                    return -1;
                }
            }

            // add the new head since this is a valid move
            this.snakeQueue.offerFirst(newHead);

            // case 2: if the new head is the pos of food, then eat it
            if(this.foodIdx < this.food.length && this.food[this.foodIdx][0] == newHead.x && this.food[this.foodIdx][1] == newHead.y){
                this.foodIdx++; // next food's pos
                this.score++; // score plus

                // !!! put back the tail
                this.snakeQueue.offerLast(tail);

                return this.score;
            }

            // !!! the snkae do a normal move, all knots are forward, so no need putback the tail

            return this.score;

        }
    }

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
}
