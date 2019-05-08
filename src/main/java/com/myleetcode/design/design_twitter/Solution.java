package com.myleetcode.design.design_twitter;

import java.util.*;

public class Solution {
    class Twitter {

        // thought:
        // https://leetcode.com/problems/design-twitter/discuss/82935/Java-OOD-solution-with-detailed-explanation
        // https://leetcode.com/problems/design-twitter/discuss/82825/Java-OO-Design-with-most-efficient-function-getNewsFeed

        class Tweet{
            int tweetId;
            int timestamp;

            public Tweet(int tweetId, int timestamp){
                this.tweetId = tweetId;
                this.timestamp = timestamp;
            }
        }

        int timestamp;
        int feedMaxNum;
        Map<Integer, Set<Integer>> userFolloweeDB;
        Map<Integer, List<Tweet>> userTweetDB;

        /** Initialize your data structure here. */
        public Twitter() {
            this.timestamp = 0; // fake timestamp starts from 9, and increasing by one everytime someone post tweet
            this.feedMaxNum = 10; // this problem said the capacity of news feed is 10
            this.userFolloweeDB = new HashMap<>(); // represents the user and his followee DB
            this.userTweetDB = new HashMap<>(); // represents the user and his tweets DB
        }

        /** Compose a new tweet. */
        public void postTweet(int userId, int tweetId) {
            // get user's tweetList, if no such user posted tweet yet, add
            List<Tweet> tweetList = userTweetDB.getOrDefault(userId, new ArrayList<>());
            tweetList.add(new Tweet(tweetId, this.timestamp));
            // update DB
            userTweetDB.put(userId, tweetList);

            this.timestamp++;
        }

        /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
        public List<Integer> getNewsFeed(int userId) {
            // get news feed has two parts: self, followee
            // use PQ to maintain a sorted window, window length is 10
            // timestamp is increasing, so the recent is big, so PQ use ascending sort
            PriorityQueue<Tweet> feedPQ = new PriorityQueue<>((tw1, tw2) -> {
                return tw1.timestamp - tw2.timestamp;
            });

            // followee feed:
            Set<Integer> followeeSet = userFolloweeDB.get(userId);
            if(followeeSet != null){
                for(int followeeId: followeeSet){
                    buildFeedPQ(userTweetDB.get(followeeId), feedPQ);
                }
            }

            // self feed
            buildFeedPQ(userTweetDB.get(userId), feedPQ);

            List<Integer> ret = new ArrayList<>();
            while(!feedPQ.isEmpty()){
                ret.add(feedPQ.poll().tweetId);
            }

            // !!! here leetcode mean output tweets by timestamp descending, means we should reverse the ret
            Collections.reverse(ret);

            return ret;

        }

        private void buildFeedPQ(List<Tweet> tweetList, PriorityQueue<Tweet> feedPQ){
            if(tweetList == null){
                return;
            }
            for(Tweet tw: tweetList){
                if(feedPQ.size() < this.feedMaxNum){
                    // if PQ is not full, add
                    feedPQ.offer(tw);
                }else{
                    if(tw.timestamp > feedPQ.peek().timestamp){
                        // if PQ is full, then if tw is more recent than the first one in PQ, ie the oldest in PQ, then poll the oldest one out and add the tw in
                        feedPQ.poll();
                        feedPQ.offer(tw);
                    }
                }
            }
        }

        /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
        public void follow(int followerId, int followeeId) {
            // dont follow your self, otherwise you'll get duplicate tweets when get newFeed
            if(followerId == followeeId){
                return;
            }

            Set<Integer> followeeSet = userFolloweeDB.getOrDefault(followerId, new HashSet<>());
            followeeSet.add(followeeId);

            userFolloweeDB.put(followerId, followeeSet);
        }

        /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
        public void unfollow(int followerId, int followeeId) {
            Set<Integer> followeeSet = userFolloweeDB.getOrDefault(followerId, new HashSet<>());
            if(followeeSet.contains(followeeId)){
                followeeSet.remove(followeeId);

                userFolloweeDB.put(followerId, followeeSet);
            }
        }
    }

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
}
