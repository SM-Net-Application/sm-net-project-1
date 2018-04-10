package com.sm.net.miniviewer;

import java.io.IOException;

import com.sm.net.miniviewer.view.MainView;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(Main.class.getResource("view/MainView.fxml"));
			AnchorPane anchorPane;
			anchorPane = (AnchorPane) fxmlLoader.load();

			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);

			primaryStage.setTitle("SM-Net: MiniViewer 1.2");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("view/icon.png")));

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					System.exit(0);
				}
			});

			MainView controller = (MainView) fxmlLoader.getController();
			controller.setStage(primaryStage);
			controller.init();

			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
