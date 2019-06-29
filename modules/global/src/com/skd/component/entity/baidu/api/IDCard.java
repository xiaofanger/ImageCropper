package com.skd.component.entity.baidu.api;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

@MetaClass(name = "skd_IDCard")
public class IDCard extends BaseUuidEntity {
    private static final long serialVersionUID = 189503693299277759L;

    @MetaProperty
    protected String address;

    @MetaProperty
    protected String cardNum;

    @MetaProperty
    protected String birthday;

    @MetaProperty
    protected String name;

    @MetaProperty
    protected String sex;

    @MetaProperty
    protected String nation;

    @MetaProperty
    protected String idcardType;

    @MetaProperty
    protected String imageStatus;

    @MetaProperty
    protected String riskType;

    @MetaProperty
    protected Integer direction;

    @MetaProperty
    protected String editTool;

    public void setImageStatus(IDCardImageStatus imageStatus) { this.imageStatus = imageStatus == null ? null : imageStatus.getId(); }

    public IDCardImageStatus getImageStatus() { return imageStatus == null ? null : IDCardImageStatus.fromId(imageStatus); }

    public void setRiskType(IDCardRiskType riskType) { this.riskType = riskType == null ? null : riskType.getId(); }

    public IDCardRiskType getRiskType() { return riskType == null ? null : IDCardRiskType.fromId(riskType); }

    public void setDirection(IDCardImageDirection direction) { this.direction = direction == null ? null : direction.getId(); }

    public IDCardImageDirection getDirection() { return direction == null ? null : IDCardImageDirection.fromId(direction); }


    public String getEditTool() { return editTool; }

    public void setEditTool(String editTool) { this.editTool = editTool; }

    public String getIdcardType() { return idcardType; }

    public void setIdcardType(String idcardType) { this.idcardType = idcardType; }

    public String getNation() { return nation; }

    public void setNation(String nation) { this.nation = nation; }

    public String getSex() { return sex; }

    public void setSex(String sex) { this.sex = sex; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBirthday() { return birthday; }

    public void setBirthday(String birthday) { this.birthday = birthday; }

    public String getCardNum() { return cardNum; }

    public void setCardNum(String cardNum) { this.cardNum = cardNum; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }
}