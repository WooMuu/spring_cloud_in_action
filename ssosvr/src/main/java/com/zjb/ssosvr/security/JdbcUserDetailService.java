package com.zjb.ssosvr.security;

import com.alibaba.fastjson.JSON;
import com.zjb.ssosvr.model.SysPermission;
import com.zjb.ssosvr.model.SysUser;
import com.zjb.ssosvr.service.PermissionService;
import com.zjb.ssosvr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjb on 2020/1/14.
 */
public class JdbcUserDetailService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

//    @Autowired
//    Logger logger = LoggerFactory.getLogger(JdbcUserDetailService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) throw new UsernameNotFoundException("用户名或密码错误");
        SysUser sysUser = userService.getByUsername(username);
        List<SysPermission> permissionList = permissionService.findByUserName(sysUser.getUsername());

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(permissionList)) {
            for (SysPermission sysPermission : permissionList) {
                authorityList.add(new SimpleGrantedAuthority(sysPermission.getCode()));
            }
        }
        MyUser myUser = new MyUser(sysUser.getUsername(), sysUser.getPassword(), authorityList);
        myUser.setPermissions(permissionList);

        System.out.println("登录成功！用户: {}" + JSON.toJSONString(myUser));
        return myUser;
    }
}
