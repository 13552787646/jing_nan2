package com.jn.web.user.controller;

import com.jn.entity.Result;
import com.jn.entity.StatusCode;
import com.jn.pojo.User;
import com.jn.web.user.pojo.UserDo;
import com.jn.web.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Author yaxiongliu
 **/
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    //根据id查询用户信息
    @RequestMapping("/findByUsername/{username}")
    public Result<UserDo> findByUsername(@PathVariable("username") String username){
        UserDo user = userService.findByUsername(username);
        user.setUsername("IT雄哥哥 v1.1版本");
        Result<UserDo> result = new Result<>();
        result.setData(user);
        result.setCode(StatusCode.OK);
        result.setMessage("查询成功@@@@");
        return result;
    }

    //根据id查询用户信息
    @RequestMapping("/findUserById/{id}")
    public Result<UserDo> findUserById(@PathVariable("id") String id){
        UserDo user = userService.findUserById(id);
        Result<UserDo> result = new Result<>();
        result.setData(user);
        result.setCode(StatusCode.OK);
        result.setMessage("查询成功");
        return result;
    }
}
