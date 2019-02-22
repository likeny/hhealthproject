package Model;

import java.sql.Date;

public class UserDailyWeight {
	private String userid;
	private Date day;
	private int todayWeight;
	
	public UserDailyWeight(String userid, Date day, int todayWeight) {
		this.userid = userid;
		this.day = day;
		this.todayWeight = todayWeight;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public int getTodayWeight() {
		return todayWeight;
	}

	public void setTodayWeight(int todayWeight) {
		this.todayWeight = todayWeight;
	}
	
}
