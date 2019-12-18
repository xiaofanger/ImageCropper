package cubacn.cmp.crop.web.screens.imgcrop;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import cubacn.cmp.crop.web.toolkit.ui.imgcrop.ImgCropServerComponent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UiController("cubacn_ImageCropSettingsWindow")
@UiDescriptor("ImageCropSettingsWindow.xml")
public class ImageCropSettingsWindow extends Screen {

    @Inject
    private LookupField<Integer> qualityField;
    @Inject
    private TextField<Integer> heightField;
    @Inject
    private LookupField<String> viewportField;
    @Inject
    private TextField<Integer> widthField;
    private ImageCropWindowOptions options;

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
        List<String> viewportOptions = new ArrayList<>();
        viewportOptions.add("square");
        viewportOptions.add("circle");
        viewportField.setOptionsList(viewportOptions);
        if (this.options != null) {
            qualityField.setValue(this.options.getCropQuality());
            viewportField.setValue(this.options.getViewPort().viewPortType.toString());
            widthField.setValue(this.options.getViewPort().width);
            heightField.setValue(this.options.getViewPort().height);
        }
    }


    @Subscribe("cancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        this.closeWithDefaultAction();
    }

    @Subscribe("okBtn")
    public void onOkBtnClick(Button.ClickEvent event) {
        this.close(WINDOW_COMMIT_AND_CLOSE_ACTION);
    }

    private float quality;

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }


    @Subscribe("qualityField")
    public void onQualityFieldValueChange(HasValue.ValueChangeEvent event) {
        if (this.options != null) {
            this.options.setCropQuality((Integer)event.getValue());
        }
    }

}
