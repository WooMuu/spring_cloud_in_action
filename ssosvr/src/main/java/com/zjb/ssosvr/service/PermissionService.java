package com.zjb.ssosvr.service;

import com.zjb.ssosvr.model.SysPermission;
import com.zjb.ssosvr.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zjb on 2020/1/14.
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public List<SysPermission> findByUserName(String username) {
        return permissionRepository.findByUserName(username);
    }
}
