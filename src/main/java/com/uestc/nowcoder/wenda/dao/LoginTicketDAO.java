package com.uestc.nowcoder.wenda.dao;

import com.uestc.nowcoder.wenda.model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author CLthinking
 * @date 2019/7/18 下午 07:50
 */
@Mapper
@Component
public interface LoginTicketDAO {
    // 表名
    String TABLE_NAME = "login_ticket";
    // 插入字段
    String INSERT_FIELDS = " user_id, expired, status, ticket ";
    // 选择字段
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    // 往ticket表中插入一个ticket
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    // 根据ticket类容获取LoginTicket，LoginTicket里面保存着userId,这样我们就知道了是哪一个用户了，所以ticket的内容应该全局唯一
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    // 更新ticket的状态
    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}