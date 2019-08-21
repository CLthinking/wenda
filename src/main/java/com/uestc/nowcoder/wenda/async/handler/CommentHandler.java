package com.uestc.nowcoder.wenda.async.handler;

import com.uestc.nowcoder.wenda.async.EventHandler;
import com.uestc.nowcoder.wenda.async.EventModel;
import com.uestc.nowcoder.wenda.async.EventType;
import com.uestc.nowcoder.wenda.service.CommentService;
import com.uestc.nowcoder.wenda.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author CLthinking
 * @date 2019/8/20 下午 09:09
 */
@Component
public class CommentHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(AddQuestionHandler.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    // 每次有评论发生后，更新对应问题的评论数量
    @Override
    public void doHandle(EventModel model) {
        int count = commentService.getCommentCount(model.getEntityId(), model.getEntityType());
        questionService.updateCommentCount(model.getEntityId(), count);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.COMMENT);
    }
}
