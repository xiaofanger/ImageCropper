package com.skd.component.core.baidu.api.service;

import com.skd.component.core.baidu.api.OCR;
import com.skd.component.core.baidu.api.service.baidu.api.OCRService;
import com.skd.component.entity.baidu.api.BankCard;
import com.skd.component.entity.baidu.api.IDCard;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * 百度OCR
 * 参考
 *
 */
@Service(OCRService.NAME)
public class OCRServiceBean implements OCRService {

    @Inject
    private OCR ocr;
    @Override
    public IDCard idCard(String imageBase64, String idCardSide) {
        return ocr.idCard(imageBase64, idCardSide);
    }

    @Override
    public BankCard bankCard(String base64) {
        return ocr.bankCard(base64);
    }
}