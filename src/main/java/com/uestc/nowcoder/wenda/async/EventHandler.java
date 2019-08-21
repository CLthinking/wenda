package com.uestc.nowcoder.wenda.async;

import java.util.List;

/**
 * @author CLthinking
 * @date 2019/7/21 下午 08:23
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
