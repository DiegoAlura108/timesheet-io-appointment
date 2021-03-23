package br.com.timesheetio.appointment.enums;

public enum PointTypeEnum {

	START_EXP("Start Hours"),
	EXIT_EXP("Exit Hours"),
	START_LUNCH("Start Lunch"),
	EXIT_LUNCH("Exit Lunch");
	
	String description;
	
	PointTypeEnum(String description){
		
		this.description = description;
	}
}
