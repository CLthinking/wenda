package com.uestc.nowcoder.wenda.model;

import java.util.Date;

/**
 * @author CLthinking
 * @date 2019/7/18 上午 10:18
 */
public class Question {
    // 问题id
    private int id;
    // 问题的标题
    private String title;
    // 问题的内容
    private String content;
    // 问题的创建时间
    private Date createdDate;
    // 外键，这个问题是由谁发布的，知道的userId就可以得到该用户的详细信息了
    private int userId;
    // 问题的评论数量
    private int commentCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", userId=" + userId +
                ", commentCount=" + commentCount +
                '}';
    }
}
