package com.marist.mscs721;


import org.apache.commons.io.FileUtils;










import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;










import org.json.*;

import au.com.bytecode.opencsv.CSVReader;


public class RoomScheduler {
 protected static Scanner keyboard = new Scanner(System.in);


 public static void main(String[] args) {
 Boolean end = false;
 ArrayList<Room> rooms = new ArrayList<Room>();
 ArrayList<Meeting> meetings = new ArrayList<Meeting>();



 while (!end) {
 switch (mainMenu()) {


 case 1:
 System.out.println(addRoom(rooms));
 break;
 case 2:
 System.out.println(removeRoom(rooms));
 break;
 case 3:
 System.out.print(scheduleRoom(rooms));
 break;
 case 4:
 System.out.println(listSchedule(rooms));
 break;
 case 5:
 System.out.println(listRooms(rooms));
 break;
 case 6:
	 System.out.println(saveRoomJSON(rooms));
	 break;
 case 7:
	 System.out.println(saveScheduleJSON(rooms));
	 break;
 case 8:
	 //here i changed room to meeting because in method it is given meeting
	 System.out.println(saveMeetingJSON(meetings));
	 break;
// case 9:
//	 System.out.println(importMeetingJSON(rooms));
//	 break;
// case 10:
//	 System.out.println(importScheduleJSON(rooms));
//	 break;
// case 11:
//	 System.out.println(importRoomJSON(rooms));
//	 break;
//	 
 }


 }


 }





protected static String listSchedule(ArrayList<Room> roomList) {
 String roomName = getRoomName();
 System.out.println(roomName + " Schedule");
 System.out.println("---------------------");
 
 for (Meeting m : getRoomFromName(roomList, roomName).getMeetings()) {
 System.out.println(m.toString());
 }


 return "";
 }


 protected static int mainMenu() {
 System.out.println("Main Menu:");
 System.out.println("  1 - Add a room");
 System.out.println("  2 - Remove a room");
 System.out.println("  3 - Schedule a room");
 System.out.println("  4 - List Schedule");
 System.out.println("  5 - List Rooms");
 System.out.println(" 6 - saveRoomJSON"); 
 System.out.println(" 7 - save schedule");
 System.out.println(" 8 - save meeting");
 System.out.println("Enter your selection: ");


 return keyboard.nextInt();
 }


 protected static String addRoom(ArrayList<Room> roomList) {
 System.out.println("Add a room:");
 String name = getRoomName();
 System.out.println("Room capacity?");
 int capacity = keyboard.nextInt();


 Room newRoom = new Room(name, capacity);
 roomList.add(newRoom);


 return "Room '" + newRoom.getName() + "' added successfully!";
 }


 protected static String removeRoom(ArrayList<Room> roomList) {
 System.out.println("Remove a room:");
 roomList.remove(findRoomIndex(roomList, getRoomName()));


 return "Room removed successfully!";
 }


 protected static String listRooms(ArrayList<Room> roomList) {
 System.out.println("Room Name - Capacity");
 System.out.println("---------------------");


 for (Room room : roomList) {
 System.out.println(room.getName() + " - " + room.getCapacity());
 }


 System.out.println("---------------------");


 return roomList.size() + " Room(s)";
 }


 protected static String scheduleRoom(ArrayList<Room> roomList) {
 System.out.println("Schedule a room:");
 String name = getRoomName();


 System.out.println("Start Date? (yyyy-mm-dd):");
 String startDate = keyboard.next();
 System.out.println("Start Time?");
 String startTime = keyboard.next();
 startTime = startTime + ":00.0";


 System.out.println("End Date? (yyyy-mm-dd):");
 String endDate = keyboard.next();
 System.out.println("End Time?");
 String endTime = keyboard.next();
 endTime = endTime + ":00.0";


 Timestamp startTimestamp = Timestamp.valueOf(startDate + " " + startTime);
 Timestamp endTimestamp = Timestamp.valueOf(endDate + " " + endTime);


 System.out.println("Subject?");
 String subject = keyboard.next();


 Room curRoom = getRoomFromName(roomList, name);


 Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);
 //check weather time is already exists for timestamp
 curRoom.addMeeting(meeting);


