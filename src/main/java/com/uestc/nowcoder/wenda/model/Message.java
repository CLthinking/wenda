package com.uestc.nowcoder.wenda.model;

import java.util.Date;

/**
 * Created by nowcoder on 2016/7/9.
 */
// 站内信
public class Message {
    // 站内信id
    private int id;
    // 是谁发的站内信，userId
    private int fromId;
    // 发给谁的， uesrId
    private int toId;
    // 站内信的内容
    private String content;
    // 创建日期
    private Date createdDate;
    // 是否读了, 0表示未读
    private int hasRead;
    // 会话ID,一次对话
    private String conversationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public String getConversationId() {
        if (fromId < toId) {
            return String.format("%d_%d", fromId, toId);
        }
        return String.format("%d_%d", toId, fromId);
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
