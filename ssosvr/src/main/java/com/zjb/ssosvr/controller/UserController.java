package com.zjb.ssosvr.controller;

import com.zjb.ssosvr.model.SysUser;
import com.zjb.ssosvr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zjb on 2020/1/14.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public void add(SysUser sysUser) {
        userService.addUser(sysUser);
    }
}
