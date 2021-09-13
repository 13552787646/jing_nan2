package com.jn.web.user.service;


import com.jn.pojo.User;
import com.jn.web.user.pojo.UserDo;

public interface UserService {
    /**
     * 根据用户名查询用户信息
     */
    UserDo findByUsername(String username);

    UserDo findUserById(String id);
}
