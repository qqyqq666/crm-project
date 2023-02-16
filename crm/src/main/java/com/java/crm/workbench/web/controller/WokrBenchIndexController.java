package com.java.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qqyqq
 * @create 2023-02-15 21:59
 */
@Controller
public class WokrBenchIndexController {


    @RequestMapping("/workbench/index.do")
    public String index(){
        //跳转到主业务页面
        return "workbench/index";
    }
}
