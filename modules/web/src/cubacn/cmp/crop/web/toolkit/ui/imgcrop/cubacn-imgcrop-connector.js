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
    var img = document.createElement('img');
    img.setAttribute('src', '');
    var sizeLabel = document.createElement('div');
    sizeLabel.setAttribute('class', 'cr-preview-label');

    connector.doDestroy = function () {
        if (croppie != null) {
            destroy();
        }
    };
    var me = this;
    /**
     * Crop result callback.
     * @param pw Preview element height
     */
    var updateF = function (pw) {
        croppie.result({
            type: 'base64',
            quality: quality,
            format: "jpeg"
        }).then(function (base64) {
            var size = getImgSize(base64);
            img.src = base64;
            sizeLabel.innerText = size+'KB';
            imageBase64 = base64;
        })
    };

    /**
     * Get image size.
     * @param base64url
     * @returns {string}
     */
    var getImgSize = function (base64url) {
        var str = base64url.replace('data:image/jpeg;base64,', '');
        var strLength = str.length;
        var fileLength = parseInt(strLength - (strLength / 8) * 2);
        return (fileLength / 1024).toFixed(2);
    };

    connector.registerRpc({
        gerImageCropResult: function () {
            rpcProxy.resultUpdate(imageBase64)
        }
    });

    connector.onStateChange = function () {
        var me = this;
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
            var ew = element.offsetWidth;
            var eh = element.offsetHeight;
            var pw = ew * 0.26;
            element.style.width = ew * 0.7 + 'px';
            preview.style.maxHeight = pw + 'px';
            preview.style.maxWidth = pw + 'px';
            img.setAttribute('width', pw+'px');
            img.setAttribute('height', pw+'px');
            preview.appendChild(img);
            preview.appendChild(sizeLabel);
            element.parentElement.appendChild(preview);

            element.addEventListener('update', function (ev) {
                updateF();
            });
            croppie.bind({
                url: url
            })
        } else {
            croppie.element.style.width = '70%';
            updateF();
        }

    }
}
