package com.sczy.common.dao.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sczy.common.api.Constant;
import com.sczy.common.dao.DaoMaster;
import com.sczy.common.dao.DaoSession;


public class DaoManager {

    private static DaoSession mDaoSession;

    public static synchronized DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static synchronized void init(Context context) {
        initDaoSession(context);
    }

    private static void initDaoSession(Context context) {
        // 相当于得到数据库帮助对象，用于便捷获取db
        // 这里会自动执行upgrade的逻辑.backup all table→del all table→create all new table→restore data
        UpgradeHelper helper = new UpgradeHelper(context, Constant.DB_NAME, null);
        // 得到可写的数据库操作对象
        SQLiteDatabase db = helper.getWritableDatabase();
        // 获得Master实例,相当于给database包装工具
        db.getPath();
        DaoMaster daoMaster = new DaoMaster(db);
        // 获取类似于缓存管理器,提供各表的DAO类
        mDaoSession = daoMaster.newSession();
    }
}
