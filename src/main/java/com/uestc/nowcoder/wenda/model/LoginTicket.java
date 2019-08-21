package com.uestc.nowcoder.wenda.model;

import java.util.Date;

/**
 * @author CLthinking
 * @date 2019/7/18 下午 07:49
 */
// 登入凭证
public class LoginTicket {
    // id，唯一标识符
    private int id;
    // 是哪一个用户持有该ticket
    private int userId;
    // 失效时间
    private Date expired;
    // 状态，比如用户登出时，更改ticket状态
    private int status;// 0有效，1无效
    // ticket的内容，下发的cookie中保存的数据
    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

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

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
