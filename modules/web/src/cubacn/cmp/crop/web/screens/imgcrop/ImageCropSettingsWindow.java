package cubacn.cmp.crop.web.screens.imgcrop;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("cubacn_ImageCropSettingsWindow")
@UiDescriptor("ImageCropSettingsWindow.xml")
public class ImageCropSettingsWindow extends Screen {

    @Subscribe("cancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        this.closeWithDefaultAction();
    }

    @Subscribe("okBtn")
    public void onOkBtnClick(Button.ClickEvent event) {
        this.close(WINDOW_COMMIT_AND_CLOSE_ACTION);
    }

}
