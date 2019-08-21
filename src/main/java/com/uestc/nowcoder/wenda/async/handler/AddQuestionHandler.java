package com.uestc.nowcoder.wenda.async.handler;

import com.uestc.nowcoder.wenda.async.EventHandler;
import com.uestc.nowcoder.wenda.async.EventModel;
import com.uestc.nowcoder.wenda.async.EventType;
import com.uestc.nowcoder.wenda.controller.CommentController;
import com.uestc.nowcoder.wenda.model.Message;
import com.uestc.nowcoder.wenda.model.User;
import com.uestc.nowcoder.wenda.service.MessageService;
import com.uestc.nowcoder.wenda.service.SearchService;
import com.uestc.nowcoder.wenda.service.UserService;
import com.uestc.nowcoder.wenda.util.WendaUtil;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author CLthinking
 * @date 2019/8/19 下午 05:21
 */
@Component
public class AddQuestionHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(AddQuestionHandler.class);

    @Autowired
    SearchService searchService;

    // 每次增加一个问题后，调用solr将新增加的问题入库
    @Override
    public void doHandle(EventModel model) {
       try {
           searchService.indexQuestion(model.getEntityId(),
                   model.getExt("title"), model.getExt("content"));
       } catch (Exception e) {
           logger.error("增加题目索引失败");
       }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.ADD_QUESTION);
    }
}
