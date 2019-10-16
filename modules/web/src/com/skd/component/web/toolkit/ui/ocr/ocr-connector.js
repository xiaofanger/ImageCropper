function com_skd_component_web_toolkit_ui_ocr_OCRServerComponent(){
    var connector = this;
    var element = connector.getElement();

    var html=[
        '<table  style= "width: 100%;height: 100%;" class="skd-ocr">' +
        '   <tr style="height:50%;width:100%">' +
        '       <td style="width: 50%;" class="skd-orc-preview-td">' +
        '           <table><tr><td>预览</td></tr><tr><td><div style="width: 100%;height:100%" class="skd-ocr-preview" ></div></td></tr></table>' +
        '       </td>' +
        '       <td style="width:50%;" class="skd-orc-snapshot-td"></td>' +
        '   </tr>' +
        '   <tr style="width: 100%;height: 50%">' +
        '       <td class="skd-ocr-result-td"></td>' +
        '   </tr>' +
        '</table>'
    ].join("\r");
    var node=$(html)[0];
    element.appendChild(node);
    var previewEl=element.querySelector(".skd-ocr-preview");
    Webcam.attach(previewEl);
    // var button=element.querySelector(".ocr-preview-button");
    // var snapshot=element.querySelector(".ocr-preview-button");
    // $(button).click(
    //     function () {
    //         Webcam.snap( function(data_uri) {
    //             document.getElementById('result-body').innerHTML = '<img src="'+data_uri+'"/>';
    //
    //         } );
    //     }
    // );



    connector.onStateChange = function() {
        var state = connector.getState();
        slider.slider("values", state.values);
        slider.slider("option", "min", state.minValue);
        slider.slider("option", "max", state.maxValue);
        $(element).width(state.width);
    }
}
