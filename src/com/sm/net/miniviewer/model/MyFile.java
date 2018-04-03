package com.sm.net.miniviewer.model;

import java.io.File;

public class MyFile extends File {

	private static final long serialVersionUID = 1L;

	public MyFile(String pathname) {
		super(pathname);
	}

	@Override
	public String toString() {
		return getName();
	}
}