package com.marist.mscs721;

import static org.junit.Assert.*;


import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.marist.mscs721.Room;

public class TestRoomScheduler {

	ArrayList<Room> roomList = new ArrayList<Room>();

	/*
	 * Adding the new room to the array list and validating it using
	 * assertEquals method
	 */

	@Test
	public void addRoomTest() {
		Room room1 = new Room("first room", 20);
		roomList.add(room1);
		Assert.assertEquals(room1.getName(), "first room");
	}

	/*
	 * Removing the added room based on
	 * 
	 * @method findRoomIndex()
	 */

	@Test
	public void removeRoomTest() {
		Room room1 = new Room("Third room", 30);
		roomList.add(room1);
		Assert.assertEquals(room1.getName(), "Third room");

		roomList.remove(RoomScheduler.findRoomIndex(roomList, "remove room"));
		Assert.assertEquals(roomList.size(), 0);
		System.out.println("Room was removed");

	}

	/*
	 * Generating the list of rooms which we have added
	 * 
	 * @method addRoomTest() and comparing the size of the room with 1.
	 */
	@Test
	public void listRoomsTest() {

		// addRoomTest();
		Room room1 = new Room("second room", 40);
		roomList.add(room1);

		Assert.assertEquals(room1.getName(), "second room");

		RoomScheduler.listRooms(roomList);
		Assert.assertEquals(roomList.size(), 1);
	}

	/*
	 * Getting the roomname using
	 * 
	 * @method listRoomTest() and comapring using asserEquals()
	 * 
	 * @Parameters getRoomName and "second Room"
	 */
	@Test
	public void getRoomFromNameTest() {
		listRoomsTest();
		String getRoomName = RoomScheduler.getRoomFromName(roomList,
				"getRoomName").getName();
		Assert.assertEquals(getRoomName, "second room");

	}

	/*
	 * Finding the room index which will help us to remove the room and to
	 * getFromRoomName() method
	 */

	@Test
	public void FindRoomIndexTest() {
		int Index = RoomScheduler.findRoomIndex(roomList, "first room");
		Assert.assertEquals(Index, 2);
	}

	/*
	 * Scheduling the rooming by validating the date and time using TimeStamp
	 * and assertEquals() method.
	 */
	@Test
	public void scheduleRoom() {
		addRoomTest();
		Room curRoom = RoomScheduler.getRoomFromName(roomList, "first room");
		Timestamp startTimestamp = Timestamp.valueOf("2015-08-08 10:10:00");
		Timestamp endTimestamp = Timestamp.valueOf("2012-02-04 11:23:00");
		Assert.assertEquals(startTimestamp, "2012-02-04 11:23:00");
		Assert.assertEquals(endTimestamp, "2015-08-08 10:10:00");
		String subject = "Testing";
		Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);
		Assert.assertEquals(subject, "test case");
		curRoom.addMeeting(meeting);
	}

	/*
	 * Exporting the room details to the JSON file using
	 * 
	 * @method size() to check whether any room added(existed) or not
	 */
	@Test
	public void exportRoomTest() {
		addRoomTest();
		RoomScheduler.saveRoomJSON(roomList);
		Assert.assertEquals(roomList.size(), 1);
	}

	/*
	 * Exporting the Schedule details to the JSON file using
	 * 
	 * @method size() to check whether any room added(existed) or not
	 */
	@Test
	public void exportScheduleTest() {

		addRoomTest();
		RoomScheduler.saveScheduleJSON(roomList);
		Assert.assertEquals(roomList.size(), 1);
	}

}
