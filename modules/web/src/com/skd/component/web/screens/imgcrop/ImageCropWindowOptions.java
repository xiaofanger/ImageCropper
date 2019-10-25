package com.skd.component.web.screens.imgcrop;

import com.haulmont.cuba.gui.screen.ScreenOptions;

import java.io.File;

/**
 * Created by green on 2019/10/25.
 */
public class ImageCropWindowOptions implements ScreenOptions {

    private int cropQuality=10;
    private byte[] result;

    public ImageCropWindowOptions(File file){
        this.imageFile=file;
    }
    public ImageCropWindowOptions(File file,int cropQuality){
        this.imageFile=file;
        this.cropQuality=cropQuality;
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
}