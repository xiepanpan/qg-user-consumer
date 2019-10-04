package com.qg.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qg.dto.ReturnResult;
import com.qg.dto.ReturnResultUtils;
import com.qg.exception.CommonException;
import com.qg.service.LocalUserService;
import com.qg.service.QgUserService;
import com.qg.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

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


    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult doLogin(String phone, String password, HttpServletResponse response) throws Exception {
        //调用接口 实现用户名和密码的验证
        return localUserService.validateToken(phone,password);
    }

    @RequestMapping(value = "/v/loginOut",method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult loginOut(String token) throws Exception {
        //调用接口 实现用户名和密码的验证
        return localUserService.removeToken(token);
    }
}
