package com.epam.engx.cleandesign.lod;

public class Task {

	private String name;
	private String id;
	private String userStoryName;

	public Task(String taskName) {
		String[] taskDetails = taskName.split(NameFormatter.DELIMITER);
		name = taskDetails[0];
		id = taskDetails[1];

		userStoryName = "Story" + NameFormatter.DELIMITER + id;
	}

	public UserStory getUserStory() {
		return new UserStory(userStoryName);
	}

	public String getAssignedEmployeeName(){
		return getUserStory().getAssigneeEmployeeName();
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
}
