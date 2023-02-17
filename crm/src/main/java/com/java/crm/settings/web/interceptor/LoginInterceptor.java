package com.java.crm.settings.web.interceptor;

import com.java.crm.common.contants.Contants;
import com.java.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author qqyqq
 * @create 2023-02-16 20:21
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //如果用户没有登录成功,则跳转到登录页面
        HttpSession session = httpServletRequest.getSession();
        User user=(User)session.getAttribute(Contants.SESSION_USER);

        if(user == null){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());//重定向时，url必须加项目的名称
            //springmvc 重定向的时候不需要加项目名，会自己翻译
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
