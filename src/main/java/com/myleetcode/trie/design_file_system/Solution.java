package com.myleetcode.trie.design_file_system;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    class FileSystem {

        // Trie tree node
        class Node {
            Map<String, Node> pathNodeMap;
            int val;
            boolean isEnd;

            Node() {
                this.pathNodeMap = new HashMap<>();
                this.val = 0;
                this.isEnd = false;
            }
        }

        Node root;

        public FileSystem() {
            root = new Node();
        }

        public boolean createPath(String path, int value) {
            if (path == null || path.length() == 0 || path.equals("/")) {
                return false;
            }

            String[] components = path.split("/");
            Node curNode = root;
            for (int i = 0; i < components.length; i++) {
                // After split by /, the first element will be "", skip it.
                if (i == 0) {
                    continue;
                }

                // If curNode is not root, and it does not represent a path end, this whole path is not valid, return false.
                if (curNode != root && !curNode.isEnd) {
                    return false;
                }

                String comp = components[i];
                if (!curNode.pathNodeMap.containsKey(comp)) {
                    curNode.pathNodeMap.put(comp, new Node());
                }
                curNode = curNode.pathNodeMap.get(comp);
            }
            // If curNode represent a path end, means the given path already exists, false.
            if (curNode.isEnd) {
                return false;
            }

            curNode.isEnd = true;
            curNode.val = value;

            return true;
        }

        public int get(String path) {
            if (path == null || path.length() == 0 || path.equals("/")) {
                return -1;
            }

            Node curNode = root;
            String[] components = path.split("/");
            for (int i = 0; i < components.length; i++) {
                // After split by /, the first element will be "", skip it.
                if (i == 0) {
                    continue;
                }

                String comp = components[i];
                if (!curNode.pathNodeMap.containsKey(comp)) {
                    return -1;
                }
                curNode = curNode.pathNodeMap.get(comp);
            }
            // If curNode does not represent a path end, means given path not exist.
            if (!curNode.isEnd) {
                return -1;
            }

            return curNode.val;
        }


    /*
    Map
    https://leetcode.com/problems/design-file-system/discuss/365901/JavaPython-3-7-line-simple-HashMap-code-w-analysis.

    */
    /*
    Map<String, Integer> file = new HashMap<>();

    public FileSystem() {
        file.put("", -1);
    }

    // N is given path length.
    // TC: O(N)
    public boolean createPath(String path, int value) {
        if(path == null || path.length() == 0 || path.equals("/")){
            return false;
        }

        // Check given path.
        if(file.containsKey(path)){
            return false;
        }

        // Check given path's parent path.
        int idx = path.lastIndexOf("/");
        String parent = path.substring(0, idx);
        if (!file.containsKey(parent)) {
            return false;
        }

        // Put.
        file.put(path, value);

        return true;
    }

    public int get(String path) {
        if(path == null || path.length() == 0 || path.equals("/")){
            return -1;
        }

        return file.getOrDefault(path, -1);
    }
    */
    }

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * boolean param_1 = obj.createPath(path,value);
 * int param_2 = obj.get(path);
 */
}
