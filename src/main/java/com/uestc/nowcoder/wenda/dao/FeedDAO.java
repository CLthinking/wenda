package com.uestc.nowcoder.wenda.dao;

import com.uestc.nowcoder.wenda.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author CLthinking
 * @date 2019/7/22 下午 04:44
 */
@Component
@Mapper
public interface FeedDAO {
    // 表名
    String TABLE_NAME = " feed ";
    // 插入字段
    String INSERT_FIELDS = " user_id, data, created_date, type ";
    // 选择字段
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    // 插入一条新鲜事
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{data},#{createdDate},#{type})"})
    int addFeed(Feed feed);

    // 根据Feed id去取Feed,推模式，每次发生新鲜事了将Feed id推送给粉丝，粉丝就可以直接去读取Feed了
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Feed getFeedById(int id);

    // 拉模式，实现在resource目录下的FeedDAO中，去Feed表中找是userIds里面userId发生的新鲜事
    // ???? userId怎么来
    List<Feed> selectUserFeeds(@Param("maxId") int maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);
}