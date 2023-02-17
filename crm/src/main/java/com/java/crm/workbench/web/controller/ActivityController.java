package com.java.crm.workbench.web.controller;

import com.java.crm.settings.domain.User;
import com.java.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qqyqq
 * @create 2023-02-17 10:32
 */
@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        List<User> users = userService.queryAllUsers();
        request.setAttribute("userList", users);
        //请求转发到市场活动的主页面
        return "workbench/activity/index";
    }
}
