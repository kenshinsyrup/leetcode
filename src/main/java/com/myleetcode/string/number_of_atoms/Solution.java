package com.myleetcode.string.number_of_atoms;

import java.util.*;

public class Solution {
    public String countOfAtoms(String formula) {
        return countOfAtomsByRecursion(formula);
    }

    /*
    https://leetcode.com/problems/number-of-atoms/discuss/109346/Java-solution-Map-%2B-Recursion

    */
    private String countOfAtomsByRecursion(String formula) {
        if (formula == null || formula.length() == 0) {
            return "";
        }

        // Atom -> num map.
        Map<String, Integer> map = countHelper(formula);

        // Ascending order atoms.
        List<String> atoms = new ArrayList<>(map.keySet());
        Collections.sort(atoms, (String a, String b) -> {
            return a.compareTo(b);
        });

        // Build res.
        StringBuilder sb = new StringBuilder();
        for (String atom : atoms) {
            sb.append(atom + (map.get(atom) > 1 ? map.get(atom) : ""));
        }

        return sb.toString();

    }

    private Map<String, Integer> countHelper(String formula) {
        Map<String, Integer> map = new HashMap<>();
        if (formula == null || formula.length() == 0) {
            return map;
        }

        int len = formula.length();
        int i = 0;
        while (i < formula.length()) {
            if (formula.charAt(i) == '(') {
                // Get map of subformula in ().
                int count = 0;
                int j = i;
                while (j < len) {
                    if (formula.charAt(j) == '(') {
                        count++;
                    } else if (formula.charAt(j) == ')') {
                        count--;
                    }

                    if (count == 0) {
                        break;
                    }

                    j++;
                }
                Map<String, Integer> subMap = countHelper(formula.substring(i + 1, j));

                j++;

                // Get num.
                int num = 1;
                int k = j;
                while (k < formula.length() && Character.isDigit(formula.charAt(k))) {
                    k++;
                }
                if (k > j) {
                    num = Integer.parseInt(formula.substring(j, k));
                }

                // Update the map with num and subformula map.
                for (String atom : subMap.keySet()) {
                    map.put(atom, map.getOrDefault(atom, 0) + subMap.get(atom) * num);
                }

                i = k;
            } else {
                // Now char at i is a upper case char.
                // Make j is i+1, check whether this atom is a mix of uper and lower case chars.
                int j = i + 1;
                while (j < len && formula.charAt(j) >= 'a' && formula.charAt(j) <= 'z') {
                    j++;
                }

                // Get num.
                int num = 1;
                int k = j;
                while (k < len && formula.charAt(k) >= '0' && formula.charAt(k) <= '9') {
                    k++;
                }
                if (k > j) {
                    num = Integer.parseInt(formula.substring(j, k));
                }

                String atom = formula.substring(i, j);
                map.put(atom, map.getOrDefault(atom, 0) + num);

                i = k;
            }
        }

        return map;
    }
}
