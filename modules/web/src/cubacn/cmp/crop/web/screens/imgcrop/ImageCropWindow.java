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
    @Inject
    private Label<String> label;
    @Inject
    private LookupField<Integer> qualityField;

    private ImageCropWindowOptions options;
    private ImgCropServerComponent imgCrop;
    @Inject
    private Label<String> viewportLabel;

    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if(options instanceof ImageCropWindowOptions){
            this.options= (ImageCropWindowOptions)options;
        }
        List<Integer> qualityOptions = new ArrayList<>();
        for (int i = 10; i >= 1; i--) {
            qualityOptions.add(i);
        }
        qualityField.setOptionsList(qualityOptions);
        if (this.options != null) {
            qualityField.setValue(this.options.getCropQuality());
            label.setValue(this.options.getViewPort().width + " * " + this.options.getViewPort().height);
            viewportLabel.setValue(this.options.getViewPort().viewPortType.toString());
        }
    }

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

    @Subscribe("qualityField")
    public void onQualityFieldValueChange(HasValue.ValueChangeEvent event) {
        if (options != null) {
            options.setCropQuality((Integer) event.getValue());
            if(imgCrop!=null){
                imgCrop.setQuality(((Integer)event.getValue()).floatValue()/10f);
            }
        }
    }

    @Subscribe("spinLeft")
    public void onSpinLeftClick(Button.ClickEvent event) {
        this.imgCrop.setImageRotationAngle(90);
    }

    @Subscribe("spinRight")
    public void onSpinRightClick(Button.ClickEvent event) {
        this.imgCrop.setImageRotationAngle(-90);
    }

    /**
     * Show a dialog for cropping an image
     * @param origin caller screen
     * @param options options for cropping
     * @param closeEventConsumer callback for processing the cropping result
     */
    public static void showAsDialog(FrameOwner origin, ImageCropWindowOptions options,
                                    Consumer<AfterScreenCloseEvent<ImageCropWindow>> closeEventConsumer){
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
