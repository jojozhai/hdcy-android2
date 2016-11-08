package com.hdcy.app.dbmodel;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.Date;

/**
 * 基础数据实体类(数据库用)
 * Created by lishilin on 2016/6/15.
 */
@Table(name = "BaseDataEntity")
public class BaseDataEntity {

    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "key")
    private String key;

    @Column(name = "stringData")
    private String stringData;

    @Column(name = "intData")
    private int intData;

    @Column(name = "booleanData")
    private boolean booleanData;

    @Column(name = "dateData")
    private Date dateData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStringData() {
        return stringData;
    }

    public void putStringData(String stringData) {
        this.stringData = stringData;
    }

    public int getIntData() {
        return intData;
    }

    public void putIntData(int intData) {
        this.intData = intData;
    }

    public boolean isBooleanData() {
        return booleanData;
    }

    public void putBooleanData(boolean booleanData) {
        this.booleanData = booleanData;
    }

    public Date getDateData() {
        return dateData;
    }

    public void setDateData(Date dateData) {
        this.dateData = dateData;
    }

    /**
     * 保存数据
     *
     * @param dbManager 数据库管理
     * @return 是否保存成功
     */
    public boolean saveBindingId(DbManager dbManager) {
        try {
            dbManager.saveBindingId(this);
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除数据
     *
     * @param dbManager 数据库管理
     * @return 是否保存成功
     */
    public boolean deleteData(DbManager dbManager) {
        try {
            dbManager.delete(this);
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 修改DateData
     *
     *
     */

    public boolean updateDateData(DbManager dbManager){
        try{
            dbManager.update(this,"dateData");
        }catch (DbException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 修改StringData
     *
     * @param dbManager 数据库管理
     * @return 是否修改StringData成功
     */
    public boolean updateStringData(DbManager dbManager) {
        try {
            dbManager.update(this, "stringData");
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 修改IntData
     *
     * @param dbManager 数据库管理
     * @return 是否修改IntData成功
     */
    public boolean updateIntData(DbManager dbManager) {
        try {
            dbManager.update(this, "intData");
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 修改BooleanData
     *
     * @param dbManager 数据库管理
     * @return 是否修改BooleanData成功
     */
    public boolean updateBooleanData(DbManager dbManager) {
        try {
            dbManager.update(this, "booleanData");
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BaseDataEntity{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", stringData='" + stringData + '\'' +
                ", intData=" + intData +
                ", booleanData=" + booleanData +
                ", dateData=" + dateData +
                '}';
    }
}
