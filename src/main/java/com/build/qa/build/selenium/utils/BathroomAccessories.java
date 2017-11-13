package com.build.qa.build.selenium.utils;

public enum BathroomAccessories {

	BathroomFaucets("Bathroom Faucets"), BathroomSinks("Bathroom Sinks");

	private String name;

	private BathroomAccessories(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
