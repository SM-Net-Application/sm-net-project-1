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

	private double newWidth;
	private double newHeight;

	public void init(MyFile selectedItem, double screenWidth, double screenHeight) {

		this.newWidth = 0;
		this.newHeight = 0;

		Image image = new Image(selectedItem.toURI().toString());

		imageView.setImage(image);
		imageView.setPreserveRatio(true);

		double imageWidth = image.getWidth();
		double imageHeight = image.getHeight();

		setNewSize(imageWidth, imageHeight, screenWidth, screenHeight);

		if (this.newWidth > screenWidth || this.newHeight > screenHeight) {
			reverseSize(screenWidth, screenHeight);
		}

		imageView.setFitWidth(this.newWidth);
		imageView.setFitHeight(this.newHeight);
	}

	private void setNewSize(double width, double height, double screenWidth, double screenHeight) {
		if (width > height) {
			this.newWidth = screenWidth;
			this.newHeight = getRatioSize(screenWidth, width, height);

		} else {
			this.newWidth = getRatioSize(screenHeight, height, width);
			this.newHeight = screenHeight;
		}
	}

	private void reverseSize(double screenWidth, double screenHeight) {
		if (this.newWidth > this.newHeight) {
			this.newWidth = getRatioSize(screenHeight, this.newHeight, this.newWidth);
			this.newHeight = screenHeight;
		} else {
			this.newWidth = screenWidth;
			this.newHeight = getRatioSize(screenWidth, this.newWidth, this.newHeight);
		}
	}

	private double getRatioSize(double screenSize, double imageOriginalSize, double imageOtherSide) {
		double ratio = screenSize * 100 / imageOriginalSize;
		return (imageOtherSide * ratio / 100);
	}
}