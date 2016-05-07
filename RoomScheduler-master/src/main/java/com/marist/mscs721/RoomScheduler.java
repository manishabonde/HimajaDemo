/**
 * Himaja Kethiri Software License (HKSL), 1.0
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES 
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT.
 * 
 */
/*

 * @author Himaja
 * @CWID 20062532
 * @program RoomScheduler adds,removes and schedules rooms and these details 
 * will be imported from or exported to JSON files
 */
package main.java.com.marist.mscs721;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
                logger.info(exportIntoJson(rooms));
                break;
            case 7:
                logger.info(importFromJson(rooms));
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
        System.out.println(" 7 - import Roomdetails");
        System.out.println("Enter your selection: ");

        return keyboard.nextInt();
    }

    /*
     * To add rooms and checking the capacity of the rooms if a room is not
     * available then print the unavailable message
     */
    public static String addRoom(ArrayList<Room> roomList) {
        System.out.println("Add a room:");
        String Room_name = getRoomName();
        System.out.println("Room capacity?");
        int Capacity = keyboard.nextInt();
        System.out.println("Room buliding?");
        String Building = keyboard.next();
        System.out.println("Room location?");
        String Location = keyboard.next();
        Room newRoom = new Room(Room_name, Capacity, Building, Location);
        roomList.add(newRoom);
        if (Capacity == 0)
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
        System.out.println("Room Name - Capacity-Building-Location");
        System.out.println("---------------------");

        for (Room room : roomList) {
            System.out.println(room.getName() + " - " + room.getCapacity()
                    + " - " + room.getBuilding() + " - " + room.getLocation());
        }

        logger.info("---------------------");

        return roomList.size() + " Room(s)";
    }

    protected static String availableRooms(ArrayList<Room> roomList,
            String start_date) {
        // ArrayList<Room> roomList = new ArrayList<Room>();
        String stringList = "";
        for (Room room : roomList) {
            System.out.println(room.getName());
            ArrayList<Meeting> meet = room.getMeetings();
            for (Meeting m : meet) {
                stringList += m + "\t";
            }
            System.out.println("RoomsList" + stringList);
        }
        return "";
    }

    /**
     * Schedule the rooms based on availability
     */

    protected static String scheduleRoom(ArrayList<Room> roomList) {
        if (roomList.size() > 0) {
            System.out.println("Schedule a room:");
            String name = getRoomName();
            // if (findRoomIndex(roomList, name) != -1) {
            System.out.println("Start Date? (yyyy-mm-dd):");
            String startDate = keyboard.next();
            System.out.println("Start Time?");
            String startTime = keyboard.next();
            startTime = startTime + ":00.0";
            String tStartDate = startDate + startTime;
            String avRooms = availableRooms(roomList, tStartDate);
            System.out.println("End Date? (yyyy-mm-dd):");
            String endDate = keyboard.next();
            System.out.println("End Time?");
            String endTime = keyboard.next();
            endTime = endTime + ":00.0";
            Timestamp startTimestamp = Timestamp.valueOf(startDate + " "
                    + startTime);
            Timestamp endTimestamp = Timestamp.valueOf(endDate + " " + endTime);

            System.out.println("Subject?");
            String subject = keyboard.next();
            Room curRoom = getRoomFromName(roomList, name);
            Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);

            // check wheather time is already exists for timestamp
            curRoom.addMeeting(meeting);
            return "Successfully scheduled meeting!";

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
    protected static String exportIntoJson(ArrayList<Room> roomList) {
       
        // Outer most Object containing an array of Rooms
        JSONObject Rooms = new JSONObject();
        // This is the array of Room Objects
        JSONArray roomArr = new JSONArray();
        for (int loop = 0; loop < roomList.size(); loop++) {
          Room rm = roomList.get(loop);
          // Contains the name, capacity, meetings of every room
          // This is added to the Array of Rooms
          JSONObject roomObj = new JSONObject();
          roomObj.put("name", rm.getName());
          roomObj.put("capacity", rm.getCapacity());
          roomObj.put("Building", rm.getBuilding());
          roomObj.put("Location", rm.getLocation());
          // This is the array containing the meetings for every room.
          JSONArray meetings = new JSONArray();
          // This contains details of one meeting : Start Time, Stop Time and
          // Subject
          JSONObject meetingObj = new JSONObject();
          ArrayList<Meeting> localMeetings = roomList.get(loop).getMeetings();
          for (int innerloop = 0; innerloop < localMeetings.size(); innerloop++) {
            Meeting lmeet = localMeetings.get(innerloop);
            meetingObj.put("startTime", lmeet.getStartTime().toString());
            meetingObj.put("stopTime", lmeet.getStopTime().toString());
            meetingObj.put("subject", lmeet.getSubject());
            meetings.add(meetingObj.clone());
          }
          roomObj.put("meetings", meetings);
          roomArr.add(roomObj.clone());
        }
        Rooms.put("rooms", roomArr);
        try {
                File file = new File("D://fromJSON.json");
          // Creating a JSON file at the specified location
          file.createNewFile();

          // Writes the contents into the file
          FileWriter fileWriter = new FileWriter(file);
          fileWriter.write(Rooms.toJSONString());
          fileWriter.write("\n");
          fileWriter.flush();
          fileWriter.close();
        } catch (IOException ioex) {
          logger.error(Level.SEVERE);
          System.out.println("Error while exporting JSON");
        }
       logger.info( "Successfully exported into JSON");
    return "Successfully exported into JSON";
    }
    /**
       * Imports rooms and their details from the JSON files
       *
       * @param roomList : Adds the imported rooms and their details into this array
       * @return : Success or error message to be displayed to the user
       */
      protected static String importFromJson(ArrayList<Room> roomList) {
        JSONParser parser = new JSONParser();
        try {
          File fileName = new File("D://fromJSON.json");
          BufferedReader br = new BufferedReader(new FileReader(fileName));
            String currLine;
          // Read the file till the end
          while ((currLine = br.readLine()) != null) {
            // Convert the read stream into object.
            Object obj = parser.parse(currLine);
            // Check if the file is empty
            if (obj.toString().isEmpty()) {
              return "File is empty";
            }
            Room room;
            Meeting meet;
            JSONObject Rooms = (JSONObject) obj;
            JSONArray roomArr = (JSONArray) Rooms.get("rooms");
            for (int loop = 0; loop < roomArr.size(); loop++) {
              JSONObject roomObj = (JSONObject) roomArr.get(loop);
              String Room_name = (String) roomObj.get("Room_name");
              Integer Capacity = (Integer) roomObj.get("Capacity");
              String Building = (String) roomObj.get("Building");
              String Location = (String) roomObj.get("Location");
              room = new Room(Room_name, Capacity, Building, Location);
              JSONArray meetings = (JSONArray) roomObj.get("meetings");
              for (int innerloop = 0; innerloop < meetings.size(); innerloop++) {
                JSONObject scheduleObj = (JSONObject) meetings.get(innerloop);
                meet = new Meeting(Timestamp.valueOf((String) scheduleObj.get("startTime")),
                  Timestamp.valueOf((String) scheduleObj.get("stopTime")),
                  (String) scheduleObj.get("subject"));
                room.addMeeting(meet);
              }
              roomList.add(room);
            }
          }
          br.close();
        } catch (FileNotFoundException e) {
          System.out.println("Your export failed. File Couldnt be found");
        } catch (IOException ioEx) {
          System.out.println("Exception caught : "+ ioEx.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Successfully imported!";
      }
}