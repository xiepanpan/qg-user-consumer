package com.qg.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.qg.common.Constants;
import com.qg.dto.ReturnResult;
import com.qg.dto.ReturnResultUtils;
import com.qg.exception.UserException;
import com.qg.pojo.QgUser;
import com.qg.utils.EmptyUtils;
import com.qg.utils.RedisUtil;
import com.qg.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public ReturnResult validateToken(String phone, String password) throws Exception {
        //调用接口 实现用户名和密码的验证
        QgUser qgUser = null;
        String token = null;
        ReturnResult returnResult=null;
        qgUser = qgUserService.queryQgUserByPhoneAndPwd(phone, password);
        if (qgUser!=null) {
            //验证成功 生成token放到Redis中
            String oldToken = redisUtil.getStr(qgUser.getId());
            if (EmptyUtils.isNotEmpty(oldToken)) {
                redisUtil.del(oldToken);
                redisUtil.del(qgUser.getId());
            }
            token= Constants.tokenPrefix+TokenUtils.creatToken(qgUser.getId(),qgUser.getPhone());
            redisUtil.setStr(token, JSONObject.toJSONString(qgUser),Constants.loginExpire);
            redisUtil.setStr(qgUser.getId(),token,Constants.loginExpire);
            Map<String,Object> result = new HashMap<>();
            result.put("token",token);
            returnResult = ReturnResultUtils.returnSuccess(result);
        }else {
            returnResult = ReturnResultUtils.returnFail(UserException.USER_PASSWORD_ERROR.getCode(),UserException.USER_PASSWORD_ERROR.getMessage());
        }
        return returnResult;
    }
}
