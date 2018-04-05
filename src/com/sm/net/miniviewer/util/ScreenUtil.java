package com.sm.net.miniviewer.util;

public class ScreenUtil {

	public static double getRatioSize(double screenSize, double originalSize, double otherSide) {
		
		double ratio = screenSize * 100 / originalSize;
		return (otherSide * ratio / 100);
	}
}
