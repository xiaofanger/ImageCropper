function cubacn_cmp_crop_web_toolkit_ui_imgcrop_ImgCropServerComponent(){
    var connector = this;
    var element = connector.getElement();
    var croppie=null;
    var quality=1;
    connector.doDestroy=function(){
        if(croppie!=null){
            destroy();
        }
    };
    var me=this;
    var updateF=function(){
        croppie.result({
            quality:quality,
            format:"jpeg"
        }).then(function (base64) {
            me.imageUpdate(base64);
        })
    };
    connector.onStateChange = function() {
        var me=this;
        var opts={};
        var state = connector.getState();
        quality=state.quality;
        quality=parseFloat(quality.toFixed(1));
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
                updateF();
            });
            var url="data:image/jpeg;base64,"+state.imageBase64;
            croppie=new Croppie(element, opts);
            croppie.bind({
                url:url
            })
        }else {
            updateF();
        }


    }
}
