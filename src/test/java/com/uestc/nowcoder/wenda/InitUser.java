package com.uestc.nowcoder.wenda;

import com.uestc.nowcoder.wenda.dao.UserDAO;
import com.uestc.nowcoder.wenda.model.User;
import com.uestc.nowcoder.wenda.service.UserService;
import com.uestc.nowcoder.wenda.util.WendaUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author CLthinking
 * @date 2019/8/20 下午 10:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
public class InitUser {

    @Autowired
    UserDAO userDAO;

    static List<String> username ;

    @BeforeClass
    public static void initName() throws IOException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("username.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String lineTxt;
        Set<String> set = new HashSet<>();
        while ((lineTxt = bufferedReader.readLine()) != null) {
            set.add(lineTxt);
        }
        bufferedReader.close();
        username = new ArrayList<>(set);
    }

    @Test
    public void initUser(){
        for (int i = 0; i < 1000; i++) {
            // 说明传入的用户名和密码均是合法的
            User user = new User();
            int idx = new Random().nextInt(username.size());
            user.setName(username.get(idx));
            username.remove(idx);
            // 每个用户都有一个salt,用于安全，后台的数据库不能保存明文密码
            user.setSalt(UUID.randomUUID().toString().substring(0,5));
            user.setHeadUrl(String.format("http://39.108.231.148/%d.jpeg", new Random().nextInt(28107) + 1));
            // 用户输入的密码和每个用户的salt经过MD5算法后过程保存在数据库中的算法
            user.setPassword(WendaUtil.MD5( "123456"+ user.getSalt()));
            userDAO.addUser(user);
        }
    }
}
