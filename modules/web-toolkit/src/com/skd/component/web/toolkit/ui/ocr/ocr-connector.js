function com_skd_component_web_toolkit_ui_ocr_OCRServerComponent(){
    var connector = this;
    var element = connector.getElement();
    var html=[
        '<div style="width: 100%;height: 100%;">',
            '<div style="width:50%;height:100%;">',
                '<div style="width: 100%;height: 50%;" class="ocr-preview">',
                    '<div style="height:15px;" class="ocr-preview-toolbar">',
                        '<span class="ocr-preview-title">预览<span><span class="ocr-preview-button">识别</span>',
                    '</div>',
                   '<div class="ocr-preview-body"></div>',
                '<div style="width: 100%;height: 50%;" class="ocr-result">',
                    '<div style="height:15px;"  class="ocr-result-title">快照</div>',
                    '<div class="ocr-result-body"></div>',
                '</div>',
            '</div>',
            '<div style="width: 50%;height: 50%;">' ,
                '<div style="height: 15px" class="ocr-recognition-title">识别结果</div>',
                '<div class="ocr-recognition-body"></div>',
            '</div>',
        '</div>'
    ].join("\r");
    element.append(html);
    var previewEl=element.querySelector(".ocr-preview");
    Webcam.attach(previewEl);
    var button=element.querySelector(".ocr-preview-button");
    var snapshot=element.querySelector(".ocr-preview-button");
    $(button).click(
        function () {
            Webcam.snap( function(data_uri) {
                document.getElementById('result-body').innerHTML = '<img src="'+data_uri+'"/>';

            } );
        }
    );


    var slider = $("div", element).slider({
        range: true,
        slide: function(event, ui) {
            connector.valueChanged(ui.values);
        }
    });

    connector.onStateChange = function() {
        var state = connector.getState();
        slider.slider("values", state.values);
        slider.slider("option", "min", state.minValue);
        slider.slider("option", "max", state.maxValue);
        $(element).width(state.width);
    }
}
