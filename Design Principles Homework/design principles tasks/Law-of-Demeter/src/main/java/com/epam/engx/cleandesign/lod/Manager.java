package com.epam.engx.cleandesign.lod;

public class Manager {

	public String whoIsAssignedTo(String taskName) {
		Task task = new Task(taskName);
		return task.getAssignedEmployeeName();
	}
}
