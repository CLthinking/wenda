package com.uestc.nowcoder.wenda.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CLthinking
 * @date 2019/7/18 下午 01:31
 */
// vo类
public class ViewObject {
    // 一个vo可以容纳各种信息
    private Map<String, Object> objectMap = new HashMap<>();


    public void set(String key, Object vlaue) {
        objectMap.put(key, vlaue);
    }

    public Object get(String key) {
        return objectMap.get(key);
    }
}
