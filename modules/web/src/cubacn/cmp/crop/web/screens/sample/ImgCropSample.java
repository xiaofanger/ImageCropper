package cubacn.cmp.crop.web.screens.sample;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import cubacn.cmp.crop.web.screens.imgcrop.ImageCropWindow;
import cubacn.cmp.crop.web.screens.imgcrop.ImageCropWindowOptions;
import cubacn.cmp.crop.web.toolkit.ui.imgcrop.ImgCropServerComponent;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.UUID;


/**
 * Created by Ray.Lv on 2019/10/21.
 */

@UiController("cubacn_ImgCropSample")
@UiDescriptor("ImgCropSample.xml")
public class ImgCropSample extends Screen {

    @Inject
    private Image image;
    @Inject
    private FileUploadField uploadField;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private Notifications notifications;
    @Inject
    private Button cropBtn;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        cropBtn.setEnabled(false);
    }

    @Subscribe("cropBtn")
    public void onCropBtnClick(Button.ClickEvent event) {
        UUID fileId = uploadField.getFileId();
        File file = fileUploadingAPI.getFile(fileId);
        if(file==null){
            return;
        }
        // Create an viewport configuration object
        ImgCropServerComponent.ViewPort viewPort =
                new ImgCropServerComponent.ViewPort(200, 100,
                ImgCropServerComponent.ViewPortType.square);
        // Create an option object
        ImageCropWindowOptions options = new ImageCropWindowOptions(file, 10, viewPort);
        // Open a winow for cropping an image
        ImageCropWindow.showAsDialog(this, options, (cropWindowAfterScreenCloseEvent)->{
            // process the cropping result
            if(cropWindowAfterScreenCloseEvent.getCloseAction().equals(WINDOW_DISCARD_AND_CLOSE_ACTION)){
               //cropping window is closed by  "Cancel" button
            }else if(cropWindowAfterScreenCloseEvent.getCloseAction().equals(WINDOW_COMMIT_AND_CLOSE_ACTION)){
                // cropping window is closed  by "ok" button,then we can get the cropping result in bytes.
                byte[] result = options.getResult();
                if (result != null) {
                    //show the cropping result to an image component
                    image.setSource(StreamResource.class)
                            .setStreamSupplier(()-> new ByteArrayInputStream(result)).setBufferSize(1024);
                }
            }
        });
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
            cropBtn.setEnabled(true);
        }
    }
}
