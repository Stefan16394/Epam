package com.epam.engx.cleandesign.lod;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ManagerTest {
	private Manager manager = new Manager();

	@Test
	public void GivenTask1_WhenAskWhoIsAssignedToTask_ThenReturnName1() {

		assertEquals("Name-1", manager.whoIsAssignedTo("Task-1"));
	}

	@Test
	public void GivenTask2_WhenAskWhoIsAssignedToTask_ThenReturnName2() {

		assertEquals("Name-2", manager.whoIsAssignedTo("Task-2"));
	}
}
