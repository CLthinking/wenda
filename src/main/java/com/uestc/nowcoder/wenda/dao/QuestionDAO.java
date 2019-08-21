package com.uestc.nowcoder.wenda.dao;

import com.uestc.nowcoder.wenda.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author CLthinking
 * @date 2019/7/18 上午 10:20
 */
@Mapper
@Component
public interface QuestionDAO {
    // 表名
    String TABLE_NAME = " question ";
    // 插入字段
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";
    // 选择字段
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    // 往question表中插入一个问题
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    // 根据qid选择问题
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Question selectById(int id);

    // 根据userId选择最新的问题，具体的实现在 resource目录下的QuestionDAO中
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    // 更新问题的评论数量
    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

}
