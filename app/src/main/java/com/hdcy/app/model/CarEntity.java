package com.hdcy.app.model;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Created by WeiYanGeorge on 2016-11-03.
 */

public class CarEntity implements IndexableEntity {
    private long id;
    private String name;
    private String pinyin;

    public CarEntity() {
    }

    public CarEntity(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String getFieldIndexBy() {
        return name;
    }

    @Override
    public void setFieldIndexBy(String indexByField) {
        this.name = indexByField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
        this.pinyin = pinyin;
    }
}
