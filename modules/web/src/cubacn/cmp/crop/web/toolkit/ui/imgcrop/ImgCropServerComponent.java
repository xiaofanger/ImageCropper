package cubacn.cmp.crop.web.toolkit.ui.imgcrop;


import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.shared.ui.JavaScriptComponentState;
import com.vaadin.ui.AbstractJavaScriptComponent;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@JavaScript({"croppie.js", "cubacn-imgcrop-connector.js"})
@StyleSheet({"croppie.css", "cubacn-imgcrop-connector.css"})
public class ImgCropServerComponent extends AbstractJavaScriptComponent {

    /**
     * ViewPort 类型
     */
    public enum ViewPortType {
        /**
         * 矩形
         */
        square,
        /**
         * 圆形
         */
        circle
    }

    public static class ViewPort {
        public ViewPort() {
        }

        public ViewPort(int width, int height, ViewPortType viewPortType) {
            this.width = width;
            this.height = height;
            this.viewPortType = viewPortType;
        }

        public int width = 100;
        public int height = 100;
        public ViewPortType viewPortType = ViewPortType.square;
    }

    String base64ImageFile(File fileDescriptor) {
        try (InputStream is = new FileInputStream(fileDescriptor)) {
            byte[] bytes = IOUtils.toByteArray(is);
            return Base64.encodeBase64String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImgCropServerComponent(File imageFileDescriptor, ViewPort viewPort
    ) {
        this(imageFileDescriptor, "", false, false, false, true, true, true, viewPort,1);
    }

    public ImgCropServerComponent(File imageFileDescriptor, ViewPort viewPort, int quality
    ) {
        this(imageFileDescriptor, "", false, false, false, true, true, true, viewPort,quality);
    }


    /**
     * 调整剪裁质量
     * @param quality 0-1
     */
    public void setQuality(float quality) {
        this.getState(true).quality = quality;
    }

    public ImgCropServerComponent(
            File imageFileDescriptor
    ) {
        this(imageFileDescriptor, "", false, false, false, true, true, true, new ViewPort(),1);
    }


    /**
     * 创建剪裁控件
     *
     * @param imageFile         图片文件
     * @param customClass       添加到剪裁控件上的自定义CSS类
     * @param enableExif        启用Exif信息读取，启用后croppie控件可以通过读取图片的extif信息来识别图片方向
     * @param enableOrientation 是否支持自定义方向
     * @param enableResize      是否支持调整viewport区域大小
     * @param enableZoom        是否支持缩放
     * @param mouseWheelZoom    是否允许使用鼠标滚轮缩放
     * @param showZoomer        是否显示绽放手柄
     * @param viewPort
     */
    public ImgCropServerComponent(
            File imageFile,
            String customClass,
            boolean enableExif,
            boolean enableOrientation,
            boolean enableResize,
            boolean enableZoom,
            boolean mouseWheelZoom,
            boolean showZoomer,
            ViewPort viewPort,
            int quality
    ) {
        this();
        String str64 = base64ImageFile(imageFile);
        ImgCropState state = getState();
        state.customClass = customClass;
        state.imageBase64 = str64;
        state.enableExif = enableExif;
        state.enableOrientation = enableOrientation;
        state.enableResize = enableResize;
        state.enableZoom = enableZoom;
        state.mouseWheelZoom = mouseWheelZoom;
        state.showZoomer = showZoomer;
        state.viewPort = viewPort;

    }

    public interface ImageUpdateListener {
        void imageUpdate(String imageBase64);
    }

    @Override
    protected ImgCropState getState() {
        return (ImgCropState) super.getState();
    }

    @Override
    public ImgCropState getState(boolean markAsDirty) {
        return (ImgCropState) super.getState(markAsDirty);
    }

    public ImageUpdateListener getImageUpdateListener() {
        return imageUpdateListener;
    }

    public String getImageBase64() {
        return getState().imageBase64;
    }

    public void setImageUpdateListener(ImageUpdateListener listener) {
        this.imageUpdateListener = listener;
    }

    private ImageUpdateListener imageUpdateListener;

    public String getCurrentImageBase64(){
        if(currentImageBase64==null){
           return  currentImageBase64;
        }
        if(currentImageBase64.contains(",")){
            return currentImageBase64.split(",")[1];
        }
        return currentImageBase64;
    }

    String currentImageBase64;

    public ImgCropServerComponent() {
        addFunction("imageUpdate", arguments -> {
            String imageBase64 = arguments.getString(0);
            currentImageBase64=imageBase64;
            if (imageUpdateListener != null) {
                imageUpdateListener.imageUpdate(imageBase64);
            }
        });
    }

    public void registerImgCropResultUpdateRpc(ImgCropResultUpdateRpc imgCropResultUpdateRpc) {
        if (imgCropResultUpdateRpc != null) {
            registerRpc(imgCropResultUpdateRpc);
        }
    }

    public void gerImageCropResult() {
        getRpcProxy(ImgCropClientRpc.class).gerImageCropResult();
    }

    public static class ImgCropState extends JavaScriptComponentState {
        public String imageBase64;
        public String customClass;
        public boolean enableExif;
        public boolean enableOrientation;
        public boolean enableResize;
        public boolean enableZoom;
        public boolean mouseWheelZoom;
        public boolean showZoomer;
        public ViewPort viewPort;
        public float quality = 1;
    }
}
