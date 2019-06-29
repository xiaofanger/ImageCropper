package com.skd.component.core.baidu.api.service;

import com.baidu.aip.ocr.AipOcr;
import com.haulmont.cuba.core.global.Configuration;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.sys.events.AppContextStartedEvent;
import com.skd.component.core.baidu.api.service.baidu.api.OCRAPIConfig;
import com.skd.component.entity.baidu.api.*;
import org.json.JSONObject;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import com.skd.component.core.baidu.api.service.baidu.api.OCRService;

import javax.inject.Inject;
import java.util.HashMap;

/**
 * 百度OCR 服务
 * 参考
 * https://ai.baidu.com/docs#/OCR-Java-SDK/top
 */
@Service(OCRService.NAME)
public class OCRServiceBean implements OCRService {
    String APP_ID ;
    String API_KEY  ;
    String SECRET_KEY;

    @Inject
    private Configuration configuration;
    @Inject
    TimeSource timeSource;


    @Inject
    Metadata  metadata;

    private AipOcr client;

    @EventListener
    @Order(Events.LOWEST_PLATFORM_PRECEDENCE + 100)
    public void onAppStarted(AppContextStartedEvent event){
        init();
    }

    @Override
    public IDCard idCard(String imageBase64, String idCardSide){
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_risk", "false");

        byte[] image = getImageByteArray(imageBase64);
        JSONObject res = client.idcard(image, idCardSide, options);
        IDCard idCard=metadata.create(IDCard.class);
        int direction = res.getInt("direction");
        String image_status = res.getString("image_status");
        String idcard_type = res.getString("idcard_type");
        String edit_tool = res.getString("edit_tool");

        idCard.setDirection(IDCardImageDirection.fromId(direction));
        idCard.setImageStatus(IDCardImageStatus.fromId(image_status));
        idCard.setIdcardType(idcard_type);
        idCard.setEditTool(edit_tool);
        JSONObject words_result = res.getJSONObject("words_result");
        if(words_result!=null){
            JSONObject 住址=words_result.getJSONObject("住址");
            String address = 住址.getString("words");
            JSONObject 出生=words_result.getJSONObject("出生");
            String birthday = 出生.getString("words");

            JSONObject 姓名=words_result.getJSONObject("姓名");
            String name = 姓名.getString("words");

            JSONObject 性别=words_result.getJSONObject("性别");
            String sex = 性别.getString("words");

            JSONObject 民族=words_result.getJSONObject("民族");
            String nation = 民族.getString("words");

            JSONObject 公民身份号码=words_result.getJSONObject("公民身份号码");
            String cardNum = 民族.getString("words");


            idCard.setAddress(address);
            idCard.setBirthday(birthday);
            idCard.setCardNum(cardNum);
            idCard.setName(name);
            idCard.setSex(sex);
            idCard.setNation(nation);
        }
        return idCard;
    }
    byte[] getImageByteArray(String base64){
        byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(base64.getBytes());
        return decoded;
    }
    @Override
    public BankCard bankCard(String base64){
        HashMap<String, String> options = new HashMap<String, String>();
        // 参数为本地路径
        byte[] image = getImageByteArray(base64);
        JSONObject res = client.bankcard(image, options);
        JSONObject result = res.getJSONObject("result");
        String bank_card_number = result.getString("bank_card_number");
        String bank_name = result.getString("bank_name");
        Integer bank_card_type = result.getInt("bank_card_type");
        BankCard bankCard=metadata.create(BankCard.class);
        bankCard.setBankName(bank_name);
        bankCard.setCardNumber(bank_card_number);
        bankCard.setCardType(BankCardType.fromId(Integer.toString(bank_card_type)));
        return bankCard;
    }
    public void init () {
        OCRAPIConfig config = configuration.getConfig(OCRAPIConfig.class);
        // 初始化一个AipOcr
        client = new AipOcr(config.getOcrAppId(), config.getOcrAppKey(), config.getOcrSecretKey());

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        //        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        // System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

    }
}