package com.build.qa.build.selenium.utils;

public enum Theme {

	Modern("Modern"), Traditional("Traditional"), Transitional("Transitional");

	private String name;

	private Theme(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
