package ru.est412.cryptoexplorer.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import ru.est412.cryptoexplorer.App;

import java.util.Optional;


public class RootLayoutController {

    private App app;

    public RootLayoutController() {
    }
    
    @FXML
    private void initialize() {
    }

    @FXML
    private void onMenuAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Crypto Explorer");
        alert.setHeaderText(null);
        alert.setContentText("Crypto Explorer 0.1.0\n"+
                "Release date: 2017-12-23\n"+
                "Copyright (c) 2017, Eduard Tukhvatullin\n"+
                "est412@gmail.com");
        alert.showAndWait();
    }

    @FXML
    public void onClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Exit Crypto Explorer?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            app.getPrimaryStage().close();
        }
    }

    public void setApp(App app) {
        this.app = app;
    }
}
