package com.myleetcode.employee_importance;

import com.myleetcode.utils.employee.Employee;

import java.util.*;

/*
// Employee info
class Employee {
    // It's the unique id of each node;
    // unique id of this employee
    public int id;
    // the importance value of this employee
    public int importance;
    // the id of direct subordinates
    public List<Integer> subordinates;
};
*/
class Solution {
    public int getImportance(List<Employee> employees, int id) {
        // bfs traverse直到最后一层，每层记录层内所有node的importance value
        
        int value = 0;
        
        // special case
        if(employees == null){
            return value;
        }
        
        // 构造一个map将的通过id来找到employee的操作从list需要的O(n)降低到O(1)
        Map<Integer, Employee> employeeMap = new HashMap<>();
        for(Employee e: employees){
            employeeMap.put(e.id, e);
        }
        
        // special case 2
        Employee employee = employeeMap.get(id);
        if(employee == null){
            return value;
        }
        
        value = traverseByBFS(employeeMap, employee);
        
        return value;
        
    }
    
    private int traverseByBFS(Map<Integer, Employee> employeeMap, Employee employee){
        Queue<Employee> employeeQueue = new LinkedList<Employee>();
        
        employeeQueue.add(employee);
        
        int value = 0;
        
        while(!employeeQueue.isEmpty()){
            Employee currentEmployee = employeeQueue.poll();
            
            value += currentEmployee.importance;
            
            if(currentEmployee.subordinates != null){
                for(Integer subordinatesID: currentEmployee.subordinates){
                    employeeQueue.add(employeeMap.get(subordinatesID));
                }
            }
        }
        
        return value;
    }
}