package com.skd.component.web.screens.imgcrop;

import com.haulmont.cuba.gui.screen.ScreenOptions;
import com.skd.component.web.toolkit.ui.imgcrop.ImgCropServerComponent;

import java.io.File;

/**
 * Created by green on 2019/10/25.
 */
public class ImageCropWindowOptions implements ScreenOptions {

    private int cropQuality=10;
    private byte[] result;
    private ImgCropServerComponent.ViewPort viewPort;

    public ImageCropWindowOptions(File file){
        this(file,10,new ImgCropServerComponent.ViewPort());
    }
    public ImageCropWindowOptions(File file, int cropQuality){
        this(file,cropQuality,new ImgCropServerComponent.ViewPort());
    }

    /**
     * 图片剪裁选项
     * @param file 要剪裁的图片文件
     * @param cropQuality 图片质量 1-10
     * @param viewPort
     */
    public ImageCropWindowOptions(File file, int cropQuality, ImgCropServerComponent.ViewPort viewPort){
        this.imageFile=file;
        this.cropQuality=cropQuality;
        this.viewPort=viewPort;
    }
    private File imageFile;

    public File getImageFile() {
        return imageFile;
    }
    public int getCropQuality() {
        return cropQuality;
    }
    public void setResult(byte[] result){
        this.result=result;
    }
    public byte[] getResult(){
        return result;
    }

    public ImgCropServerComponent.ViewPort getViewPort() {
        return viewPort;
    }
}
