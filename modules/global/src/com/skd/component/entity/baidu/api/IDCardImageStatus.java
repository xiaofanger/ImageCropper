package com.skd.component.entity.baidu.api;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum IDCardImageStatus implements EnumClass<String> {

    识别正常("normal"),
    未摆正身份证("reversed_side"),
    上传的图片中不包含身份证("non_idcard"),
    身份证模糊("blurred"),
    身份证关键字段反光或过曝("over_exposure"),
    未知状态("unknown");

    private String id;

    IDCardImageStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static IDCardImageStatus fromId(String id) {
        for (IDCardImageStatus at : IDCardImageStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}