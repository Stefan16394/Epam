package com.epam.engx.cleandesign.lod;

public class UserStory {

	private String id;
	private String assigneeId;

	public UserStory(String storyName) {
		String[] storyDetails = storyName.split(NameFormatter.DELIMITER);
		id = storyDetails[1];
		assigneeId = id;
	}

	public Employee getAssignedEmployee() {
		return new Employee(assigneeId);
	}

	public String getAssigneeEmployeeName(){
		return getAssignedEmployee().getName();
	}

	public String getId() {
		return id;
	}

	public String getAssigneeId() {
		return assigneeId;
	}
}
