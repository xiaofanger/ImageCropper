package com.skd.component.entity.baidu.api;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum IDCardRiskType implements EnumClass<String> {

    正常身份证("normal"),
    复印件("copy"),
    临时身份证("temporary"),
    翻拍("screen"),
    未知("unknow");

    private String id;

    IDCardRiskType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static IDCardRiskType fromId(String id) {
        for (IDCardRiskType at : IDCardRiskType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}