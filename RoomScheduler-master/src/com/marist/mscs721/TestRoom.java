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
		Room newRoom = new Room("room1", 20,"Lowell Thomas",
		        "Marist Poughkeepsie Campus");
	}

	/*
	 * Getting the added room and capacity using the Assert
	 */
	@Test
	public void getRoomName() {
		Room newRoom = new Room("room1", 20,"Lowell Thomas",
		         "Marist Poughkeepsie Campus");
		String name = newRoom.getName();
		Assert.assertEquals(name, "room1");
		int capacity = newRoom.getCapacity();
		Assert.assertEquals(capacity, 20);
		String building=newRoom.getBuilding();
		Assert.assertEquals(building,"Lowell Thomas");
		String location=newRoom.getLocation();
		Assert.assertEquals(location,"Marist Poughkeepsie Campus");
		

	}

}
