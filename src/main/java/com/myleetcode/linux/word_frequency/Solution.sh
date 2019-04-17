# Read from the file words.txt and output the word frequency list to stdout.
# https://leetcode.com/problems/word-frequency/discuss/55462/Solution-using-awk-and-pipes-with-explaination

awk '\
{for(i=1; i<=NF; i++){Dict[$i]++;}}\
END\
{for(i in Dict){print(i, Dict[i]);}\
}' words.txt | sort -nr -k 2