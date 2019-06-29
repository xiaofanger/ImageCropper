package com.skd.component.core.baidu.api.service.baidu.api;


import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;

public interface OCRAPIConfig extends Config {
    @Property("skd.component.ocr.appId")
    @Source(type = SourceType.DATABASE)
    String getOcrAppId();
    void setOcrAppId(String appid);

    @Property("skd.component.ocr.appKey")
    @Source(type = SourceType.DATABASE)
    String getOcrAppKey();
    void setOcrAppKey(String appKey);

    @Property("skd.component.ocr.secretKey")
    @Source(type = SourceType.DATABASE)
    String getOcrSecretKey();
    void setOcrSecretKey(String secretKey);

}
