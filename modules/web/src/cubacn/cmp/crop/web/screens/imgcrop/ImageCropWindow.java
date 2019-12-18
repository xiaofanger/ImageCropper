package cubacn.cmp.crop.web.screens.imgcrop;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.builders.AfterScreenCloseEvent;
import com.haulmont.cuba.gui.builders.ScreenClassBuilder;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import cubacn.cmp.crop.web.toolkit.ui.imgcrop.ImgCropResultUpdateRpc;
import cubacn.cmp.crop.web.toolkit.ui.imgcrop.ImgCropServerComponent;
import com.vaadin.ui.Layout;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import java.util.function.Consumer;


/**
 * Created by Ray.Lv on 2019/10/25.
 */

@UiController("cubacn_ImageCropWindow")
@UiDescriptor("ImageCropWindow.xml")
public class ImageCropWindow extends Screen {
    @Inject
    private VBoxLayout cropCmpCtn;

    private ImageCropWindowOptions options;
    private ImgCropServerComponent imgCrop;

    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if(options instanceof ImageCropWindowOptions){
            this.options= (ImageCropWindowOptions)options;
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
                this.options.getCropQuality());
        cropCmpCtn.unwrap(Layout.class).addComponent(imgCrop);
    }

    public static String getDataSize(long size){
        return FileUtils.byteCountToDisplaySize(size);
    }


    @Subscribe("okBtn")
    public void onOkBtnClick(Button.ClickEvent event) {
        this.imgCrop.registerImgCropResultUpdateRpc((ImgCropResultUpdateRpc) base64 -> {
            String file;
            if(base64.contains(",")){
                file = base64.split(",")[1];
            } else {
                file = base64;
            }
            byte[] result = Base64.decodeBase64(file);
            options.setResult(result);
            close(WINDOW_COMMIT_AND_CLOSE_ACTION);
        });
        this.imgCrop.gerImageCropResult();
    }

    @Subscribe("cancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        this.closeWithDefaultAction();
    }
    @Subscribe("settingsBtn")
    public void onSettingsBtnClick(Button.ClickEvent event) {
        ScreenBuilders screenBuilders = AppBeans.get(ScreenBuilders.class);
        ImageCropSettingsWindow settingsWindow = screenBuilders.screen(this)
                .withScreenClass(ImageCropSettingsWindow.class)
                .withLaunchMode(OpenMode.DIALOG)
                .withOptions(options)
                .withAfterCloseListener((settingsWindowCloseEvent)->{
                    if(settingsWindowCloseEvent.getCloseAction().equals(WINDOW_DISCARD_AND_CLOSE_ACTION)){
                        //close by  "Cancel" button
                    }else if(settingsWindowCloseEvent.getCloseAction().equals(WINDOW_COMMIT_AND_CLOSE_ACTION)){
                        // close by "ok" button
                        imgCrop.setQuality(new Integer(this.options.getCropQuality()).floatValue()/10f);
                    }
                })
                .build();
        settingsWindow.show();
    }

    public static void showAsDialog(FrameOwner origin, ImageCropWindowOptions options, Consumer<AfterScreenCloseEvent<ImageCropWindow>> closeEventConsumer){
        ScreenBuilders screenBuilders = AppBeans.get(ScreenBuilders.class);
        ScreenClassBuilder<ImageCropWindow> screenBuilder = screenBuilders.screen(origin)
                .withScreenClass(ImageCropWindow.class)
                .withLaunchMode(OpenMode.DIALOG)
                .withOptions(options);

        if(closeEventConsumer!=null){
            screenBuilder.withAfterCloseListener(closeEventConsumer);
        }
        ImageCropWindow screen = screenBuilder.build();
        DialogWindow dialogWindow=((DialogWindow)screen.getWindow());
        dialogWindow.setDialogHeight(options.windowHeight);
        dialogWindow.setDialogWidth(options.windowWidth);
        screen.show();
    }
}
