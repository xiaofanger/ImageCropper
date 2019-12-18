package cubacn.cmp.crop.web.screens.imgcrop;

import com.haulmont.cuba.gui.screen.ScreenOptions;
import cubacn.cmp.crop.web.toolkit.ui.imgcrop.ImgCropResultUpdateRpc;
import cubacn.cmp.crop.web.toolkit.ui.imgcrop.ImgCropServerComponent;
import org.apache.commons.codec.binary.Base64;

import java.io.File;

/**
 * Created by Ray.Lv on 2019/10/25.
 */
public class ImageCropWindowOptions implements ScreenOptions {

    private int cropQuality=10;
    private byte[] result;
    private ImgCropServerComponent.ViewPort viewPort;
    private ImgCropResultUpdateRpc imageResultUpdateListener;

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
     * @param viewPort 目标区域视图配置
     */
    public ImageCropWindowOptions(File file, int cropQuality, ImgCropServerComponent.ViewPort viewPort){
        this.imageFile=file;
        this.cropQuality=cropQuality;
        this.viewPort=viewPort;
        this.setDefaultImageResultUpdateListener();
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
    public String windowWidth="800px";
    public String windowHeight="600px";

    public ImgCropServerComponent.ViewPort getViewPort() {
        return viewPort;
    }

    public ImgCropResultUpdateRpc getImageResultUpdateListener() {
        return imageResultUpdateListener;
    }

    public void setImageResultUpdateListener(ImgCropResultUpdateRpc imageResultUpdateListener) {
        this.imageResultUpdateListener = imageResultUpdateListener;
    }

    private void setDefaultImageResultUpdateListener() {
        this.imageResultUpdateListener = (ImgCropResultUpdateRpc) base64 -> {
            System.out.println(base64);
            String res;
            if(base64.contains(",")){
                res = base64.split(",")[1];
            } else {
                res = base64;
            }
            result = Base64.decodeBase64(res);
        };
    }
}
