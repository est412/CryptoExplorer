package ru.est412.cryptoexplorer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.est412.cryptoexplorer.model.*;
import ru.est412.cryptoexplorer.view.MainViewController;
import ru.est412.cryptoexplorer.view.RootLayoutController;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) throws SQLException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Crypto Explorer");
		
        Services.load();

		initRootLayout();
		initMainView();

        primaryStage.show();
    }

	public static void main(String[] args) {
		launch(args);
	}

	public void initRootLayout() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ClassLoader.getSystemResource("fxml/RootLayout.fxml"));
		try {
			rootLayout = loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		RootLayoutController controller = loader.getController();
        primaryStage.setOnCloseRequest( e ->
        {   e.consume();
            controller.onClose();
        } );
		controller.setApp(this);
	}
	
	public void initMainView() throws SQLException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ClassLoader.getSystemResource("fxml/MainView.fxml"));
		try {
			SplitPane mainView = loader.load();
			rootLayout.setCenter(mainView);
		} catch (IOException e) {
			e.printStackTrace();
		}
        MainViewController controller = loader.getController();
        controller.refreshTables();
	}

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
