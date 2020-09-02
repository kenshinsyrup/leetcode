package com.myleetcode.trie.design_in_memory_file_system;

import java.util.*;

public class Solution {
    class FileSystem {

        /*
        File System is kind of Trie Tree kind problem.
        https://leetcode.com/problems/design-in-memory-file-system/discuss/103331/Java-Solution-File-class
        */
        class Node {
            boolean isFile = false;
            Map<String, Node> children = new HashMap<>();
            String content = "";
        }

        Node root = null;

        public FileSystem() {
            this.root = new Node();
        }

        public List<String> ls(String path) {
            String[] dirs = path.split("/");

            List<String> ret = new ArrayList<>();
            String fileName = "";
            Node curNode = this.root;
            for (String dir : dirs) {
                if (dir.length() == 0) {
                    continue;
                }

                if (!curNode.children.containsKey(dir)) {
                    return new ArrayList<>();
                }

                curNode = curNode.children.get(dir);
                fileName = dir;
            }

            // If it is a file path, return a list that only contains this file's name. If it is a directory path, return the list of file and directory names in this directory.
            if (curNode.isFile) {
                ret.add(fileName);
            } else {
                for (String dirName : curNode.children.keySet()) {
                    ret.add(dirName);
                }
            }

            // Lexicographic order.
            Collections.sort(ret);

            return ret;
        }

        public void mkdir(String path) {
            String[] dirs = path.split("/");

            Node curNode = this.root;
            for (String dir : dirs) {
                if (dir.length() == 0) {
                    continue;
                }

                if (!curNode.children.containsKey(dir)) {
                    curNode.children.put(dir, new Node());
                }

                curNode = curNode.children.get(dir);
            }
        }

        public void addContentToFile(String filePath, String content) {
            String[] dirs = filePath.split("/");

            Node curNode = this.root;
            for (String dir : dirs) {
                if (dir.length() == 0) {
                    continue;
                }

                if (!curNode.children.containsKey(dir)) {
                    curNode.children.put(dir, new Node());
                }

                curNode = curNode.children.get(dir);
            }

            curNode.isFile = true;
            curNode.content += content;
        }

        public String readContentFromFile(String filePath) {
            String[] dirs = filePath.split("/");

            Node curNode = this.root;
            for (String dir : dirs) {
                if (dir.length() == 0) {
                    continue;
                }

                if (!curNode.children.containsKey(dir)) {
                    curNode.children.put(dir, new Node());
                }

                curNode = curNode.children.get(dir);
            }

            return curNode.content;
        }
    }

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * List<String> param_1 = obj.ls(path);
 * obj.mkdir(path);
 * obj.addContentToFile(filePath,content);
 * String param_4 = obj.readContentFromFile(filePath);
 */
}
