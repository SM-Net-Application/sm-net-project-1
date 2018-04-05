package com.sm.net.miniviewer.view;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import com.sm.net.miniviewer.Main;
import com.sm.net.miniviewer.model.MyFile;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainView {

	@FXML
	private ListView<MyFile> listView;
	@FXML
	private Button buttonView;

	@FXML
	private void initialize() {

		listView.setStyle("-fx-font: 15px System");
		buttonView.setStyle("-fx-font: 15px System");
		buttonView.setText("Visualizza");

		setListViewDragAndDrop();
		setButtonEventHandler();
	}

	public void init() {
		stageViewer = null;
	}

	private Stage stage;
	private Stage stageViewer;

	private void setButtonEventHandler() {

		buttonView.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (listView.getSelectionModel().getSelectedIndex() > -1) {

					if (stageViewer != null) {
						closeViewer();
						buttonView.setText("Visualizza");
					} else {
						loadViewer(listView.getSelectionModel().getSelectedItem());
						buttonView.setText("Nascondi");
					}

				}
			}
		});
	}

	private void closeViewer() {
		stageViewer.close();
		stageViewer = null;
	}

	private void loadViewer(MyFile selectedItem) {

		if (isImage(selectedItem))
			loadViewerImage(selectedItem);
		else if (isVideo(selectedItem))
			loadViewerVideo(selectedItem);
	}

	private void loadViewerImage(MyFile selectedItem) {

		try {

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(Main.class.getResource("view/Viewer.fxml"));
			AnchorPane anchorPane;
			anchorPane = (AnchorPane) fxmlLoader.load();

			Scene scene = new Scene(anchorPane);
			Stage stage = new Stage();
			stage.setScene(scene);

			stage.setResizable(false);
			stage.setAlwaysOnTop(true);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.getIcons().add(new Image(Main.class.getResourceAsStream("view/icon.png")));

			setStageViewer(stage);

			Rectangle2D bounds = getScreen();
			double height = bounds.getHeight();
			double width = bounds.getWidth();
			double minX = bounds.getMinX();
			double minY = bounds.getMinY();

			stage.setWidth(width);
			stage.setHeight(height);
			stage.setX(minX);
			stage.setY(minY);

			ViewerImage controller = (ViewerImage) fxmlLoader.getController();
			controller.init(selectedItem, width, height);

			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Rectangle2D getScreen() {
		
		ObservableList<Screen> screens = Screen.getScreens();

		Screen screen = null;
		if (screens.size() > 1) {
			screen = screens.get(1);
		} else {
			screen = screens.get(0);
		}
		Rectangle2D bounds = screen.getBounds();
		return bounds;
	}

	private void loadViewerVideo(MyFile selectedItem) {

		try {

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(Main.class.getResource("view/ViewerVideo.fxml"));
			AnchorPane anchorPane;
			anchorPane = (AnchorPane) fxmlLoader.load();

			Scene scene = new Scene(anchorPane);
			Stage stage = new Stage();
			stage.setScene(scene);

			stage.setResizable(false);
			stage.setAlwaysOnTop(true);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.getIcons().add(new Image(Main.class.getResourceAsStream("view/icon.png")));

			setStageViewer(stage);

			Rectangle2D bounds = getScreen();
			double height = bounds.getHeight();
			double width = bounds.getWidth();
			double minX = bounds.getMinX();
			double minY = bounds.getMinY();

			stage.setWidth(width);
			stage.setHeight(height);
			stage.setX(minX);
			stage.setY(minY);

			ViewerVideo controller = (ViewerVideo) fxmlLoader.getController();
			controller.init(selectedItem, width, height);

			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setListViewDragAndDrop() {

		listView.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (db.hasFiles()) {
					event.acceptTransferModes(TransferMode.COPY);
				} else {
					event.consume();
				}
			}
		});

		listView.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard dragboard = event.getDragboard();
				boolean success = false;
				if (dragboard.hasFiles()) {
					success = true;
					for (File file : dragboard.getFiles()) {
						if (isImageOrVideo(file)) {
							listView.getItems().add(new MyFile(file.getAbsolutePath()));
						}
					}
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
	}

	private boolean isImageOrVideo(File file) {
		if (isImage(file) || isVideo(file))
			return true;
		else
			return false;
	}

	private boolean isImage(File file) {

		if (file != null) {

			String extension = FilenameUtils.getExtension(file.getAbsolutePath());

			if (extension.length() > 0) {

				extension = extension.toLowerCase();
				switch (extension) {
				case "jpg":
				case "jpeg":
				case "png":
				case "bmp":
					return true;
				default:
					return false;
				}
			}
		}
		return false;
	}

	private boolean isVideo(File file) {

		if (file != null) {

			String extension = FilenameUtils.getExtension(file.getAbsolutePath());

			if (extension.length() > 0) {

				extension = extension.toLowerCase();
				switch (extension) {
				case "mpg":
				case "avi":
				case "wmv":
				case "mp4":
				case "m4v":
					return true;
				default:
					return false;
				}
			}
		}
		return false;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStageViewer() {
		return stageViewer;
	}

	public void setStageViewer(Stage stageViewer) {
		this.stageViewer = stageViewer;
	}
}
