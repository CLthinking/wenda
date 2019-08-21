package com.uestc.nowcoder.wenda.model;

import org.springframework.stereotype.Component;

/**
 * @author CLthinking
 * @date 2019/7/18 下午 09:06
 */
@Component
public class HostHolder {
    // 线程本地变量，源码解析见：https://blog.csdn.net/qq_22158743/article/details/98381002
    // ThreadLocal 的经典用法，private static
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUsers(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
