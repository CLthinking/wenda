package com.uestc.nowcoder.wenda.dao;

import com.uestc.nowcoder.wenda.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author CLthinking
 * @date 2019/7/17 下午 09:20
 */
@Mapper
@Component
public interface UserDAO {
    // 表名
    String TABLE_NAME = " user ";
    // 插入字段
    String INSERT_FIELDS = " name, password, salt, head_url ";
    // 选择字段
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    // 往user表中插入一个用户
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    // 根据user id从user表中将用户选择出来
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    // 根据用户名选出用户，我们的网站不容许有重复的用户名，使用用户名是唯一的
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    // 更新用户密码
    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    // 删除用户
    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
