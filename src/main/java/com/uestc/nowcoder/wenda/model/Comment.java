package com.uestc.nowcoder.wenda.model;

import java.util.Date;

/**
 * Created by nowcoder on 2016/7/9.
 */
public class Comment {
    // 评论ID
    private int id;
    // 由谁评论的
    private int userId;
    // 评论的是什么东西，比如，可以评论问题，也可以评论评论
    private int entityType;
    //  对应类型的Id,比如，问题Id, 评论Id。entityType与entityType唯一确定一个实体
    private int entityId;
    // 评论的类容
    private String content;
    // 评论的创建时间
    private Date createdDate;
    // 评论的转态，默认0为有效
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
