package com.myleetcode.union_find.accounts_merge;

import java.util.*;

class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        return accountsMergeByUF(accounts);
    }

    // accounts could be looked as some connected components, every elem of accounts, is a account, and in every account, the first elem is its name, and others are its emails.
    // first we could make a email -> ownerName map to store all emails and its correspoinding owner name, we will nee tis at last step to build ret. then we build the edges List to make UF
    // then we make a rootMap to store email as key and its root as value, do UF on this with edge List.
    // at last, we need put all emails in one DS together, but we only has the email->rootEmail map after UF, so we need another map store rootEmail->emailsSet map. To build this unionSetMap, we need first do a whole path compression on the rootMap to make sure all email key stores its DS's final root, this is important. Then, since we need sorted order emails, so the emailsSet in unionSetMap should be a TreeSet.
    // after we get the unionSetMap, we could build the ret finally. we could get the username from key(email) in unionSetMap, then add it to newAcccount List, then add the TreeSet of this key(email), then add the newAccount to ret
    // TC: Time Complexity: O(A * log A), where is the sum of all account length
    // SC: O(A)
    private List<List<String>> accountsMergeByUF(List<List<String>> accounts){
        List<List<String>> ret = new ArrayList<>();

        if(accounts == null || accounts.size() == 0 || accounts.get(0) == null || accounts.get(0).size() == 0){
            return ret;
        }

        // 1 init.
        // emailNameMap link email and name, ie the email and its owner
        Map<String, String> emailNameMap = new HashMap<>();
        // edgeList link email and email in one account
        List<String[]> edgeList = new ArrayList<>();
        for(List<String> account: accounts){
            String name = account.get(0);

            int size = account.size();

            for(int i = 1; i < size; i++){
                // email -> owner name
                emailNameMap.put(account.get(i), name);
            }

            // edges
            if(size == 2){ // only one email under current account
                edgeList.add(new String[]{account.get(1), account.get(1)});
            }else{ // more than one email
                for(int i = 1; i < size - 1; i++){
                    edgeList.add(new String[]{account.get(i), account.get(i + 1)});
                }
            }
        }

        int emailNum = emailNameMap.size();

        // 2 UF in DS(Disjoint Set)
        // rootMap: email -> its root email in DS, at first every email's root is itself
        Map<String, String> rootMap = new HashMap<>();
        for(String email: emailNameMap.keySet()){
            rootMap.put(email, email);
        }

        for(String[] edge: edgeList){
            String emailU = edge[0];
            String emailV = edge[1];

            String emailURoot = find(rootMap, emailU);
            String emailVRoot = find(rootMap, emailV);

            if(emailURoot != emailVRoot){
                union(rootMap, emailURoot, emailVRoot);
            }
        }
        /*在上面这个操作中需要注意的并非各个节点都进行了 path compression因此借助 find 方法找到各个节点的parent来保证rootMap中每个key都存储都是自己所在DS的最上面的root*/
        // !!! path compression for all email to make all emails in one set have the same root
        for(String email: emailNameMap.keySet()){
            find(rootMap, email);
        }

        // 3 把email -> its root email的map转成root email -> emails tree set map, 之所以要用treeset是因为最终的输出emails要sorted order
        Map<String, TreeSet<String>> unionSetMap = new HashMap<>();
        for(String email: rootMap.keySet()){
            String rootEmail = rootMap.get(email);
            unionSetMap.putIfAbsent(rootEmail, new TreeSet<>());
            unionSetMap.get(rootEmail).add(email);
        }

        // 4 build ret
        for(String rootEmail: unionSetMap.keySet()){
            String name = emailNameMap.get(rootEmail);

            List<String> newAccount = new ArrayList<>();
            newAccount.add(name);
            for(String email: unionSetMap.get(rootEmail)){
                newAccount.add(email);
            }

            ret.add(newAccount);
        }

        return ret;
    }

    private String find(Map<String, String> rootMap, String email){
        if(!rootMap.get(email).equals(email)){
            rootMap.put(email,  find(rootMap, rootMap.get(email)));
        }

        return rootMap.get(email);
    }

    private void union(Map<String, String> rootMap, String u, String v){
        rootMap.put(u, v);
    }
}
