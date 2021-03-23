package br.com.timesheetio.appointment.enums;

public enum PointStatusEnum {

	ENABLED("Enabled"),
	PENDING("Pending"),
	DISABLED("Disabled");
	
	String description;
	
	PointStatusEnum(String description){
		
		this.description = description;
	}
}
