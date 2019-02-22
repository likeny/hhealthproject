package Model;

import java.sql.Date;

public class UserCal {
	private String userid;
	private Date day;
	private String foodname;
	private String plusCal;
	private String exername;
	private String minusCal;

	public UserCal(Date day, String foodname, String plusCal) {
		this.day = day;
		this.foodname = foodname;
		this.exername = foodname;
		this.minusCal = plusCal;
		this.plusCal = plusCal;
	}

	public UserCal(String userid, Date day, String foodname, String plusCal, String exername, String minusCal) {
		this.userid = userid;
		this.day = day;
		this.foodname = foodname;
		this.plusCal = plusCal;
		this.exername = exername;
		this.minusCal = minusCal;
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

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public String getPlusCal() {
		return plusCal;
	}

	public void setPlusCal(String plusCal) {
		this.plusCal = plusCal;
	}

	public String getExername() {
		return exername;
	}

	public void setExername(String exername) {
		this.exername = exername;
	}

	public String getMinusCal() {
		return minusCal;
	}

	public void setMinusCal(String minusCal) {
		this.minusCal = minusCal;
	}

}
