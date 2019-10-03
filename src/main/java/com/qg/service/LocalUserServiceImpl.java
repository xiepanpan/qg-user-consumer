package com.qg.service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.qg.pojo.QgUser;
import com.qg.utils.EmptyUtils;
import com.qg.utils.RedisUtil;
import com.qg.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: xiepanpan
 * @Date: 2019/10/3
 * @Description:
 */
@Component
public class LocalUserServiceImpl implements LocalUserService {

    @Reference
    private QgUserService qgUserService;
    @Autowired
    private RedisUtil redisUtil;

    public String validateToken(String phone,String password) throws Exception {
        //调用接口 实现用户名和密码的验证
        QgUser qgUser = null;
        String token = null;
        try {
            qgUser = qgUserService.queryQgUserByPhoneAndPwd(phone, password);
            if (qgUser!=null) {
                //验证成功 生成token放到Redis中
                String oldToken = redisUtil.getStr(qgUser.getId());
                if (EmptyUtils.isNotEmpty(oldToken)) {
                    redisUtil.del(oldToken);
                    redisUtil.del(qgUser.getId());
                }
                token= TokenUtils.creatToken(qgUser.getId(),qgUser.getPhone());
                redisUtil.setStr("token", JSON.json(qgUser));
                redisUtil.setStr(qgUser.getId(),token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return token;
        }
    }
}
