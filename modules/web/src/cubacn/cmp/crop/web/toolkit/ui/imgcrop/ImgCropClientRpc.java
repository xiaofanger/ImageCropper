package cubacn.cmp.crop.web.toolkit.ui.imgcrop;

import com.vaadin.shared.communication.ClientRpc;

public interface ImgCropClientRpc extends ClientRpc {

    String gerImageCropResult();

    /**
     * Rotate 90 degrees clockwise or counterclockwise.
     */
    void rotationImgAngle(int angle);

}
