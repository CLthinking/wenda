package com.uestc.nowcoder.wenda.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

// Feed流，新鲜事
public class Feed {
    // 新鲜事的id
    private int id;
    // 新鲜事的类型，如：评论， 关注，点赞等
    private int type;
    // 新鲜事的由谁产生的，谁点赞了，谁关注了等待
    private int userId;
    // 新鲜事的产生时间
    private Date createdDate;

    //新鲜事的数据，不同类型新鲜事的数据都不一样， JSON格式
    // JSON
    private String data;
    private JSONObject dataJSON = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        dataJSON = JSONObject.parseObject(data);
    }
    public String get(String key) {
        return dataJSON == null ? null : dataJSON.getString(key);
    }
}
