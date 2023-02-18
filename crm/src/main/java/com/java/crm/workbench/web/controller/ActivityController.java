package com.java.crm.workbench.web.controller;

import com.java.crm.common.contants.Contants;
import com.java.crm.common.domain.ReturnObject;
import com.java.crm.common.utils.DateUtils;
import com.java.crm.common.utils.UUIDUtils;
import com.java.crm.settings.domain.User;
import com.java.crm.settings.service.UserService;
import com.java.crm.workbench.domain.Activity;
import com.java.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author qqyqq
 * @create 2023-02-17 10:32
 */
@Controller
public class ActivityController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        List<User> users = userService.queryAllUsers();
        request.setAttribute("userList", users);
        //请求转发到市场活动的主页面
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    @ResponseBody
    public Object saveCreateActivity(Activity activity, HttpSession session){
        User user=(User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formateDateTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObject returnObject=new ReturnObject();

        try {
            //调用service层方法，保存创建的市场活动   查数据一般不考虑异常
            int ret = activityService.saveCreateActivity(activity);
            if(ret > 0 ){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙,请稍后重试....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙,请稍后重试....");
        }
        return returnObject;
    }
}
