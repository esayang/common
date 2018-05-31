package com.sczy.common.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class OfflineUser {
    @Id(autoincrement = true)
    private Long id;
    private String account;
    private String name;
    private String userCode;
    private String password;
    private String city;

    @Generated(hash = 412767979)
    public OfflineUser(Long id, String account, String name, String userCode,
            String password, String city) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.userCode = userCode;
        this.password = password;
        this.city = city;
    }

    @Generated(hash = 421925781)
    public OfflineUser() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "OfflineUser{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", userCode='" + userCode + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}