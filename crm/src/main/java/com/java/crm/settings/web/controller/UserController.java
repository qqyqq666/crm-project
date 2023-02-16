package com.java.crm.settings.web.controller;

import com.java.crm.common.contants.Contants;
import com.java.crm.common.domain.ReturnObject;
import com.java.crm.common.utils.DateUtils;
import com.java.crm.settings.domain.User;
import com.java.crm.settings.service.UserService;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qqyqq
 * @create 2023-02-15 19:14
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * url要和controller方法处理完请求之后，响应信息返回的页面的资源目录保持一致
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        //请求转发到登录页面
        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd,
                        HttpServletRequest request, HttpServletResponse response, HttpSession session){
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd", loginPwd);
        //调用service
        User user = userService.queryUserByLoginActAndPwd(map);
        //根据查询结果生成相应信息
        ReturnObject returnObject = new ReturnObject();
        if(user == null){
            //登录失败,用户名或者密码错误
            returnObject.setCode("0");
            returnObject.setMessage("用户名或者密码错误");

        }else{//进一步判断账号是否合法
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String nowStr=sdf.format(new Date());  //封装为DateUtils
            if(DateUtils.formateDateTime(new Date()).compareTo(user.getExpireTime())>0){
                //登录失败，账号已过期
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号已过期");
            }else if("0".equals(user.getLockState())){
                //登录失败，状态被锁定
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("状态被锁定");
            }else if(!user.getAllowIps().contains(request.getRemoteAddr())){
                //登录失败，ip受限
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("ip受限");
            }else{
                //登录成功
                returnObject.setCode("1");
                //把user保存到session中
                session.setAttribute(Contants.SESSION_USER,user);
                //如果需要记住密码，则往外写cookie
                if("true".equals(isRemPwd)){
                    Cookie c1 = new Cookie("loginAct", user.getLoginAct());
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd",user.getLoginPwd());
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }else{
                    //把没有过期cookie删除
                    Cookie c1=new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }

        }
        return returnObject;
    }
    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //清空Cookie
        Cookie c1 = new Cookie("loginAct", "1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2 = new Cookie("loginPwd", "1");
        c2.setMaxAge(0);
        response.addCookie(c2);
        //销毁session
        session.invalidate();
        //跳转到首页
        return "redirect:/";  //重定向  地址栏会改变
        //return "/";  请求转发的方式

    }
}
