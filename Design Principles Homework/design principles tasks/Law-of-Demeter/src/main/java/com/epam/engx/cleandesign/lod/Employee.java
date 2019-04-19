package com.epam.engx.cleandesign.lod;

public class Employee {
	private String id;
	private String name;

	public Employee(String id) {
		this.id = id;
		this.name = "Name" + NameFormatter.DELIMITER + this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getId() {
		return id;
	}
}
