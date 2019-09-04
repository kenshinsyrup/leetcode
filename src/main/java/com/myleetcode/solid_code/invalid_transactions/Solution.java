package com.myleetcode.solid_code.invalid_transactions;

import java.util.*;

class Solution {
    public List<String> invalidTransactions(String[] transactions) {
        return invalidTransactionsByTraverse(transactions);
    }


    // use a Map to store name-transaction pair
    // for each name, traverse all transactions:
    //  if same city and within 60 mins, invalid; to reach same city faster, use another map to store city-transaction pair, so the outter map is name-map:city-transaction
    //  if over 1000, invalid
    // put all invalid transaction to a Set to avoid multiple put
    class Transaction{
        String name;
        int time;
        int amount;
        String city;

        public Transaction(String name, int time, int amount, String city){
            this.name = name;
            this.time = time;
            this.amount = amount;
            this.city = city;
        }

        public String toString(){
            return this.name + "," + this.time + "," + this.amount + "," + this.city;
        }
    }
    // TC: O(M^2 * L), M is transaction number of one name, L is name num
    // SC: O(N), N is transaction number
    private List<String> invalidTransactionsByTraverse(String[] transactions){
        List<String> ret = new ArrayList<>();
        if(transactions == null || transactions.length == 0){
            return ret;
        }

        Map<String, List<Transaction>> map = new HashMap<>();
        for(String str: transactions){
            String[] detail = str.split(",");
            Transaction tran = new Transaction(detail[0], Integer.valueOf(detail[1]), Integer.valueOf(detail[2]), detail[3]);

            map.putIfAbsent(tran.name, new ArrayList<>());
            map.get(tran.name).add(tran);
        }

        Set<Transaction> retSet = new HashSet<>();
        for(String name: map.keySet()){
            List<Transaction> tranList = map.get(name);

            for(int i = 0; i < tranList.size(); i++){
                Transaction tranI = tranList.get(i);
                if(tranI.amount > 1000){
                    retSet.add(tranI);
                }

                for(int j = i + 1; j < tranList.size(); j++){
                    Transaction tranJ = tranList.get(j);

                    if(!tranI.city.equals(tranJ.city) && Math.abs(tranI.time - tranJ.time) <= 60){
                        retSet.add(tranI);
                        retSet.add(tranJ);
                    }
                }
            }
        }

        for(Transaction tran: retSet){
            ret.add(tran.toString());
        }

        return ret;
    }
}
