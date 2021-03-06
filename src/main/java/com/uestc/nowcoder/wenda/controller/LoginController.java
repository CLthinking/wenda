package com.uestc.nowcoder.wenda.controller;

import com.uestc.nowcoder.wenda.async.EventModel;
import com.uestc.nowcoder.wenda.async.EventProducer;
import com.uestc.nowcoder.wenda.async.EventType;
import com.uestc.nowcoder.wenda.dao.UserDAO;
import com.uestc.nowcoder.wenda.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.util.Map;

/**
 * @author CLthinking
 * @date 2019/7/18 下午 05:06
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

     @Autowired
    EventProducer eventProducer;

    @RequestMapping(value = "/reg", method = {RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "next", required = false) String next,
                      HttpServletResponse response) {
        try {
            Map<String, String> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);


                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next;
                }

                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean remeberme,
                        HttpServletResponse response) {
        try {
            Map<String, String> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }

    @RequestMapping(value = "/reglogin", method = {RequestMethod.GET})
    public String reg(Model model,
                      @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        return "login";
    }
}
