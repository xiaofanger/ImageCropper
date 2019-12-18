function cubacn_cmp_crop_web_toolkit_ui_imgcrop_ImgCropServerComponent() {
    var connector = this;
    var element = connector.getElement();
    var rpcProxy = connector.getRpcProxy();
    var croppie = null;
    var quality = 1;
    var imageBase64 = null;
    // Create preview div element.
    var preview = document.createElement('div');
    preview.setAttribute('class', 'cr-preview')

    connector.doDestroy = function () {
        if (croppie != null) {
            destroy();
        }
    };
    var me = this;
    // Crop result listener
    var updateF = function () {
        croppie.result({
            type: 'base64',
            quality: quality,
            format: "jpeg"
        }).then(function (base64) {
            imageBase64 = base64
        })
    };
    // Preview
    var previewF = function (pw) {
        croppie.result({
            type: 'rawcanvas',
            quality: quality,
            size: {
                width: pw,
                height: pw
            }
        }).then(function (canvas) {
            preview.childNodes.forEach(function (item) {
                item.remove()
            });
            preview.append(canvas)
        })
    };

    connector.registerRpc({
        gerImageCropResult: function () {
            rpcProxy.resultUpdate(imageBase64)
        }
    });

    connector.onStateChange = function () {
        var me = this;
        var ew, eh, pw;
        var opts = {};
        var state = connector.getState();
        quality = state.quality;
        quality = parseFloat(quality.toFixed(1));
        if (croppie == null) {
            opts = {
                customClass: state.customClass,
                enableExif: state.enableExif,
                enableOrientation: state.enableOrientation,
                enableResize: state.enableResize,
                enableZoom: state.enableZoom,
                mouseWheelZoom: state.mouseWheelZoom,
                showZoomer: state.showZoomer,
                viewport: state.viewPort
            };

            var url = "data:image/jpeg;base64," + state.imageBase64;
            croppie = new Croppie(element, opts);
            // Add preview elements after setting the crop component
            // and set preview properties.
            ew = element.offsetWidth;
            eh = element.offsetHeight;
            pw = ew * 0.26;
            element.style.width = ew * 0.7 + 'px';
            preview.style.maxHeight = pw + 'px';
            preview.style.maxWidth = pw + 'px';
            element.parentElement.appendChild(preview);

            element.addEventListener('update', function (ev) {
                previewF(pw);
                updateF();
            });

            croppie.bind({
                url: url
            })
        } else {
            croppie.elements.boundary.style.width = '70%';
            croppie.elements.boundary.style.margin = '0';
            previewF(pw);
            updateF();
        }

    }
}
