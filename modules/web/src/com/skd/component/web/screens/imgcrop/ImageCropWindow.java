package com.skd.component.web.screens.imgcrop;

import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.skd.component.web.toolkit.ui.imgcrop.ImgCropServerComponent;
import com.vaadin.ui.Layout;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
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
        this.imgCrop = new ImgCropServerComponent(this.options.getImageFile());
        cropCmpCtn.unwrap(Layout.class).addComponent(imgCrop);
        imgCrop.setImageUpdateListener(new ImgCropServerComponent.ImageUpdateListener() {
            @Override
            public void imageUpdate(String imageBase64) {
                byte[] bytes = Base64.decodeBase64(imageBase64);
                try {
                    File f=fileUploadingAPI.createFile().getFile();
                    FileUtils.writeByteArrayToFile(f,bytes);
                    previewImage.setSource(FileResource.class).setFile(f);
                } catch (IOException e) {
                    throw  new RuntimeException(e);
                } catch (FileStorageException e) {
                    throw  new RuntimeException(e);
                }

            }
        });
    }

    @Subscribe("okBtn")
    public void onOkBtnClick(Button.ClickEvent event) {

    }
    @Subscribe("qualityField")
    public void onQualityFieldValueChange(HasValue.ValueChangeEvent event) {
        imgCrop.setQuality(((Integer)event.getValue())/10);
    }
}