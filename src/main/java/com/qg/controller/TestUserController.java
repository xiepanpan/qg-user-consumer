package com.qg.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qg.pojo.QgUser;
import com.qg.service.QgUserService;
import com.qg.utils.KafkaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiepanpan
 * @Date: 2019/9/21
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestUserController {

    @Reference
    private QgUserService qgUserService;
    @Autowired
    private KafkaUtil kafkaUtil;

    /**
     * 根据id查询用户信息
     * @return
     */
    @RequestMapping("/queryUserById")
    public QgUser queryUserById() {
        QgUser qgUser = null;
        try {
            kafkaUtil.sendInfoMessage("this is queryUserById function!");
            qgUser = qgUserService.getQgUserById("1");
        } catch (Exception e) {
            kafkaUtil.sendErrorMessage(e);
            e.printStackTrace();
        }
        return qgUser;
    }
}
