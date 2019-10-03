package com.qg.service;

/**
 * @author: xiepanpan
 * @Date: 2019/10/3
 * @Description:
 */
public interface LocalUserService {

    public String validateToken(String phone,String password) throws Exception;
}
