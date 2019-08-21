package com.uestc.nowcoder.wenda.async;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CLthinking
 * @date 2019/7/21 下午 08:05
 */
public class EventModel {
    private EventType type;  // 事件类型，喜欢，评论，登录....
    private int actorId;     // 事件由谁触发的
    private int entityType;  // 触发事件的类型
    private int entityId;    // 触发事件的Id
    private int entityOwnerId;  // 被触发实体谁拥有

    // 附加参数
    private Map<String, String> exts = new HashMap<>();

    public EventModel() {}

    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }

    public EventModel(EventType type) {
        this.type = type;
    }


    public String getExt(String key) {
        return exts.get(key);
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
