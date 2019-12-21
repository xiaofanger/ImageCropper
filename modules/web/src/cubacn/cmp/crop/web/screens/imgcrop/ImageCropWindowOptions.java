package cubacn.cmp.crop.web.screens.imgcrop;

import com.haulmont.cuba.gui.screen.ScreenOptions;
import cubacn.cmp.crop.web.toolkit.ui.imgcrop.ImgCropServerComponent;

import java.io.File;

/**
 * Represent  some options for cropping an image
 * Created by Ray.Lv on 2019/10/25.
 */
public class ImageCropWindowOptions implements ScreenOptions {

    private int cropQuality = 10;
    private byte[] result;
    private ImgCropServerComponent.ViewPort viewPort;

    public ImageCropWindowOptions(File file) {
        this(file, 10, new ImgCropServerComponent.ViewPort());
    }

    public ImageCropWindowOptions(File file, int cropQuality) {
        this(file, cropQuality, new ImgCropServerComponent.ViewPort());
    }

    /**
     * Contructor
     * @param file      the image file to be cropped
     * @param cropQuality the quality for  output image,1-10
     * @param viewPort Viewport
     */
    public ImageCropWindowOptions(File file, int cropQuality, ImgCropServerComponent.ViewPort viewPort) {
        this.imageFile = file;
        this.cropQuality = cropQuality;
        this.viewPort = viewPort;
    }

    private File imageFile;

    public File getImageFile() {
        return imageFile;
    }

    public int getCropQuality() {
        return cropQuality;
    }

    public void setCropQuality(int cropQuality) {
        this.cropQuality = cropQuality;
    }

    public void setResult(byte[] result) {
        this.result = result;
    }

    public byte[] getResult() {
        return result;
    }

    public String windowWidth = "800px";
    public String windowHeight = "600px";

    public ImgCropServerComponent.ViewPort getViewPort() {
        return viewPort;
    }


}
