package com.skd.component.web.screens.imgcrop;

import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.skd.component.web.toolkit.ui.imgcrop.ImgCropServerComponent;
import com.vaadin.ui.Layout;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by green on 2019/10/25.
 */

@UiController("skd_ImageCropWindow")
@UiDescriptor("ImageCropWindow.xml")
public class ImageCropWindow extends Screen {
    @Inject
    private VBoxLayout cropCmpCtn;
    @Inject
    private Image previewImage;
    @Inject
    private Button cancelBtn;
    private ImageCropWindowOptions options;
    @Inject
    private Label<String> fileSizeLabel;
    @Inject
    private LookupField qualityField;
    private ImgCropServerComponent imgCrop;
    @Inject
    FileUploadingAPI fileUploadingAPI;

    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if(options instanceof ImageCropWindowOptions){
            this.options= (ImageCropWindowOptions)options;
            List<Integer> list = new ArrayList<>();
            for (int i = 10; i >= 1; i--) {
                list.add(i);
            }
            qualityField.setOptionsList(list);
            qualityField.setValue(this.options.getCropQuality());
        }


    }
    @Inject
    private Button okBtn;
    @Inject
    private ButtonsPanel toolbar;
    @Inject
    private VBoxLayout rootCtn;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        this.imgCrop = new ImgCropServerComponent(
                this.options.getImageFile(),
                this.options.getViewPort(),
                this.options.getCropQuality()
                );
        cropCmpCtn.unwrap(Layout.class).addComponent(imgCrop);
        imgCrop.setImageUpdateListener(imageBase64 -> {
            if(imageBase64.contains(",")){
                imageBase64=imageBase64.split(",")[1];
            }
            byte[] bytes = Base64.decodeBase64(imageBase64);
            try {
                //TODO:可能会产生较多临时文件，需要完善
                File tmpFile = fileUploadingAPI.createFile().getFile();
                FileUtils.writeByteArrayToFile(tmpFile,bytes);
                previewImage.setSource(FileResource.class).setFile(tmpFile);
                fileSizeLabel.setValue(getDataSize(bytes.length));
            } catch (IOException | FileStorageException e) {
                throw  new RuntimeException(e);
            }

        });
    }



    public static String getDataSize(long size){
        DecimalFormat formater = new DecimalFormat("####.00");
        if(size<1024){
            return size+"bytes";
        }else if(size<1024*1024){
            float kbsize = size/1024f;
            return formater.format(kbsize)+"KB";
        }else if(size<1024*1024*1024){
            float mbsize = size/1024f/1024f;
            return formater.format(mbsize)+"MB";
        }else if(size<1024*1024*1024*1024){
            float gbsize = size/1024f/1024f/1024f;
            return formater.format(gbsize)+"GB";
        }else{
            return "size: error";
        }
    }


    @Subscribe("okBtn")
    public void onOkBtnClick(Button.ClickEvent event) {
        String base64 = this.imgCrop.getCurrentImageBase64();
        byte[] bytes = Base64.decodeBase64(base64);
        this.options.setResult(bytes);
        this.close(WINDOW_COMMIT_AND_CLOSE_ACTION);
    }
    @Subscribe("qualityField")
    public void onQualityFieldValueChange(HasValue.ValueChangeEvent event) {
        if(imgCrop!=null){
            imgCrop.setQuality(((Integer)event.getValue()).floatValue()/10f);
        }
    }

    @Subscribe("cancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        this.close(WINDOW_DISCARD_AND_CLOSE_ACTION);
    }
}