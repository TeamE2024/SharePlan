package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logic {
	
	
	
	public String label() {
		String label = "<label>"+"Todo:"+" </label>"+"<input type=\"text\" name=\"list\">"+"<br>"+
	    "<label>"+"Limit:"+" </label>"+"<input type=\"date\" name=\"todo_day\">"+"<br>";
		return label;
	}
//	public String label() {
//		String label = "<label>"+"Todo:"+" </label>"+"<input type=\"text\" name=\"list\">"+"<br>"+
//	    "<label>"+"Limit:"+" </label>"+"<input type=\"datetime-local\" name=\"todo_day\">"+"<br>";
//		return label;
//	}
	public String labelLogic(String list,Date todo_day) {
		String labelHTML = "<label>"+"Todo: "+"</label>"+"<input type=\"text\" name=\"list\" value="+ list +">"+"<br>"+
				"<label>"+"Limit: "+"</label>"+"<input type=\"date\" name=\"todo_day\" value=" + todo_day +">"+"<br>";
		return labelHTML;
	}
//	public String labelLogic(String list,Date todo_day) {
//		String labelHTML = "<label>"+"Todo: "+"</label>"+"<input type=\"text\" name=\"list\" value="+ list +">"+"<br>"+
//				"<label>"+"Limit: "+"</label>"+"<input type=\"datetime-local\" name=\"todo_day\" value=" + todo_day +">"+"<br>";
//		return labelHTML;
//	}
//	public String labelLogic(String list,Timestamp todo_day) {
//		String labelHTML = "<label>"+"Todo: "+"</label>"+"<input type=\"text\" name=\"list\" value="+ list +">"+"<br>"+
//				"<label>"+"Limit: "+"</label>"+"<input type=\"datetime-local\" name=\"todo_day\" value=" + todo_day +">"+"<br>";
//		return labelHTML;
//	}
	
//	timestamp→String
	public String timestamp(Timestamp TimeStamp) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = sdf.format(timestamp);
        
        return formattedDate;
	}
	
//	String→timestamp
	public Timestamp timestamp(String timestamp) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(timestamp, formatter);

        Timestamp TimeStamp = Timestamp.valueOf(dateTime);
        
        return TimeStamp;
	}

}
