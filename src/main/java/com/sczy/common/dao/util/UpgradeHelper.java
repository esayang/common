package com.sczy.common.dao.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sczy.common.dao.ContactDepartmentDao;
import com.sczy.common.dao.DaoMaster;
import com.sczy.common.dao.OfflineUserDao;
import com.sczy.common.dao.UserDao;
import com.sczy.common.dao.UserDetailDao;

import org.greenrobot.greendao.database.Database;


public class UpgradeHelper extends DaoMaster.DevOpenHelper {

    public static String TAG = UpgradeHelper.class.getSimpleName();

    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override

    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            MigrationHelper.getInstance().migrate(db,
                    UserDao.class,
                    UserDetailDao.class, OfflineUserDao.class,
                    ContactDepartmentDao.class
            );
        }
    }
}
