package model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalTime;
/*
 * scheduleテーブルのJavaBeans
 */
public class ScheduleData implements Serializable{

	private int id;
	private String group_name;
	private String plan;
	private Date plan_day;
	private LocalTime plan_time;
	
	public ScheduleData() {}
	
	public ScheduleData(int id, String group_name, String plan, Date plan_day,LocalTime plan_time) {
		this.id = id;
		this.group_name = group_name;
		this.plan = plan;
		this.plan_day = plan_day;
		this.plan_time = plan_time;
		
	}


	public int getId() {
		return id;
	}


	public String getGroup_name() {
		return group_name;
	}


	public String getPlan() {
		return plan;
	}


	public Date getPlan_day() {
		return plan_day;
	}

	public LocalTime getPlan_time() {
		return plan_time;
	}
	
	
}
