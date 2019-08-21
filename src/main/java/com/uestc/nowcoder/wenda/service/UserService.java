package com.uestc.nowcoder.wenda.service;

import com.uestc.nowcoder.wenda.dao.LoginTicketDAO;
import com.uestc.nowcoder.wenda.dao.UserDAO;
import com.uestc.nowcoder.wenda.model.LoginTicket;
import com.uestc.nowcoder.wenda.model.User;
import com.uestc.nowcoder.wenda.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author CLthinking
 * @date 2019/7/18 下午 01:25
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    /**
     * 用户注册
     * @param username 用户名
     * @param passwd 密码
     * @return 如果注册失败，返回注册失败的信息；注册成功返回对应的ticket
     */
    public Map<String, String> register(String username, String passwd) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(passwd)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);
        if (user != null) {
            map.put("msg", "用户名已经被注册");
            return map;
        }
        // 说明传入的用户名和密码均是合法的
        user = new User();
        user.setName(username);
        // 每个用户都有一个salt,用于安全，后台的数据库不能保存明文密码
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://39.108.231.148/%d.jpeg", new Random().nextInt(1000)));
        // 用户输入的密码和每个用户的salt经过MD5算法后过程保存在数据库中的算法
        user.setPassword(WendaUtil.MD5(passwd+user.getSalt()));
        userDAO.addUser(user);

        // 注册成功，返回ticket,这里的userId是mybatis回填的
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    /**
     * 根据用户Id创建一个ticket
     * @param uesrId 用户名
     * @return 服务器下发的ticket
     */
    private String addLoginTicket(int uesrId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(uesrId);
        Date now = new Date();
        // 这里简单的将过期时间设置为100天
        now.setTime(3600*24*100 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        // 全局唯一的ticket
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    /**
     * 用户登入，如果登入失败，返回失败的原因；否则返回服务器准备下发的ticket
     * @param username 用户名
     * @param passwd 密码
     * @return 登入失败，返回失败的原因；否则返回服务器准备下发的ticket
     */
    public Map<String, String> login(String username, String passwd) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(passwd)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);
        if (user == null) {
            map.put("msg", "用户不存在");
            return map;
        }
        if (!Objects.equals(WendaUtil.MD5(passwd + user.getSalt()), user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }

        // 这里应该还需要优化xxxxxxx,判断用户是否已经带有cookie
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;

    }

    // 登出，将ticket的状态由0改成1
    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }



    // 根据用户名获取User, 在我们的系统中用户名不能重复
    public User selectByName(String name) {
        return userDAO.selectByName(name);
    }

    // 根据用户id获取User
    public User getUser(int id) {
        return userDAO.selectById(id);
    }
}
