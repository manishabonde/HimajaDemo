package com.marist.mscs721;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class TestRoom {
	public Room newRoom;

	/*
	 * Creating a new room using
	 * 
	 * @param name and capacity
	 */

	@Test
	public void addRoom() {
		Room newRoom = new Room("room1", 30);
	}

	/*
	 * Getting the added room and capacity using the Assert
	 */
	@Test
	public void getRoomName() {
		Room newRoom = new Room("room2", 20);
		String name = newRoom.getName();
		Assert.assertEquals(name, "room2");
		int capacity = newRoom.getCapacity();
		Assert.assertEquals(capacity, 20);

	}

}
