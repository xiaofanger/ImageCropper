package com.skd.component.web.screens.imgcrop;

import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.skd.component.web.toolkit.ui.imgcrop.ImgCropServerComponent;
import com.vaadin.ui.Layout;

import javax.inject.Inject;


/**
 * Created by green on 2019/10/21.
 */

@UiController("skd_Imgcropsample")
@UiDescriptor("ImgCropSample.xml")
public class Imgcropsample extends Screen {
    @Inject
    private VBoxLayout imgcropCtn;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        ImgCropServerComponent imgcrop = new ImgCropServerComponent();
        imgcrop.setWidth("250px");
        imgcrop.setHeight("250px");
        imgcrop.setImageUpdateListener(newValue -> {
            System.out.println("test");
        });
        imgcropCtn.unwrap(Layout.class).addComponent(imgcrop);
    }
}