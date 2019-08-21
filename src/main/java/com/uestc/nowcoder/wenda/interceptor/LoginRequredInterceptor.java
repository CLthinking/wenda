package com.uestc.nowcoder.wenda.interceptor;

import com.uestc.nowcoder.wenda.dao.LoginTicketDAO;
import com.uestc.nowcoder.wenda.dao.UserDAO;
import com.uestc.nowcoder.wenda.model.HostHolder;
import com.uestc.nowcoder.wenda.model.LoginTicket;
import com.uestc.nowcoder.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author CLthinking
 * @date 2019/7/18 下午 09:56
 */
@Component
public class LoginRequredInterceptor implements HandlerInterceptor  {

    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (hostHolder.getUser() == null) {
            response.sendRedirect("/reglogin?next=" + request.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
