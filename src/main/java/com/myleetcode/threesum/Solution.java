package com.myleetcode.threesum;

class Solution {
    // 请教了下leetcode之后的解法
    // 这样的话twoSum的那道题应该可以改用这里的写法
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> solution = new ArrayList<>();

         // special case
         if (nums == null || nums.length < 3){
            return solution;
        }

        // must sort first, this is the soul guys.
        Arrays.sort(nums);

        for (int i = 0; i + 2 < nums.length; i++) {
            // skip same result
            if (i > 0 && nums[i] == nums[i - 1]) {              
                continue;
            }

            int target = 0;
            int start = i+1;
            int end = nums.length - 1;
            int a = nums[i];
            target = target -a;
            twoSum(nums, a, start, end, target, solution);
        }

        return solution;
    }

    private void twoSum(int[] nums,int a, int start, int end, int target,  List<List<Integer>> solution){
        while (start < end) {
            if (nums[start] + nums[end] == target) {
                solution.add(Arrays.asList(a, nums[start], nums[end]));
                start++;
                end--;
                while (start < end && nums[start] == nums[start - 1]) start++;  // skip same result
                while (start < end && nums[end] == nums[end + 1]) end--;  // skip same result
            } else if (nums[start] + nums[end] > target) {
                end--;
            } else {
                start++;
            }
        }
    }

/*
    // 然后就简单的改了下，来达到不从同一个位置取数的目的
    // 但是会有一个很尴尬的问题出现，很难检查重复
    // 比如，input: [-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0], 那么expected:[[-5,1,4],[-4,0,4],[-4,1,3],[-2,-2,4],[-2,1,1],[0,0,0]]
    // 但输出确却是：[[-4,1,3],[-4,4,0],[-2,1,1],[-2,4,-2],[1,-5,4]]
    // 可以看到少了[0,0,0]这个组合。原因是在noDuplicate中通过contains判断的话，这样的值通不过检测。
    // 但是这里就很难解决了，很难找到合适的方法来保证，在abc三个值相同的时候，我们只把[a,b,c]存入solution 一 次。
        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> solution = new ArrayList<List<Integer>>();
            
            // special case
            if (nums == null || nums.length < 3){
                return solution;
            }
            
           
            int len = nums.length;
             // reduce to two-sum
            for(int i = 0; i<len-2; i++){
                int target = 0;
                int a = nums[i];
                target = target - a;
                twoSum(nums, a, i+1, target,len, solution);
            }
            
            return solution;
        }
        
        private void twoSum(int[] nums,int a, int start, int target,int len,List<List<Integer>> solution){
            for(int i = start; i<len-1; i++){
                for(int j = i+1; j<len;j++){
                    if(nums[i] == target - nums[j]){
                        int b = nums[i];
                        int c = nums[j];
                        if( noDuplicate(solution, a,b,c ) == true){
                            List<Integer> result = new ArrayList<Integer>();
                            result.add(a);
                            result.add(b);
                            result.add(c);
                            solution.add(result);
                        }
                    }  
                }
            } 
        }
        
        private Boolean noDuplicate(List<List<Integer>> solution, int a, int b, int c){
            for(int i = 0; i < solution.size(); i++){
                List<Integer> result = solution.get(i);
                if(result.contains(a) && result.contains(b) && result.contains(c)){
                    return false;
                }
            }
            return true;
        }
*/

/*
    // 这是最初的解法，是错误的
    // 原因是这里实际上忘记了交流，以为可以有重复的元素。但实际应该仔细看一下例子，例子里面，solution里面没有[2, 2, -4]这个result
    // 说明，每个元素是只访问一次的在每个result里面
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> solution = new ArrayList<List<Integer>>();
        
        // special case
        if (nums == null || nums.length == 0){
            return solution;
        }
        
       
        int len = nums.length;
        int target = 0;
            
         // reduce to two-sum
        for(int i = 0; i<len; i++){
            int a = nums[i];
            target = target - a;
            twoSum(nums, a, i, target,len, solution);
        }
        
        return solution;
    }
    
    private void twoSum(int[] nums,int a, int start, int target,int len,List<List<Integer>> solution){
        for(int i = start; i<len; i++){
            for(int j = i; j<len;j++){
                if(nums[i] == target - nums[j]){
                    int b = nums[i];
                    int c = nums[j];
                    if( checkDuplicate(solution, a,b,c ) == true){
                        List<Integer> result = new ArrayList<Integer>();
                        result.add(a);
                        result.add(b);
                        result.add(c);
                        solution.add(result);
                    }
                }  
            }
        } 
    }
    
    private Boolean checkDuplicate(List<List<Integer>> solution, int a, int b, int c){
        for(int i = 0; i < solution.size(); i++){
            List<Integer> result = solution.get(i);
            if(result.contains(a) && result.contains(b) && result.contains(c)){
                return false;
            }
        }
        return true;
    }
    */
}