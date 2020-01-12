package com.zjb.ssosvr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by zjb on 2020/1/12.
 */
@Entity
@Table(name = "user_orgs")
public class UserOrganization implements Serializable {
    @Id
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "organization_id", nullable = false)
    private String organizationId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
}
