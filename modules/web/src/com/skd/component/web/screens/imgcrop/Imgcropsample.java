package com.skd.component.web.screens.imgcrop;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.skd.component.web.toolkit.ui.imgcrop.ImgCropServerComponent;
import com.vaadin.ui.Layout;

import javax.inject.Inject;
import java.io.File;
import java.util.UUID;


/**
 * Created by green on 2019/10/21.
 */

@UiController("skd_Imgcropsample")
@UiDescriptor("ImgCropSample.xml")
public class Imgcropsample extends Screen {
    @Inject
    private VBoxLayout imgcropCtn;

    @Inject
    private Image image;
    @Inject
    private FileUploadField uploadField;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private DataManager dataManager;
    @Inject
    Screens screens;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Notifications notifications;
    @Inject
    private Dialogs dialogs;
    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
//        ImgCropServerComponent imgcrop = new ImgCropServerComponent();
//        imgcrop.setWidth("250px");
//        imgcrop.setHeight("250px");
//        imgcrop.setImageUpdateListener(newValue -> {
//            System.out.println("test");
//        });
//        imgcropCtn.unwrap(Layout.class).addComponent(imgcrop);
    }

    @Subscribe("cropBtn")
    public void onCropBtnClick(Button.ClickEvent event) {
        UUID fileId = uploadField.getFileId();
        File file = fileUploadingAPI.getFile(fileId);
        if(file==null){
            return;
        }
        ImageCropWindowOptions options=new ImageCropWindowOptions(file);
        Screen screen = screenBuilders.screen(this)
                .withScreenClass(ImageCropWindow.class)
                .withLaunchMode(OpenMode.DIALOG)
                .withOptions(options)
                .withAfterCloseListener(e -> {
                    notifications.create().withCaption("Closed").show();
                })
                .build()
                .show();
    }

    @Subscribe("uploadField")
    public void onUploadFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        File file = fileUploadingAPI.getFile(uploadField.getFileId());
        if (file != null) {
            notifications.create()
                    .withCaption("提示")
                    .withDescription("文件已经上传至临时存储，点击[剪裁]按钮对图片进行剪裁" + file.getAbsolutePath())
                    .show();
            image.setSource(FileResource.class).setFile(file);
        }
    }
}