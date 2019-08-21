package com.uestc.nowcoder.wenda.controller;

import com.uestc.nowcoder.wenda.async.EventModel;
import com.uestc.nowcoder.wenda.async.EventProducer;
import com.uestc.nowcoder.wenda.async.EventType;
import com.uestc.nowcoder.wenda.model.Comment;
import com.uestc.nowcoder.wenda.model.EntityType;
import com.uestc.nowcoder.wenda.model.HostHolder;
import com.uestc.nowcoder.wenda.service.CommentService;
import com.uestc.nowcoder.wenda.service.LikeService;
import com.uestc.nowcoder.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.html.parser.Entity;

/**
 * @author CLthinking
 * @date 2019/7/20 下午 10:38
 */
@Controller
public class LikeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }
        Comment comment = commentService.getCommentById(commentId);
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                                    .setActorId(hostHolder.getUser().getId())
                                    .setEntityId(commentId)
                                    .setEntityType(EntityType.ENTITY_COMMENT)
                                    .setExt("questionId", String.valueOf(comment.getEntityId()))
                                    .setEntityOwnerId(comment.getUserId()));

        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }
        long dislikeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(dislikeCount));
    }


}