 return "Successfully scheduled meeting!";
 }


 protected static Room getRoomFromName(ArrayList<Room> roomList, String name) {
 return roomList.get(findRoomIndex(roomList, name));
 }


 protected static int findRoomIndex(ArrayList<Room> roomList, String roomName) {
 int roomIndex = 0;


 for (Room room : roomList) {
 if (room.getName().compareTo(roomName) == 0) {
 break;
 }
 roomIndex++;
 }


 return roomIndex;
 }


 protected static String getRoomName() {
 System.out.println("Room Name?");
 return keyboard.next();
 }
 protected static String saveToCsv() {
 System.out.println("Room Name?");
 return keyboard.next();
 }
 
 protected static String saveRoomJSON(ArrayList<Room> roomList ) {
     JSONArray jsonArray = new JSONArray();


     for (int i = 0; i < roomList.size(); i++)
     {
       JSONObject roomDetails = new JSONObject();
       roomDetails.put("room_capacity", roomList.get(i).getCapacity());
       //roomDetails.put("room_meetings", roomList.get(i).getMeetings());
       roomDetails.put("room_name", roomList.get(i).getName());
   
       jsonArray.put(roomDetails);
   
     }
     //check file exists or not 
     File file=new File("C://Users/Murali-Honey/Downloads/roomfromJSON.csv");
     String csv = CDL.toString(jsonArray);
     try {
 FileUtils.writeStringToFile(file, csv);
 } catch (IOException e) {
 e.printStackTrace();
 }
     return "it is priting this one";
   
 }
 
 protected static String saveScheduleJSON(ArrayList<Room> roomList )  {
     JSONArray jsonArray = new JSONArray();


     for (int i = 0; i < roomList.size(); i++)
     {
       JSONObject roomDetails = new JSONObject();
       for(int j=0;j<roomList.get(i).getMeetings().size();j++){
       roomDetails.put("room_capacity", roomList.get(i).getCapacity());
       roomDetails.put("room_meetings", roomList.get(i).getMeetings().get(j).getStartTime());
       roomDetails.put("room_meetings", roomList.get(i).getMeetings().get(j).getStopTime());
       roomDetails.put("room_meetings", roomList.get(i).getMeetings().get(j).getSubject());
       roomDetails.put("room_name", roomList.get(i).getName());
       }
       jsonArray.put(roomDetails);
   
     }
     //check file exists or not
     File file=new File("C://Users/Murali-Honey/Downloads/fromJSON.csv");
     String csv = CDL.toString(jsonArray);
     try {
 FileUtils.writeStringToFile(file, csv);
 } catch (IOException e) {
 e.printStackTrace();
 }
	return csv;
   
 }
 protected static String saveMeetingJSON(ArrayList<Meeting> meetings ) {
     JSONArray jsonArray = new JSONArray();


     for (int i = 0; i < meetings.size(); i++)
     {
       JSONObject meetingDetails = new JSONObject();
       meetingDetails.put("meeting_start", meetings.get(i).getStartTime());
       meetingDetails.put("meeting_stop", meetings.get(i).getStopTime());
       meetingDetails.put("meeting_subject", meetings.get(i).getSubject());
   
       jsonArray.put(meetingDetails);
   
     }
     //this will save as CSV file if your given path
     //check this file exists or not
     File file=new File("C://Users/Murali-Honey/Downloads/fromJSON.csv");
     String csv = CDL.toString(jsonArray);
     try {
 FileUtils.writeStringToFile(file, csv);
 } catch (IOException e) {
 // TODO Auto-generated catch block
 e.printStackTrace();
 }
	return "KK";  }
 
 protected static ArrayList<Meeting> importMeetingJSON(String filePath) throws Exception {
 //check for a valid file path
CSVReader readMeeting = new CSVReader(new FileReader(filePath));
 ArrayList<Meeting> meetings=new ArrayList<Meeting>();
 String [] nextLine;
 //ignoring first line
 readMeeting.readNext();
      while ((nextLine = readMeeting.readNext()) != null) {
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
     Meeting meeting = new Meeting(new java.sql.Timestamp(dateFormat.parse(nextLine[0]).getTime()), new java.sql.Timestamp(dateFormat.parse(nextLine[1]).getTime()),
     nextLine[2]); 
     meetings.add(meeting);
      }
     readMeeting.close();
     
 return meetings;
   
 }



 protected static ArrayList<Room> importScheduleJSON(String filePath) throws Exception {
 //check for a valid file path
CSVReader readMeeting = new CSVReader(new FileReader(filePath));
 ArrayList<Room> rooms=new ArrayList<Room>();
 String [] nextLine;
 //ignoring first line
 readMeeting.readNext();
      while ((nextLine = readMeeting.readNext()) != null) {
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
     Meeting meeting = new Meeting(new java.sql.Timestamp(dateFormat.parse(nextLine[1]).getTime()), 
     new java.sql.Timestamp(dateFormat.parse(nextLine[2]).getTime()),
     nextLine[3]); 
    // meetings.add(meeting);
     Room room=new Room(nextLine[1],Integer.parseInt(nextLine[0]));
     room.addMeeting(meeting);
     rooms.add(room);
     
      }
     readMeeting.close();
 return rooms;
   
 }


 protected static ArrayList<Room> importRoomJSON(String filePath) throws Exception {
 //check for a valid file path
 CSVReader readMeeting = new CSVReader(new FileReader(filePath));
 ArrayList<Room> roomList=new ArrayList<Room>();
 String [] nextLine;
 //ignoring first line
 readMeeting.readNext();
      while ((nextLine = readMeeting.readNext()) != null) {
     Room newRoom = new Room(nextLine[0], Integer.parseInt(nextLine[1]));
   roomList.add(newRoom);     }
     readMeeting.close();
 return roomList;
   
 }


 }