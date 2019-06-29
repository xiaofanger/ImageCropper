package com.skd.component.core.baidu.api.service.baidu.api;

import com.skd.component.entity.baidu.api.BankCard;
import com.skd.component.entity.baidu.api.IDCard;

public interface OCRService {
    String NAME = "skd_OCRService";
    String  idCardSide_FRONT="front";
    String  idCardSide_BACK="back";

    IDCard idCard(String imageBase64, String idCardSide);

    BankCard bankCard(String base64);
}