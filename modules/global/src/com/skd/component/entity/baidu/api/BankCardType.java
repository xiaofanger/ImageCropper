package com.skd.component.entity.baidu.api;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum BankCardType implements EnumClass<String> {

    不能识别("0"),
    借记卡("1"),
    信用卡("2");

    private String id;

    BankCardType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static BankCardType fromId(String id) {
        for (BankCardType at : BankCardType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}