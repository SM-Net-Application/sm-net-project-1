package com.sm.net.miniviewer.view;

import com.sm.net.miniviewer.model.MyFile;
import com.sm.net.miniviewer.util.ScreenUtil;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class ViewerVideo {

	@FXML
	private MediaView mediaView;

	@FXML
	private void initialize() {

	}

	private double newWidth;
	private double newHeight;
	private int videoWidth;
	private int videoHeight;

	public void init(MyFile selectedItem, double screenWidth, double screenHeight) {

		this.newWidth = 0;
		this.newHeight = 0;

		Media media = new Media(selectedItem.toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		mediaView.setMediaPlayer(mediaPlayer);
		mediaView.setPreserveRatio(true);

		mediaPlayer.setOnReady(new Runnable() {

			@Override
			public void run() {
				videoWidth = media.getWidth();
				videoHeight = media.getHeight();

				setNewSize(videoWidth, videoHeight, screenWidth, screenHeight);

				if (newWidth > screenWidth || newHeight > screenHeight) {
					reverseSize(screenWidth, screenHeight);
				}

				mediaView.setFitWidth(newWidth);
				mediaView.setFitHeight(newHeight);
			}
		});
	}

	private void setNewSize(double width, double height, double screenWidth, double screenHeight) {
		if (width > height) {
			this.newWidth = screenWidth;
			this.newHeight = ScreenUtil.getRatioSize(screenWidth, width, height);

		} else {
			this.newWidth = ScreenUtil.getRatioSize(screenHeight, height, width);
			this.newHeight = screenHeight;
		}
	}

	private void reverseSize(double screenWidth, double screenHeight) {
		if (this.newWidth > this.newHeight) {
			this.newWidth = ScreenUtil.getRatioSize(screenHeight, this.newHeight, this.newWidth);
			this.newHeight = screenHeight;
		} else {
			this.newWidth = screenWidth;
			this.newHeight = ScreenUtil.getRatioSize(screenWidth, this.newWidth, this.newHeight);
		}
	}
}