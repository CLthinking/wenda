package com.uestc.nowcoder.wenda.async.handler;

import com.uestc.nowcoder.wenda.async.EventHandler;
import com.uestc.nowcoder.wenda.async.EventModel;
import com.uestc.nowcoder.wenda.async.EventType;
import com.uestc.nowcoder.wenda.model.Message;
import com.uestc.nowcoder.wenda.model.User;
import com.uestc.nowcoder.wenda.service.MessageService;
import com.uestc.nowcoder.wenda.service.UserService;
import com.uestc.nowcoder.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author CLthinking
 * @date 2019/7/21 下午 08:47
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName() +
                "赞了你的评论，http://127.0.0.1:8080/question/" + model.getExt("questionId"));

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
