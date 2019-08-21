package com.uestc.nowcoder.wenda.async;

import com.alibaba.fastjson.JSONObject;
import com.uestc.nowcoder.wenda.service.JedisService;
import com.uestc.nowcoder.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CLthinking
 * @date 2019/7/21 下午 08:13
 */
@Service
public class EventProducer {
    @Autowired
    JedisService jedisService;

    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisService.lpush(key,json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
