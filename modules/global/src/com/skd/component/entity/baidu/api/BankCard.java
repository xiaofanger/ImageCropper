package com.skd.component.entity.baidu.api;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

@MetaClass(name = "skd_BankCard")
public class BankCard extends BaseUuidEntity {
    private static final long serialVersionUID = 2843275809067775784L;

    @MetaProperty
    protected String cardNumber;

    @MetaProperty
    protected String bankName;

    @MetaProperty
    protected String cardType;

    public BankCardType getCardType() { return cardType == null ? null : BankCardType.fromId(cardType); }

    public void setCardType(BankCardType cardType) { this.cardType = cardType == null ? null : cardType.getId(); }

    public String getBankName() { return bankName; }

    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getCardNumber() { return cardNumber; }

    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
}