package cubacn.cmp.crop.web.toolkit.ui.imgcrop;

import com.vaadin.shared.communication.ServerRpc;

public interface ImgCropResultUpdateRpc extends ServerRpc {

    void resultUpdate(String base64);

}
