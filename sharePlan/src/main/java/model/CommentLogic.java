package model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import dao.ScheduleDAO;

public class CommentLogic {
	
	/*
	 * 予定追加メソッド
	 */
	public boolean input(String com,String date,String time,String group_name) {
		Date sqlDate = null;
		Time sqlTime = null;
		if(date != null && date.length() != 0) {
			sqlDate = Date.valueOf(date);
		} else {
			sqlDate = new Date(System.currentTimeMillis());
		}
		if (time != null && time.length() != 0) {
			sqlTime= Time.valueOf(time + ":00");
		} else {
			sqlTime = Time.valueOf("00:00:00");
		}
		
		ScheduleDAO dao = new ScheduleDAO();
		boolean conf = dao.input(com, sqlDate,sqlTime,group_name);
		return conf;
	}
	
	
	/*
	 * 予定表示メソッド
	 */
	public List<ScheduleData> outputSeparateDate(String date,String group_name){
		Date sqlDate = null;
		if (date == null || date.length() == 0) {
			LocalDate local = LocalDate.now();
			 sqlDate = Date.valueOf(local);
		}else {
			sqlDate= Date.valueOf(date);
		}

		ScheduleDAO dao = new ScheduleDAO();
		List<ScheduleData> allPlanList = dao.getAllPlan(group_name);
		List<ScheduleData> planList = new ArrayList<>();

		if (allPlanList != null) {
			for (ScheduleData plan : allPlanList) {
				if(plan.getPlan_day().equals(sqlDate)) {
					planList.add(plan);
				}
			}
			//時間のソート
			planList.sort(Comparator.comparing(ScheduleData::getPlan_time)); 

		}

		return planList;
	}
	
	/*
	 * 削除処理
	 */
	public boolean deleteData(List<ScheduleData> editPlanList) {
		int[] idArray = new int[editPlanList.size()]; 
		for(int i = 0; i < editPlanList.size(); i++) {
			idArray[i] = editPlanList.get(i).getId();
		}
		
		ScheduleDAO dao = new ScheduleDAO();
		return dao.deleteDAO(idArray);	
	}
	
	
	/*
	 * 新しい編集だけしたいList<ScheduleData>の作成
	 */
	public List<ScheduleData> readyToEdit(int[] selectedPlansInt, List<ScheduleData> planList) {
		
		List<ScheduleData> editPlanList = new ArrayList<>();
		
		for (int i = 0 ; i < selectedPlansInt.length; i++) {
			editPlanList.add(planList.get(selectedPlansInt[i]));
		}
		
		return editPlanList;
	}
	
	/*
	 * 編集登録処理
	 */
	public boolean editPlan(int id,String editCom,String editDay,String editTime) {
		Date sqlDate = null;
		Time sqlTime = null;
		ScheduleDAO dao = new ScheduleDAO();
		
		if(editDay != null && editDay.length() != 0) {
			sqlDate = Date.valueOf(editDay);
		} else {
			sqlDate = new Date(System.currentTimeMillis());
		}
		
		if (editTime != null && editTime.length() != 0) {
			sqlTime= Time.valueOf(editTime + ":00");
		} else {
			sqlTime = Time.valueOf("00:00:00");
		}

		return dao.updatePlan(id,editCom, sqlDate, sqlTime);
	}
}
