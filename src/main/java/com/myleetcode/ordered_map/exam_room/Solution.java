package com.myleetcode.ordered_map.exam_room;

import java.util.TreeSet;

public class Solution {
    class ExamRoom {

    /*
    TreeSet
    https://leetcode.com/problems/exam-room/discuss/139885/Java-Solution-based-on-treeset

    */

        TreeSet<Integer> positionTS;
        int total;

        public ExamRoom(int N) {
            this.positionTS = new TreeSet<>();
            this.total = N;
        }

        public int seat() {
            // Base case:
            // 1. 0 student now.
            if (this.positionTS.size() == 0) {
                this.positionTS.add(0);
                return 0;
            }
            // 2. 1 student now.
            if (this.positionTS.size() == 1) {
                int position = this.positionTS.first();

                if (position < this.total / 2) {
                    this.positionTS.add(this.total - 1);
                    return this.total - 1;
                } else {
                    this.positionTS.add(0);
                    return 0;
                }
            }

            // Normal case, more than 1 students.
            // Find the distance between first student position with 0.
            int position = -1;
            int dist = -1;
            int firstPos = this.positionTS.first();
            if (dist < firstPos - 0) {
                dist = firstPos - 0;
                position = 0;
            }
            // Find the distance between two students.
            int prePos = firstPos;
            for (int pos : this.positionTS) {
                // Available position.
                int distCandidate = (pos - prePos) / 2;
                if (distCandidate > dist) {
                    dist = distCandidate;
                    position = prePos + dist;
                }

                prePos = pos;
            }
            // Find the distance between last student with N-1.
            if (dist < (this.total - 1 - prePos)) {
                dist = this.total - 1 - prePos;
                position = this.total - 1;
            }

            // Sit the student here.
            this.positionTS.add(position);

            return position;

        }

        public void leave(int p) {
            this.positionTS.remove(p);
        }
    }
}

/**
 * Your ExamRoom object will be instantiated and called as such:
 * ExamRoom obj = new ExamRoom(N);
 * int param_1 = obj.seat();
 * obj.leave(p);
 */
