package com.sczy.common.domain;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * @author SC16004984
 * @date 2018/4/2.
 */

public class Account extends RealmObject {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
