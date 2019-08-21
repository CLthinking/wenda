package com.uestc.nowcoder.wenda.dao;

import com.uestc.nowcoder.wenda.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentDAO {
    // 表名
    String TABLE_NAME = " comment ";
    // 插入字段
    String INSERT_FIELDS = " user_id, content, created_date, entity_id, entity_type, status ";
    // 选择字段
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    // 传入一条评论
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    // 更新评论状态
    @Update({"update ", TABLE_NAME, " set status=#{status} where entity_id=#{entityId} and entity_type=#{entityType}"})
    void updateStatus(@Param("entityId") int entityId, @Param("entityType") int entityType, @Param("status") int status);

    // 选出某一个实体(由entityType与entityId唯一确定)比如说问题的所有评论，根据时间排序
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    // 获得某一个实体的评论数量
    @Select({"select count(id) from ", TABLE_NAME, " where entity_id=#{entityId} and entity_type=#{entityType} "})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    // 根据Id获取评论
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Comment getCommentById(int id);
}
