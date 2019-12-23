[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
 
#### OVERVIEW

In modern enterprise applications, uploading image is a popular requirement, for example, upload ID Card images, avatar images or image attachments etc.
Almost all applications have restriction on image size, in order to reduce upload time and stored file size.

Generally, the original image does not fit the size requirement, we need to process the raw image to fit the application restriction. 
This component will be helpful for processing the original image.              

#### FEATURES
0. Based on the  [Croppie Project](https://github.com/foliotek/croppie), so investigating that project will be helpful before using this component
0. Working with [FileUploadField](https://doc.cuba-platform.com/manual-7.1/gui_FileUploadField.html) from CUBA    
0. Provided a simple API for using the component
0. Provided some options for customize the component appearance, cropping area, image quality etc
0. Reviewing the cropping result in real time
     
#### Screenshot
![image](https://github.com/cubacn/ImageCropper/blob/master/demoImage/screenshot.png)
#### DEMO 
![image](https://github.com/cubacn/ImageCropper/blob/master/demoImage/CropDemo.gif)

#### Sample Code
```java
    @Subscribe("cropBtn")
    public void onCropBtnClick(Button.ClickEvent event) {
        UUID fileId = uploadField.getFileId();
        File file = fileUploadingAPI.getFile(fileId);
        if(file==null){
            return;
        }
        // Create an viewport configuration object
        ImgCropServerComponent.ViewPort viewPort =
                new ImgCropServerComponent.ViewPort(200, 100,
                ImgCropServerComponent.ViewPortType.square);
        // Create an option object
        ImageCropWindowOptions options = new ImageCropWindowOptions(file, 10, viewPort);
        // Open a winow for cropping an image
        ImageCropWindow.showAsDialog(this, options, (cropWindowAfterScreenCloseEvent)->{
            // process the cropping result
            if(cropWindowAfterScreenCloseEvent.getCloseAction().equals(WINDOW_DISCARD_AND_CLOSE_ACTION)){
               //cropping window is closed by  "Cancel" button
            }else if(cropWindowAfterScreenCloseEvent.getCloseAction().equals(WINDOW_COMMIT_AND_CLOSE_ACTION)){
                // cropping window is closed  by "ok" button,then we can get the cropping result in bytes.
                byte[] result = options.getResult();
                if (result != null) {
                    //show the cropping result to an image component
                    image.setSource(StreamResource.class)
                            .setStreamSupplier(()-> new ByteArrayInputStream(result)).setBufferSize(1024);
                }
            }
        });
    }
```
#### Sample Screen
This component included a demo screen. Clone and run this project, you will see an "Sample" menu item in menu bar.    
