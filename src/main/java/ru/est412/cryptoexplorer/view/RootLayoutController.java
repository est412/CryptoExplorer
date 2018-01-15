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
        Package pkg = this.getClass().getPackage();
        alert.setTitle("About " + pkg.getImplementationTitle());
        alert.setHeaderText(null);
        alert.setContentText("Version: " + pkg.getImplementationVersion() +"\n"+
                "Copyright: " + pkg.getImplementationVendor() + "\n");
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
