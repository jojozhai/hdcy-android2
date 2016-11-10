package com.hdcy.base.utils;

import com.hdcy.app.dbmodel.BaseDataEntity;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.Date;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-11-07.
 */

public class DBHelper {

    public final static String KEY_PP_TOKEN ="pp_token";
    public final static String KEY_IS_FIRST_START = "is_first_start";// 是否第一次启动
    public final static String KEY_USER_ID = "user_id";
    public final static String KEY_USER_NAME = "user_name";
    public final static String KEY_USER_PASSWORD = "user_password";
    public final static String KEY_USER_NICKNAME = "user_nickname";
    public final static String KEY_USER_REALNAME = "user_realname";
    public final static String KEY_USER_MOBILE = "user_mobile";
    public final static String KEY_USER_SEX = "user_sex";
    public final static String KEY_USER_WEIXIN = "user_wenxin";
    public final static String KEY_USER_WEXIN_OPEN_ID = "user_weixin_open_id";
    public final static String KEY_USER_WEIXIN_UNION_ID = "user_weixin_union_id";
    public final static String KEY_USER_CITY = "user_city";
    public final static String KEY_USER_COUNTRY = "user_country";
    public final static String KEY_USER_PROVINCE = "user_province";
    public final static String KEY_USER_ADDRESS = "user_address";
    public final static String KEY_USER_HEADIMGURL = "user_headimgurl";
    public final static String KEY_USER_POINT = "user_point";
    public final static String KEY_USER_BEANS = "user_beans";
    public final static String KEY_USER_UNREAD_MESSAGES = "user_unreadMessages";
    public final static String KEY_USER_LEVEL = "user_level";
    public final static String KEY_USER_TAGS = "user_tags";
    public final static String KEY_USER_birthday = "user_birthday";
    public final static String KEY_USER_VIPEXPIRED = "user_vipexpired";
    public final static String KEY_USER_VIPVALID = "user_vipvalid";
    public final static String KEY_USER_VIP = "user_valid";
    public final static String KEY_USER_CAR = "user_car";
    public final static String KEY_USER_MONEY = "user_money";
    public final static String KEY_USER_MONEYPLUS = "user_moneyplus";
    public final static String KEY_USER_JOB = "user_job";
    public final static String KEY_USER_PARTICIPATIONCOUNT = "user_participationcount";

    private final static DbManager.DaoConfig dbConfig = new DbManager.DaoConfig()
            .setDbName("HDCY.db")
            .setDbVersion(2)
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    // 开启WAL, 对写入加速提升巨大
                    db.getDatabase().enableWriteAheadLogging();
                }
            })
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    // db.addColumn(...);
                    // db.dropTable(...);
                    // ...
                    // or
                    // db.dropDb();
                }
            });

    private static class DbManagerHolder {
        private static final DbManager instance = x.getDb(dbConfig);
    }

    public static DbManager getDbInstance() {
        return DbManagerHolder.instance;
    }

    /**
     * 获取数据库数据集合
     *
     * @return
     */
    public static List<BaseDataEntity> getBaseDataEntityList() {
        DbManager dbManager = getDbInstance();
        try {
            return dbManager.findAll(BaseDataEntity.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 清除数据库空数据
     */
    public static boolean clearNullData() {
        DbManager dbManager = getDbInstance();
        List<BaseDataEntity> baseDataEntityList = getBaseDataEntityList();
        if (!BaseUtils.isEmptyList(baseDataEntityList)) {
            for (BaseDataEntity baseDataEntity : baseDataEntityList) {
                if (!BaseUtils.isEmptyString(baseDataEntity.getStringData())) {
                    continue;
                }
                if (baseDataEntity.getIntData() != 0) {
                    continue;
                }
                if (baseDataEntity.isBooleanData()) {
                    continue;
                }
                baseDataEntity.deleteData(dbManager);
            }
            return true;
        } else {
            return false;
        }
    }

    public static BaseDataEntity getBaseDataEntity(String key) {
        DbManager dbManager = getDbInstance();
        try {
            return dbManager.selector(BaseDataEntity.class).where("key", "=", key).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean putDateDate(String key, Date value){
        DbManager dbManager = getDbInstance();
        BaseDataEntity baseDataEntity = getBaseDataEntity(key);
        if(baseDataEntity != null){
            baseDataEntity.setDateData(value);
            return baseDataEntity.updateDateData(dbManager);
        }else {
            baseDataEntity = new BaseDataEntity();
            baseDataEntity.setKey(key);
            baseDataEntity.setDateData(value);
            return baseDataEntity.saveBindingId(dbManager);
        }
    }

    public static Date getDateData(String key, Date defValue){
        BaseDataEntity baseDataEntity = getBaseDataEntity(key);
        Date dateData = baseDataEntity ==null ? null :baseDataEntity.getDateData();
        return BaseUtils.isEmptyString(dateData.toString()) ? defValue : dateData;
    }

    public static boolean putStringData(String key, String value) {
        DbManager dbManager = getDbInstance();
        BaseDataEntity baseDataEntity = getBaseDataEntity(key);
        if (baseDataEntity != null) {
            baseDataEntity.putStringData(value);
            return baseDataEntity.updateStringData(dbManager);
        } else {
            baseDataEntity = new BaseDataEntity();
            baseDataEntity.setKey(key);
            baseDataEntity.putStringData(value);
            return baseDataEntity.saveBindingId(dbManager);
        }
    }

    public static String getStringData(String key, String defValue) {
        BaseDataEntity baseDataEntity = getBaseDataEntity(key);
        String stringData = baseDataEntity == null ? null : baseDataEntity.getStringData();
        return BaseUtils.isEmptyString(stringData) ? defValue : stringData;
    }

    public static boolean putBooleanData(String key, boolean value) {
        DbManager dbManager = getDbInstance();
        BaseDataEntity baseDataEntity = getBaseDataEntity(key);
        if (baseDataEntity != null) {
            baseDataEntity.putBooleanData(value);
            return baseDataEntity.updateBooleanData(dbManager);
        } else {
            baseDataEntity = new BaseDataEntity();
            baseDataEntity.setKey(key);
            baseDataEntity.putBooleanData(value);
            return baseDataEntity.saveBindingId(dbManager);
        }
    }

    public static boolean getBooleanData(String key, boolean defValue) {
        BaseDataEntity baseDataEntity = getBaseDataEntity(key);
        return baseDataEntity == null ? defValue : baseDataEntity.isBooleanData();
    }

    public static boolean putIntData(String key, int value) {
        DbManager dbManager = getDbInstance();
        BaseDataEntity baseDataEntity = getBaseDataEntity(key);
        if (baseDataEntity != null) {
            baseDataEntity.putIntData(value);
            return baseDataEntity.updateIntData(dbManager);
        } else {
            baseDataEntity = new BaseDataEntity();
            baseDataEntity.setKey(key);
            baseDataEntity.putIntData(value);
            return baseDataEntity.saveBindingId(dbManager);
        }
    }

    public static int getIntData(String key, int defValue) {
        BaseDataEntity baseDataEntity = getBaseDataEntity(key);
        return baseDataEntity == null ? defValue : baseDataEntity.getIntData();
    }

}
