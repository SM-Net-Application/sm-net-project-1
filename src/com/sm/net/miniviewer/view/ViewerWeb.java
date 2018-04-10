package com.sm.net.miniviewer.view;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ViewerWeb {

	@FXML
	private WebView webView;

	@FXML
	private void initialize() {

	}

	private MainView mainViewController;

	public void init(double screenWidth, double screenHeight, String url) {

		webView.setMinWidth(screenWidth);
		webView.setMinHeight(screenHeight);

		WebEngine webEngine = webView.getEngine();
		webEngine.load(url);
	}

	public MainView getMainViewController() {
		return mainViewController;
	}

	public void setMainViewController(MainView mainViewController) {
		this.mainViewController = mainViewController;
	}

	public WebView getWebView() {
		return webView;
	}

	public void setWebView(WebView webView) {
		this.webView = webView;
	}
}
