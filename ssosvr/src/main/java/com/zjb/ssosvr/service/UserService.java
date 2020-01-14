package com.zjb.ssosvr.service;

import com.zjb.ssosvr.model.SysUser;
import com.zjb.ssosvr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by zjb on 2020/1/14.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public SysUser getByUsername(String userName) {
        return userRepository.getByUsername(userName);
    }

    public void addUser(SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }
}
