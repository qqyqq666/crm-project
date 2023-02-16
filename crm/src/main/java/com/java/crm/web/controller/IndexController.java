package com.java.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qqyqq
 * @create 2023-02-15 17:21
 */
@Controller
public class IndexController {
    /*
        理论上，给Controller方法分配url：http://127.0.0.1:8080/crm/
        为了简便，协议://ip:port/应用名称必须省去，用/代表应用根目录下的/
     */
    @RequestMapping("/")
    public String index(){
        //请求转发
        return "index";
    }
}
