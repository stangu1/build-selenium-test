package com.build.qa.build.selenium.utils;

public enum Color {

	NickelTones("Nickel Tones"), Chromes("Chromes"), BronzeTones("Bronze Tones");

	private String name;

	private Color(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
