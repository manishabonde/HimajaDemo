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

public class Meeting {

	private Timestamp startTime = null;
	private Timestamp stopTime = null;
	private String subject = null;

	public Meeting(Timestamp newStartTime, Timestamp newEndTime,
			String newSubject) {
		setStartTime(newStartTime);
		setStopTime(newEndTime);
		if (newSubject.isEmpty()) {
			setSubject("");
		} else {
			setSubject(newSubject);
		}
	}

	@Override
	public String toString() {
		return this.getStartTime().toString() + " - " + this.getStopTime()
				+ ": " + getSubject();
	}
	/*test comment */
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getStopTime() {
		return stopTime;
	}

	public void setStopTime(Timestamp stopTime) {
		this.stopTime = stopTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
