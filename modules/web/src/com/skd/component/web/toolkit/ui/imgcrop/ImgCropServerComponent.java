package com.skd.component.web.toolkit.ui.imgcrop;


import com.haulmont.cuba.web.widgets.WebJarResource;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.shared.ui.JavaScriptComponentState;
import com.vaadin.ui.AbstractJavaScriptComponent;


@JavaScript({"croppie.js","skd-imgcrop-connector.js"})
@StyleSheet({"croppie.css","skd-imgcrop-connector.css"})
public class ImgCropServerComponent extends AbstractJavaScriptComponent {

    public interface ImageUpdateListener {
        void imageUpdate(String imageBase64);
    }

    @Override
    protected ImgCropState  getState() {
        return (ImgCropState)super.getState();
    }

    @Override
    public ImgCropState getState(boolean markAsDirty) {
        return (ImgCropState) super.getState(markAsDirty);
    }

    public ImageUpdateListener getImageUpdateListener() {
        return imageUpdateListener;
    }

    public void setImageUpdateListener(ImageUpdateListener listener) {
        this.imageUpdateListener = listener;
    }

    private ImageUpdateListener  imageUpdateListener;

    public ImgCropServerComponent(){
        addFunction("imageUpdate", arguments -> {
            String imageBase64 = arguments.getString(0);
            getState(false).imageBase64 = imageBase64;
            if(imageUpdateListener!=null){
                imageUpdateListener.imageUpdate(imageBase64);
            }
        });

    }

    public static class ImgCropState extends JavaScriptComponentState {
       public String imageBase64;
    }
}
