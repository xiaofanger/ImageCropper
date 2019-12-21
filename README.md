# 

#### OVERVIEW

Some times ,we need to upload images to an application,likes ID Card image, avatar image etc., almost all application restrict  the image's size. Usually  the original image is not fit the requirement, we need to  process the original image to fit the requirement of  application. This component will be helpful  for processing the original image.              

#### FEATURES
0. Based the  [Croppie Project](https://github.com/foliotek/croppie), so examine the project will be helpful for using this component
0. Working with [FileUploadField](https://doc.cuba-platform.com/manual-7.1/gui_FileUploadField.html) from CUBA    
0. Provide a simple API for using the component
0. Provide some options for customize the component appearance,cropping area,image quality etc.
0. Review the cropping result in real time
     
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
This component included a demo screen.Clone and run this project, you will see an "Sample" menu item in menu bar.    
