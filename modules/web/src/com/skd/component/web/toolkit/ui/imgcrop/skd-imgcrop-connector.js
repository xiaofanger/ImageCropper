function com_skd_component_web_toolkit_ui_imgcrop_ImgCropServerComponent(){
    var connector = this;
    var element = connector.getElement();
    var croppie=null;
    var quality=1;
    connector.doDestroy=function(){
        if(croppie!=null){
            destroy();
        }
    };
    connector.onStateChange = function() {
        var me=this;
        var opts={};
        var state = connector.getState();

        if(croppie==null){
            opts={
                customClass:state.customClass,
                enableExif:state.enableExif,
                enableOrientation:state.enableOrientation,
                enableResize:state.enableResize,
                enableZoom:state.enableZoom,
                mouseWheelZoom:state.mouseWheelZoom,
                showZoomer:state.showZoomer,
                viewport:state.viewPort
            };
            element.addEventListener('update', function(ev) {
                croppie.result({
                    quality:quality
                }).then(function (base64) {
                    me.imageUpdate(base64);
                })
            });
            var url="data:image/png;base64,"+state.imageBase64;
            croppie=new Croppie(element, opts);
            croppie.bind({
                url:url
            })
        }
        quality=state.quality;

    }
}
