package Model;

public class UserInfo {
	private String userID;
	private String password;
	private int goal;
	private int hopeControlWeight;
	private String gender;
	private int moveLevel;
	private int currentWeight;
	private int keepCalorie;
	
	public UserInfo(String userID, String password, int goal, int hopeControlWeight, String gender, int moveLevel,
			int currentWeight, int keepCalorie) {
		
		this.userID = userID;
		this.password = password;
		this.goal = goal;
		this.hopeControlWeight = hopeControlWeight;
		this.gender = gender;
		this.moveLevel = moveLevel;
		this.currentWeight = currentWeight;
		this.keepCalorie = keepCalorie;
	}

	public UserInfo() {
		
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getHopeControlWeight() {
		return hopeControlWeight;
	}

	public void setHopeControlWeight(int hopeControlWeight) {
		this.hopeControlWeight = hopeControlWeight;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getMoveLevel() {
		return moveLevel;
	}

	public void setMoveLevel(int moveLevel) {
		this.moveLevel = moveLevel;
	}

	public int getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(int currentWeight) {
		this.currentWeight = currentWeight;
	}

	public int getKeepCalorie() {
		return keepCalorie;
	}

	public void setKeepCalorie(int keepCalorie) {
		this.keepCalorie = keepCalorie;
	}
	
}
