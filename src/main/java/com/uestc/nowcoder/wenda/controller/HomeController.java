package com.uestc.nowcoder.wenda.controller;

import com.uestc.nowcoder.wenda.model.EntityType;
import com.uestc.nowcoder.wenda.model.HostHolder;
import com.uestc.nowcoder.wenda.model.Question;
import com.uestc.nowcoder.wenda.model.ViewObject;
import com.uestc.nowcoder.wenda.service.FollowService;
import com.uestc.nowcoder.wenda.service.QuestionService;
import com.uestc.nowcoder.wenda.service.UserService;
import com.uestc.nowcoder.wenda.util.DateUtil;
import org.apache.velocity.tools.generic.DateTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author CLthinking
 * @date 2019/7/18 上午 10:50
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    FollowService followService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(value = {"/user/{userId}"}, method = {RequestMethod.GET})
    public String userIndex(Model model,
                            @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 10));
        return "index";
    }

    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questions = questionService.getLatestQuestions(userId, 0, 10);

        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questions) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
            vo.set("user", userService.getUser(question.getUserId()));
            vo.set("date", DateUtil.SIMPLE_DATE_FORMAT.format(question.getCreatedDate()));
            vos.add(vo);
        }
        return vos;
    }


    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET})
    public String index(Model model) {
        model.addAttribute("vos", getQuestions(0, 0, 10));
        return "index";
    }

}
