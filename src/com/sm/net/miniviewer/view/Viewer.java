package com.sm.net.miniviewer.view;

import com.sm.net.miniviewer.model.MyFile;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Viewer {

	@FXML
	private ImageView imageView;

	@FXML
	private void initialize() {

	}

	public void init(MyFile selectedItem, double width, double height) {

		Image image = new Image(selectedItem.toURI().toString());

		imageView.setImage(image);
		imageView.setPreserveRatio(true);

		double widthImage = image.getWidth();
		double heightImage = image.getHeight();

		if (widthImage > heightImage) {

			double value = width * 100 / widthImage;
			double newHeight = value * heightImage / 100;

			imageView.setFitWidth(width);
			imageView.setFitHeight(newHeight);

		} else {

			double value = height * 100 / heightImage;
			double newWidth = value * widthImage / 100;

			imageView.setFitHeight(height);
			imageView.setFitWidth(newWidth);

		}

	}
}