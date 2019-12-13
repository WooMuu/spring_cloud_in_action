package com.zjb.organization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "organization")
public class Organization {
    @Id
    @Column(name = "organization_id", nullable = false)
    String id;

    @Column(name = "organization_name", nullable = false)
    String name;

    @Column(name = "contactName", nullable = true)
    String contactName;

    @Column(name = "contactEmail", nullable = true)
    String contactEmail;

    @Column(name = "contactPhone", nullable = true)
    String contactPhone;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }


}
