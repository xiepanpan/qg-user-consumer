package com.qg.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.qg.pojo.QgUser;
import com.qg.service.LocalUserService;
import com.qg.service.QgUserService;
import com.qg.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;

/**
 * @author: xiepanpan
 * @Date: 2019/10/3
 * @Description: 用户控制层
 */
@Controller
@RequestMapping("/api")
public class UserController {

    @Reference
    private QgUserService qgUserService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LocalUserService localUserService;


    @RequestMapping("/doLogin")
    @ResponseBody
    public String doLogin(String phone,String password) {
        //调用接口 实现用户名和密码的验证
        try {
            return localUserService.validateToken(phone,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }


}