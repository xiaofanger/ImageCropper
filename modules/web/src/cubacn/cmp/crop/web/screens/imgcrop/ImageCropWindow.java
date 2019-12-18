package cubacn.cmp.crop.web.screens.imgcrop;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.builders.AfterScreenCloseEvent;
import com.haulmont.cuba.gui.builders.ScreenClassBuilder;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import cubacn.cmp.crop.web.toolkit.ui.imgcrop.ImgCropServerComponent;
import com.vaadin.ui.Layout;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
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
//    @Inject
//    private Label<String> fileSizeLabel;
//    @Inject
//    private LookupField qualityField;
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
//            qualityField.setOptionsList(list);
//            qualityField.setValue(this.options.getCropQuality());
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
        this.imgCrop.registerImgCropResultUpdateRpc(this.options.getImageResultUpdateListener());
        cropCmpCtn.unwrap(Layout.class).addComponent(imgCrop);
    }



    public static String getDataSize(long size){
        return FileUtils.byteCountToDisplaySize(size);
    }


    @Subscribe("okBtn")
    public void onOkBtnClick(Button.ClickEvent event) {
        this.imgCrop.show();
        this.close(WINDOW_COMMIT_AND_CLOSE_ACTION);
    }
    @Subscribe("cancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        this.close(WINDOW_DISCARD_AND_CLOSE_ACTION);
    }
    @Subscribe("settingsBtn")
    public void onSettingsBtnClick(Button.ClickEvent event) {
        imgCrop.show();
    }


//    @Subscribe("qualityField")
//    public void onQualityFieldValueChange(HasValue.ValueChangeEvent event) {
//        if(imgCrop!=null){
//            imgCrop.setQuality(((Integer)event.getValue()).floatValue()/10f);
//        }
//    }

    public static void showAsDialog(FrameOwner origin,ImageCropWindowOptions options,Consumer<AfterScreenCloseEvent<ImageCropWindow>> closeEventConsumer){
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
