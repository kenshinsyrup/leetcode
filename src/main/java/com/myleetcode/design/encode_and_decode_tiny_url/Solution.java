package com.myleetcode.design.encode_and_decode_tiny_url;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Solution{

    class Codec {

        // 这个题目的solution区不错，值得看，是经典的短URL encode/decode思路讲解

        // 这里选用个人觉得最好的一种解法

        // 62位数字英文组合用于encode出短URL
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Random用于生成随机数[0,62)
        Random random = new Random();

        // map用于存储 短url-URL 键值对用于decode
        Map<String, String> shortLongURLMap = new HashMap<>();

        // 生成random string, 这里我们选择生成6位长度的随机字符串，长度实际是由开发者自己控制的，只要在62位长度以内即可，对于选定的任意长度n，可以得到的随机字符串个数为: (10 + 26 * 2)^n 即62^n
        private String getRandomString(){
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < 6; i++){
                sb.append(alphabet.charAt(random.nextInt(62)));
            }
            return sb.toString();
        }

        // Encodes a URL to a shortened URL.
        public String encode(String longUrl) {
            String encodedString = getRandomString();

            // 检查唯一性
            while(shortLongURLMap.containsKey(encodedString)){
                encodedString = getRandomString();
            }

            String shortUrl = "http://tinyurl.com/" + encodedString;

            shortLongURLMap.put(shortUrl, longUrl);

            return shortUrl;
        }

        // Decodes a shortened URL to its original URL.
        public String decode(String shortUrl) {
            return shortLongURLMap.getOrDefault(shortUrl, "");
        }
    }

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(url));
}

