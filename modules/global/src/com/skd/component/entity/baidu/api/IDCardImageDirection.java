package com.skd.component.entity.baidu.api;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum IDCardImageDirection implements EnumClass<Integer> {

    正向(0),逆时针90度(1),逆时针180度(2),逆时针270度(3),未定义(-1);

    private Integer id;

    IDCardImageDirection(Integer value) {
        this.id = value;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Nullable
    public static IDCardImageDirection fromId(Integer id) {
        for (IDCardImageDirection at : IDCardImageDirection.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}