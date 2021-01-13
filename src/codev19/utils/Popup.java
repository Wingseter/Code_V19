package codev19.utils;

import javafx.scene.control.Alert;

public class Popup {
    private Alert alert;
    public Popup(){
        alert = new Alert(Alert.AlertType.INFORMATION);
    }

    public void show(String title, String header, String content){
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
