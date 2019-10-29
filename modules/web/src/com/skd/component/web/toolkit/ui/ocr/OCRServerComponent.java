package com.skd.component.web.toolkit.ui.ocr;


import com.haulmont.cuba.web.widgets.WebJarResource;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.shared.ui.JavaScriptComponentState;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractJavaScriptComponent;
import elemental.json.JsonArray;

@WebJarResource({"jquery:jquery.min.js","jquery-ui:jquery-ui.min.js"})
@JavaScript({"webcam.js","ocr-connector.js"})
@StyleSheet({"ocr-connector.css"})
public class OCRServerComponent extends AbstractJavaScriptComponent {

    public interface SnapshotChangedListener {
        void snapshotChanged(String newValue);
    }

    @Override
    protected OCRState getState() {
        return (OCRState) super.getState();
    }

    @Override
    public OCRState getState(boolean markAsDirty) {
        return (OCRState) super.getState(markAsDirty);
    }

    public SnapshotChangedListener getSnapshotChangedListener() {
        return snapshotChangedListener;
    }

    public void getSnapshotChangedListener(SnapshotChangedListener listener) {
        this.snapshotChangedListener = listener;
    }

    private SnapshotChangedListener  snapshotChangedListener;

    public OCRServerComponent(){
        addFunction("snapshotChanged", arguments -> {
            String snapshot = arguments.getString(0);
            getState(false).snapshotBase64 = snapshot;
            if(snapshotChangedListener!=null){
                snapshotChangedListener.snapshotChanged(snapshot);
            }
        });

    }

    public static class OCRState extends JavaScriptComponentState {
       public String snapshotBase64;
       public String recognition;
    }
}
