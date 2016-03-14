/*
 * @author Himaja
 * @CWID 20062532
 * @program RoomScheduler adds,removes and schedules rooms and these details 
 * will be imported from or exported to JSON files
 */
package com.marist.mscs721;

import org.apache.commons.io.FileUtils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import org.json.*;

import au.com.bytecode.opencsv.CSVReader;

public class RoomScheduler {

	protected static Scanner keyboard = new Scanner(System.in);
	private static final Logger logger = Logger
			.getLogger("RoomScheduler.class");

	// creating room scheduler based on room,meeting,scheduleroom methods
	public static void main(String[] args) throws Exception {
		Boolean end = false;
		ArrayList<Room> rooms = new ArrayList<Room>();
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		initializeLog();

		// To perform operations on RoomSchedular by selecting a case
		while (!end) {
			switch (mainMenu()) {
			case 1:
				logger.info(addRoom(rooms));
				break;
			case 2:
				logger.info(removeRoom(rooms));
				break;
			case 3:
				logger.info(scheduleRoom(rooms));
				break;
			case 4:
				logger.info(listSchedule(rooms));
				break;
			case 5:
				logger.info(listRooms(rooms));
				break;
			case 6:
				logger.info(saveRoomJSON(rooms));
				break;
			case 7:
				logger.info(saveScheduleJSON(rooms));
				break;
			case 8:
				// here i changed room to meeting because in method it is given
				// meeting
				logger.info(saveMeetingJSON(meetings));

				break;
			case 9:

				logger.info(importRoomJSON());

				break;
			case 10:
				logger.info(importScheduleJSON());
				break;
			case 11:
				logger.info(importMeetingJSON());
				break;

			default:
				logger.info("Invalid case selection");
				break;
			}

		}

	}

	private static void initializeLog() {
		Properties proper = new Properties();
		try {
			FileInputStream input = new FileInputStream(
					"configuration/log4j.properties");
			proper.load(input);
			PropertyConfigurator.configure(proper);
		} catch (FileNotFoundException e) {
			logger.info("Unable to locate the logging properties file");
		} catch (IOException e) {
			logger.error("Error while reading the logging file");
		}

	}

	/*
	 * To print the list of scheduled rooms
	 */
	protected static String listSchedule(ArrayList<Room> roomList) {
		String roomName = getRoomName();
		System.out.println(roomName + " Schedule");
		System.out.println("------------");

		for (Meeting m : getRoomFromName(roomList, roomName).getMeetings()) {
			System.out.println(m.toString());
		}

		return "";
	}

	/*
	 * To print the results based on selecting the case using console
	 */
	protected static int mainMenu() {
		System.out.println("Main Menu:");
		System.out.println("  1 - Add a room");
		System.out.println("  2 - Remove a room");
		System.out.println("  3 - Schedule a room");
		System.out.println("  4 - List Schedule");
		System.out.println("  5 - List Rooms");
		System.out.println(" 6 - save Roomdetails");
		System.out.println(" 7 - save schedule");
		System.out.println(" 8 - save meeting");
		System.out.println(" 9 - import Roomdetails");
		System.out.println(" 10 -import schedule");
		System.out.println(" 11 -import meeting");
		System.out.println("Enter your selection: ");

		return keyboard.nextInt();
	}

	/*
	 * To add rooms and checking the capacity of the rooms if a room is not
	 * available then print the unavailable message
	 */
	public static String addRoom(ArrayList<Room> roomList) {
		System.out.println("Add a room:");
		String name = getRoomName();
		System.out.println("Room capacity?");
		int capacity = keyboard.nextInt();
		Room newRoom = new Room(name, capacity);
		roomList.add(newRoom);
		if (capacity == 0)
			System.out.println("");
		return "Room '" + newRoom.getName() + "' added successfully!";

	}

	/*
	 * To remove the rooms from the room list which we have added using
	 * addRooms() method
	 */
	protected static String removeRoom(ArrayList<Room> roomList) {
		System.out.println("Remove a room:");

		int index = findRoomIndex(roomList, getRoomName());

		if (index != 0) {
			roomList.remove(index);
			return "Room removed successfully";
		} else {
			return "Room doesn't exist!";
		}
	}

	/*
	 * To print the list of rooms which we have added using addRooms() method
	 */
	protected static String listRooms(ArrayList<Room> roomList) {
		System.out.println("Room Name - Capacity");
		System.out.println("---------------------");

		for (Room room : roomList) {
			System.out.println(room.getName() + " - " + room.getCapacity());
		}

		logger.info("---------------------");

		return roomList.size() + " Room(s)";
	}

	/*
	 * Schedule the rooms based on availability
	 */

	protected static String scheduleRoom(ArrayList<Room> roomList) {
		if (roomList.size() > 0) {
			System.out.println("Schedule a room:");
			String name = getRoomName();
			if (findRoomIndex(roomList, name) != -1) {
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

				Timestamp startTimestamp = Timestamp.valueOf(startDate + " "
						+ startTime);
				Timestamp endTimestamp = Timestamp.valueOf(endDate + " "
						+ endTime);

				System.out.println("Subject?");
				String subject = keyboard.next();

				Room curRoom = getRoomFromName(roomList, name);

				Meeting meeting = new Meeting(startTimestamp, endTimestamp,
						subject);
				// check wheather time is already exists for timestamp
				curRoom.addMeeting(meeting);
				return "Successfully scheduled meeting!";
			} else {
				return "Room " + name + " is not available in the added rooms";
			}
		} else {
			return "No room is available for schedule";
		}
	}

	/*
	 * To get the room name by using getName() and comparing whether the room
	 * name is null or not
	 */
	protected static Room getRoomFromName(ArrayList<Room> roomList, String name) {
		return roomList.get(findRoomIndex(roomList, name));
	}

