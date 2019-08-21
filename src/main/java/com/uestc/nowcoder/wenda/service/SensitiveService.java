package com.uestc.nowcoder.wenda.service;

import com.uestc.nowcoder.wenda.controller.HomeController;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CLthinking
 * @date 2019/7/19 上午 10:52
 */

/**
 *  采用前缀树实现敏感词过滤服务，敏感词采用了https://github.com/jkiss/sensitive-words
 *  与https://github.com/fighting41love/funNLP/tree/master/data/%E6%95%8F%E6%84%9F%E8%AF%8D%E5%BA%93
 *  SensitiveService类实现了InitializingBean接口的afterPropertiesSet方法，该方法会在所有属性被设置完后
 *  被BeanFactory调用，完整的spring bean 生命周期可以参考BeanFactory
 */
@Service
public class SensitiveService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);
    private static final String SENSITIVE_REPLACEMENT = "***";

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                addWord(lineTxt.trim());
            }
            bufferedReader.close();
        } catch (Exception e) {
            logger.error("读取敏感词SensitiveWords.txt失败" + e.getMessage());
        }
    }

    // 增加关键词
    private void addWord(String lineTxt) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < lineTxt.length(); i++) {
            char c = lineTxt.charAt(i);

            // 判断是否是一个符号字符，符号字符空格，标点符号不增加到前缀树中
            if (isSymbol(c)) {
                continue;
            }

            TrieNode node = tempNode.getSubNode(c);

            if (node == null) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }
            tempNode = node;
        }
        tempNode.setKeywordEnd(true);
    }

    private class TrieNode {
        // 是不是关键词的结尾
        private boolean end = false;

        // 单向结点下的所有子节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        public TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        public boolean isKeywordEnd() {
            return end;
        }

        public void setKeywordEnd(boolean end) {
            this.end = end;
        }

    }

    // 根节点，根节点不储存信息
    private TrieNode rootNode = new TrieNode();

    private boolean isSymbol(char c) {
        // CharUtils.isAsciiAlphanumeric(c)如果是大小写字母或者数字返回true,或者返回false
        // unicode码在[0x2e80-0x9fff]表示东亚文字
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FE6);
    }

    // 双指针，敏感词过滤算法
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        StringBuilder result = new StringBuilder();
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;

        while (position < text.length()) {
            char c = text.charAt(position);

            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }

            tempNode = tempNode.getSubNode(c);

            if (tempNode == null) {
                result.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()){  // 发现敏感词
                result.append(SENSITIVE_REPLACEMENT);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            } else {
                ++position;
            }
        }
        result.append(text.substring(begin));

        return result.toString();
    }


//    public static void main(String[] args) {
//        SensitiveService s = new SensitiveService();
//        s.addWord("色情");
//        s.addWord("赌博");
//
//        System.out.println(s.filter("hi  你好，赌  博  "));
//    }
}
