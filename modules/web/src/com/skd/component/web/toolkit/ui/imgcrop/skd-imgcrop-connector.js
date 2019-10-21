function com_skd_component_web_toolkit_ui_imgcrop_ImgCropServerComponent(){
    var connector = this;
    var element = connector.getElement();

    var html=[
    ].join("\r");
    var node=$(html)[0];
    element.appendChild(node);


    connector.onStateChange = function() {
        var state = connector.getState();
    }
}
