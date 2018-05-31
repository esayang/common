package com.sczy.common.dao.util;


import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;


import com.sczy.common.dao.DaoMaster;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*数据库升级工具类  ----迁移数据*/
public class MigrationHelper {

    private static final String CONVERSION_CLASS_NOT_FOUND_EXCEPTION = "MIGRATION HELPER - CLASS DOESN'T MATCH WITH THE CURRENT " +
            "PARAMETERS";
    private static MigrationHelper instance;

    public static MigrationHelper getInstance() {
        if (instance == null) {
            instance = new MigrationHelper();
        }
        return instance;
    }

    public void migrate(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        generateTempTables(db, daoClasses);
        DaoMaster.dropAllTables(db, true);
        DaoMaster.createAllTables(db, false);
        restoreData(db, daoClasses);
    }

    /*获取数据库所有表数据*/
    private void generateTempTables(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            if (getColumns(db, tableName).size() > 0 ) {
                String divider = "";
                String tempTableName = daoConfig.tablename.concat("_TEMP");
                ArrayList<String> properties = new ArrayList<>();
                StringBuilder createTableStringBuilder = new StringBuilder();
                createTableStringBuilder.append("CREATE TABLE ").append(tempTableName).append(" (");
                for (int j = 0; j < daoConfig.properties.length; j++) {
                    String columnName = daoConfig.properties[j].columnName;
                    if (getColumns(db, tableName).contains(columnName)) {
                        properties.add(columnName);
                        String type = null;
                        try {
                            type = getTypeByClass(daoConfig.properties[j].type);
                        } catch (Exception exception) {
                            System.out.print(exception.getMessage());
                        }

                        createTableStringBuilder.append(divider).append(columnName).append(" ").append(type);

                        if (daoConfig.properties[j].primaryKey) {
                            createTableStringBuilder.append(" PRIMARY KEY");
                        }
                        divider = ",";
                    }
                }
                createTableStringBuilder.append(");");
                try {
                    db.execSQL(createTableStringBuilder.toString());
                }catch (Exception e){
                }

                StringBuilder insertTableStringBuilder = new StringBuilder();
                insertTableStringBuilder.append("INSERT INTO ").append(tempTableName).append(" (");
                insertTableStringBuilder.append(TextUtils.join(",", properties));
                insertTableStringBuilder.append(") SELECT ");
                insertTableStringBuilder.append(TextUtils.join(",", properties));
                insertTableStringBuilder.append(" FROM ").append(tableName).append(";");
                try {
                    db.execSQL(insertTableStringBuilder.toString());
                }catch (Exception e){
                }
            }
        }
    }

    /*重新存储数据到新的数据库*/
    private void restoreData(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            if (getColumns(db, tempTableName).size() > 0){
                ArrayList<String> properties = new ArrayList();
                for (int j = 0; j < daoConfig.properties.length; j++) {
                    String columnName = daoConfig.properties[j].columnName;
                    if (getColumns(db, tempTableName).contains(columnName)) {
                        properties.add(columnName);
                    }
                }

                StringBuilder insertTableStringBuilder = new StringBuilder();
                insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
                insertTableStringBuilder.append(TextUtils.join(",", properties));
                insertTableStringBuilder.append(") SELECT ");
                insertTableStringBuilder.append(TextUtils.join(",", properties));
                insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");

                StringBuilder dropTableStringBuilder = new StringBuilder();

                dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);

                try {
                    db.execSQL(insertTableStringBuilder.toString());
                    db.execSQL(dropTableStringBuilder.toString());
                }catch (Exception e){
                }

            }
        }
    }

    private String getTypeByClass(Class<?> type) throws Exception {
        if (type.equals(String.class)) {
            return "TEXT";
        }
        if (type.equals(Long.class) || type.equals(Integer.class) || type.equals(long.class)) {
            return "INTEGER";
        }
        if (type.equals(Boolean.class)) {
            return "BOOLEAN";
        }

        Exception exception = new Exception(CONVERSION_CLASS_NOT_FOUND_EXCEPTION.concat(" - Class: ").concat(type.toString()));
        System.out.print(exception.getMessage());
        throw exception;
    }

    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);
            if (cursor != null) {
                columns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));
            }
        } catch (Exception e) {
            Log.v(tableName, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return columns;
    }
}



