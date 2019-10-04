package com.qg.service;

import com.qg.dto.ReturnResult;

/**
 * @author: xiepanpan
 * @Date: 2019/10/3
 * @Description:
 */
public interface LocalUserService {

    public ReturnResult validateToken(String phone, String password) throws Exception;

    /**
     * 注销功能
     * @param token
     * @return
     * @throws Exception
     */
    public ReturnResult removeToken(String token) throws Exception;
}