	protected static int findRoomIndex(ArrayList<Room> roomList, String roomName) {
		int roomIndex = 0;
		int finalIndex = 0;
		boolean exist = false;
		for (Room room : roomList) {
			if (room.getName().compareTo(roomName) == 0 && exist == true) {

				exist = true;

				roomIndex++;
			}

			if (!exist) {
				roomIndex++;
			}
		}
		if (exist) {
			finalIndex = roomIndex;
		}
		return finalIndex;
	}

	/*
	 * Takes input from the console and prints the room name
	 */
	protected static String getRoomName() {
		System.out.println("Room Name?");
		return keyboard.next();
	}

	/*
	 * Exporting the room details to the JSON files
	 */
	protected static String saveRoomJSON(ArrayList<Room> roomList) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < roomList.size(); i++) {
			JSONObject roomDetails = new JSONObject();
			roomDetails.put("room_capacity", roomList.get(i).getCapacity());
			roomDetails.put("room_name", roomList.get(i).getName());

			jsonArray.put(roomDetails);

		}
		// check file exists or not
		File file = new File("D://roomfromJSON.csv");
		String csv = CDL.toString(jsonArray);
		try {
			FileUtils.writeStringToFile(file, csv);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Can't export the room details");

		}
		return "Successfully exported the  room details";

	}

	/*
	 * Exporting the Scheduling room details to the JSON files
	 */
	protected static String saveScheduleJSON(ArrayList<Room> roomList) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < roomList.size(); i++) {
			JSONObject roomDetails = new JSONObject();
			for (int j = 0; j < roomList.get(i).getMeetings().size(); j++) {
				roomDetails.put("room_capacity", roomList.get(i).getCapacity());
				roomDetails.put("room_meetings", roomList.get(i).getMeetings()
						.get(j).getStartTime());
				roomDetails.put("room_meetings", roomList.get(i).getMeetings()
						.get(j).getStopTime());
				roomDetails.put("room_meetings", roomList.get(i).getMeetings()
						.get(j).getSubject());
				roomDetails.put("room_name", roomList.get(i).getName());
			}
			jsonArray.put(roomDetails);

		}
		// check file exists or not
		File file = new File("D://fromJSON.csv");
		String csv = CDL.toString(jsonArray);
		try {
			FileUtils.writeStringToFile(file, csv);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("can't export scheduling details to json ");
		}
		return csv;

	}

	/*
	 * Exporting the meeting details to JSON files
	 */
	protected static String saveMeetingJSON(ArrayList<Meeting> meetings) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < meetings.size(); i++) {
			JSONObject meetingDetails = new JSONObject();
			meetingDetails.put("meeting_start", meetings.get(i).getStartTime());
			meetingDetails.put("meeting_stop", meetings.get(i).getStopTime());
			meetingDetails.put("meeting_subject", meetings.get(i).getSubject());

			jsonArray.put(meetingDetails);

		}
		// this will save as CSV file if your given path
		// check this file exists or not
		File file = new File("D://fromJSON.csv");
		String csv = CDL.toString(jsonArray);
		try {
			FileUtils.writeStringToFile(file, csv);
		} catch (IOException e) {
			logger.error("Can't export meeting details to json file");

			e.printStackTrace();
		}
		return "exported data successfully";
	}

	/*
	 * Importing the meeting details from JSON files
	 */
	protected static String importMeetingJSON() throws Exception {
		// check for a valid file path
		CSVReader readMeeting = new CSVReader(
				new FileReader("D://fromJSON.csv"));
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		String[] nextLine;
		// ignoring first line
		readMeeting.readNext();
		while ((nextLine = readMeeting.readNext()) != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss.SSS");
			Meeting meeting = new Meeting(new java.sql.Timestamp(dateFormat
					.parse(nextLine[0]).getTime()), new java.sql.Timestamp(
					dateFormat.parse(nextLine[1]).getTime()), nextLine[2]);
			meetings.add(meeting);
		}
		readMeeting.close();
		logger.info("Meetings data imported from json files");

		return "";

	}

	/*
	 * Importing the scheduling details from JSON files
	 */

	protected static String importScheduleJSON() throws Exception {
		// check for a valid file path
		CSVReader readMeeting = new CSVReader(
				new FileReader("D://fromJSON.csv"));
		ArrayList<Room> rooms = new ArrayList<Room>();
		String[] nextLine;
		// ignoring first line
		readMeeting.readNext();
		while ((nextLine = readMeeting.readNext()) != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss.SSS");
			Meeting meeting = new Meeting(new java.sql.Timestamp(dateFormat
					.parse(nextLine[1]).getTime()), new java.sql.Timestamp(
					dateFormat.parse(nextLine[2]).getTime()), nextLine[3]);
			// meetings.add(meeting);
			Room room = new Room(nextLine[1], Integer.parseInt(nextLine[0]));
			room.addMeeting(meeting);
			rooms.add(room);

		}
		readMeeting.close();
		logger.info("Scheduling details imported from json");
		return "";

	}

	/*
	 * Importing the room details from JSON files
	 */
	protected static String importRoomJSON() throws Exception {
		// check for a valid file path
		CSVReader readMeeting = new CSVReader(new FileReader(
				"D://roomfromJSON.csv"));
		ArrayList<Room> roomList = new ArrayList<Room>();
		String[] nextLine;
		// ignoring first line
		readMeeting.readNext();
		while ((nextLine = readMeeting.readNext()) != null) {
			Room newRoom = new Room(nextLine[0], Integer.parseInt(nextLine[1]));
			roomList.add(newRoom);
		}
		readMeeting.close();
		return "";

	}

}