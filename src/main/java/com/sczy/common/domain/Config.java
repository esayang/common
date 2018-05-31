package com.sczy.common.domain;

import io.realm.RealmObject;

/**
 * @author SC16004984
 * @date 2018/4/10.
 * @describe   Realm  远程配置
 */

public class Config extends RealmObject {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
