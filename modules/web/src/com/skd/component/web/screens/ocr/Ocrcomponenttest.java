package com.skd.component.web.screens.ocr;

import com.haulmont.cuba.gui.components.GroupBoxLayout;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.skd.component.web.toolkit.ui.ocr.OCRServerComponent;
import com.vaadin.ui.Layout;

import javax.inject.Inject;


/**
 * Created by green on 2019/10/16.
 */

@UiController("skd_Ocrcomponenttest")
@UiDescriptor("OCRComponentTest.xml")
public class Ocrcomponenttest extends Screen {
    @Subscribe
    public void onInit(InitEvent event) {

    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        OCRServerComponent ocrComponent = new OCRServerComponent();
        main.unwrap(Layout.class).addComponent(ocrComponent);
    }

    @Subscribe
    public void onAfterInit(AfterInitEvent event) {

    }

    @Inject
    private HBoxLayout main;
}
