package model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MakeCalendar {

	private final int TODAY_YEAR;
	private final int TODAY_MONTH;
	
	public MakeCalendar() {
		LocalDate date = LocalDate.now();
		this.TODAY_YEAR = date.getYear();
		this.TODAY_MONTH= date.getMonthValue();
	}
	/*
	 * ローカル日付からカレンダーリストを生成する
	 */
	public List<LocalDate> getCalenderDays() {
		YearMonth yearMonth = YearMonth.of(TODAY_YEAR, TODAY_MONTH);
		int daysInMonth = yearMonth.lengthOfMonth();
		
		List<LocalDate> days = new ArrayList<>();
		
		for (int i = 1; i <= daysInMonth ; i ++) {
			days.add(LocalDate.of(TODAY_YEAR, TODAY_MONTH, i));
		}
		return days;
	}
	/*
	 * 月変更のパラメータから年月を取得し
	 * カレンダーリストを生成する
	 */
	public List<LocalDate> chenageCalender(String go) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(go, formatter);
        int year = localDate.getYear();
		int month = localDate.getMonthValue();
        
		YearMonth yearMonth = YearMonth.of(year, month);
		int daysInMonth = yearMonth.lengthOfMonth();
		
		List<LocalDate> days = new ArrayList<>();
		
		for (int i = 1; i <= daysInMonth ; i ++) {
			days.add(LocalDate.of(year, month, i));
		}
		return days;
	}

}
