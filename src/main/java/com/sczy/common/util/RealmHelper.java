package com.sczy.common.util;

import android.content.Context;

import com.sczy.common.domain.Account;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * @author SC16004984
 * @date 2018/4/2.
 */

public final class RealmHelper {

    private static final RealmConfiguration getDBRealmConfig(){
        return new RealmConfiguration.Builder()
                .name("db.realm")
                .schemaVersion(1)
                .build();
    }
    public static RealmConfiguration getCacheRealmConfig(){
        return new RealmConfiguration.Builder()
                .name("cache.realm")
                .schemaVersion(1)
                .build();
    }

    public static void init(Context context){
        Realm.init(context);
        Realm.setDefaultConfiguration(getDBRealmConfig());
    }
}
