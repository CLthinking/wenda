package com.uestc.nowcoder.wenda.async;

/**
 * @author CLthinking
 * @date 2019/7/21 下午 07:58
 */
public enum EventType {
    // 喜欢
    LIKE(0),

    // 评论
    COMMENT(1),

    // 登入
    LOGIN(2),

    // 邮件
    MAIL(3),

    // 关注
    FOLLOW(4),

    // 取消关注
    UNFOLLOW(5),

    // 增加问题
    ADD_QUESTION(6);


    private int value;

    EventType(int value) {this.value = value;}
    public int getValue() {return value;}
}
