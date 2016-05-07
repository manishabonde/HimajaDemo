/**
 * Himaja Kethiri Software License (HKSL), 1.0
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES 
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT.
 * 
 */
package main.java.com.marist.mscs721;

import java.sql.Timestamp;

import java.util.ArrayList;

import main.java.com.marist.mscs721.Room;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class TestRoomScheduler {

    ArrayList<Room> roomList = new ArrayList<Room>();

    /*
     * Adding the new room to the array list and validating it using
     * assertEquals method
     */

    @Test
    public void addRoomTest() {
        Room room1 = new Room("first room", 20, "Lowell Thomas",
                "Marist Poughkeepsie Campus");
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
        Room room1 = new Room("Third room", 30, "bulding 2", "new location");
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

        addRoomTest();
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
        Assert.assertEquals(getRoomName, "first room");

    }

    /*
     * Finding the room index which will help us to remove the room and to
     * getFromRoomName() method
     */

    @Test
    public void FindRoomIndexTest() {
        int Index = RoomScheduler.findRoomIndex(roomList, "first room");
        Assert.assertEquals(Index, 0);
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
        Assert.assertNotEquals(startTimestamp, "2012-02-04 11:23:00");
        Assert.assertNotEquals(endTimestamp, "2015-08-08 10:10:00");
        String subject = "Testing";
        Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);
        Assert.assertNotEquals(subject, "test case");
        curRoom.addMeeting(meeting);
    }
    /*
     * Exporting the room details to the JSON file using
     * 
     * @method size() to check whether any room added(existed) or not
     */
    @Ignore
    @Test
    public void exportRoomTest() {
       addRoomTest();
       RoomScheduler.exportIntoJson(roomList);
        Assert.assertEquals(roomList.size(), 1);
    }
}
