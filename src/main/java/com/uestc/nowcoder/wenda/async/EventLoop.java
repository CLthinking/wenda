package com.uestc.nowcoder.wenda.async;

import com.alibaba.fastjson.JSON;
import com.uestc.nowcoder.wenda.service.JedisService;
import com.uestc.nowcoder.wenda.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CLthinking
 * @date 2019/7/21 下午 08:25
 */

/**
 * EventLoop 实现了ApplicationContextAware与InitializingBean接口，关于Bean的生命周期可以参考
 * BeanFactory 类的doc
 */
@Service
public class EventLoop implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventLoop.class);

    // 事件派发的配置，保存每种事件对应的处理器
    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired
    JedisService jedisService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取到所有实现了EventHandler接口的类，注意，这些类必须定义为bean否则无法被spring发现
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
            // 获取EventHandler实现类支持的事件类型
            List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
            // 将其配置到事件分发器中
            for (EventType type : eventTypes) {
                if (!config.containsKey(type)) {
                    config.put(type, new ArrayList<>());
                }
                config.get(type).add(entry.getValue());
            }
        }
        // 启动一个线程，异步处理事件，实现解耦，这里使用了redis的阻塞队列，当然也可以使用jdk自带的阻塞对列
        new Thread(() -> {
            while (true) {
                String key = RedisKeyUtil.getEventQueueKey();
                List<String> events = jedisService.brpop(0, key);
                for (String msg : events) {
                    // 返回的第一个值是key，所以把它过滤掉
                    if (msg.equals(key)) {
                        continue;
                    }
                    EventModel eventModel = JSON.parseObject(msg, EventModel.class);
                    if (!config.containsKey(eventModel.getType())) {
                        logger.error("不能识别的事件");
                        continue;
                    }
                    for (EventHandler handler : config.get(eventModel.getType())) {
                        handler.doHandle(eventModel);
                    }
                }
            }
        }).start();
    }
}
