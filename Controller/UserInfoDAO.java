package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.UserInfo;

public class UserInfoDAO {
	public static ArrayList<UserInfo> dbArrayList = new ArrayList<>();
	public static ArrayList<String> userLoginInfoList = new ArrayList<>();

	// 1. 학생 등록하는 함수
	public static int insertUserInfoData(UserInfo userInfo) {
		int count = 0;

		// 1-1. 데이터베이스에 학생테이블에 입력하는 쿼리문
		StringBuffer insertUserInfo = new StringBuffer();
		insertUserInfo.append("insert into userinfotbl ");
		insertUserInfo
				.append("(userID, password, goal, hopecontrolweight, gender, movelevel, currentweight, keepCalorie) ");
		insertUserInfo.append("values ");
		insertUserInfo.append("(?,?,?,?,?,?,?,?) ");

		// 1-2. 데이터베이스 Connection을 가져와야 한다.
		Connection con = null;

		// 1-3. 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(insertUserInfo.toString());
			// 1-4. 쿼리문에 실제 데이터를 연결한다.
			psmt.setString(1, userInfo.getUserID());
			psmt.setString(2, userInfo.getPassword());
			psmt.setInt(3, userInfo.getGoal());
			psmt.setInt(4, userInfo.getHopeControlWeight());
			psmt.setString(5, userInfo.getGender());
			psmt.setInt(6, userInfo.getMoveLevel());
			psmt.setInt(7, userInfo.getCurrentWeight());
			psmt.setInt(8, userInfo.getKeepCalorie());

			// 1-5. 실제데이터를 연결한 쿼리문을 실행한다.
			// executeUpdate(); 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("삽입 쿼리실패 : 삽입 쿼리문에 문제가 있습니다.");
				return count;
			}
		} catch (SQLException e) {
			MainController.callAlert("삽입 실패 : 데이터베이스 삽입에 문제가 있습니다.");
		} finally {
			// 1-6. 자원객체를 닫아야한다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				MainController.callAlert("자원 닫기 실패 : 자원 닫기에 문제가 있습니다.");
			}
		}
		return count;
	}

	// 2. UserInfo테이블에서 유저아이디와 패스워드를 가져오는 함수

	public static int getKeepCalorie(String userid) {
		String userId = "select keepCalorie from userinfotbl where userid = " + userid;
		Connection con = null;
		int keepCal = 0;
		PreparedStatement psmt = null;

		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(userId);
			psmt.setString(1, userid);
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select에 문제가 있습니다.");
				return 0;
			}
			while (rs.next()) {
				keepCal = rs.getInt(1);
			}
		} catch (SQLException e) {
		} finally {
			// 2-6. 자원객체를 닫아야한다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				MainController.callAlert("자원 닫기 실패 : 자원 닫기에 문제가 있습니다.");
			}
		}
		return keepCal;
	}

	public static String getUserID(String pass) {
		String userId = "select userid from userinfotbl where password = ? ";
		Connection con = null;
		String id = "";
		PreparedStatement psmt = null;

		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(userId);
			psmt.setString(2, pass);
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select에 문제가 있습니다.");
				return null;
			}
			while (rs.next()) {
				if (rs.getString(2) == pass) {
					id = rs.getString(1);
				}
			}
		} catch (SQLException e) {
		} finally {
			// 2-6. 자원객체를 닫아야한다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				MainController.callAlert("자원 닫기 실패 : 자원 닫기에 문제가 있습니다.");
			}
		}
		return id;
	}

	public static ArrayList<UserInfo> getUserInfoData() {
		String userId = "select * from userinfotbl ";
		Connection con = null;

		PreparedStatement psmt = null;

		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(userId);
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select에 문제가 있습니다.");
				return null;
			}
			while (rs.next()) {
				UserInfo userInfo = new UserInfo(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8));
				dbArrayList.add(userInfo);
			}
		} catch (SQLException e) {
		} finally {
			// 2-6. 자원객체를 닫아야한다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				MainController.callAlert("자원 닫기 실패 : 자원 닫기에 문제가 있습니다.");
			}
		}
		return dbArrayList;
	}

	// 0. 오늘의 체중 입력에서 수정한 레코드를 데이터베이스 테이블에 수정하는 함수.
	public static int updateUserCurrentWeightData(UserInfo ui) {		
		int count = 0;
		// 4-1. 데이터베이스에 학생테이블에 수정하는 쿼리문
		StringBuffer updateUserCurrentWeightData = new StringBuffer();
		updateUserCurrentWeightData.append("update userInfotbl set ");
		updateUserCurrentWeightData
				.append("currentweight=? where userid=? ");

		// 4-2. 데이터베이스 Connection을 가져와야 한다.
		Connection con = null;
		// 4-3. 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(updateUserCurrentWeightData.toString());
			// 4-4. 쿼리문에 실제 데이터를 연결한다.
			psmt.setInt(1, ui.getCurrentWeight());
			psmt.setString(2, ui.getUserID());
			// 4-5. 실제데이터를 연결한 쿼리문을 실행한다.
			// executeUpdate(); 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("update 쿼리실패 : update 쿼리문에 문제가 있습니다.");
				return count;
			}
		} catch (SQLException e) {
			MainController.callAlert("update 실패 : update 에 문제가 있습니다.");
		} finally {
			// 4-6. 자원객체를 닫아야한다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				MainController.callAlert("자원 닫기 실패 : 자원 닫기에 문제가 있습니다.");
			}
		}
		return count;
	}

	// 0. 목표 체중 변경에서 수정한 레코드를 데이터베이스 테이블에 수정하는 함수.
	public static int updateGoalData(UserInfo ui) {		
		int count = 0;
		// 4-1. 데이터베이스에 학생테이블에 수정하는 쿼리문
		StringBuffer updateGoal = new StringBuffer();
		updateGoal.append("update userInfotbl set ");
		updateGoal
				.append("goal=? where userid=? ");

		// 4-2. 데이터베이스 Connection을 가져와야 한다.
		Connection con = null;
		// 4-3. 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(updateGoal.toString());
			// 4-4. 쿼리문에 실제 데이터를 연결한다.
			psmt.setInt(1, ui.getGoal());
			psmt.setString(2, ui.getUserID());
			// 4-5. 실제데이터를 연결한 쿼리문을 실행한다.
			// executeUpdate(); 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("update 쿼리실패 : update 쿼리문에 문제가 있습니다.");
				return count;
			}
		} catch (SQLException e) {
			MainController.callAlert("update 실패 : update 에 문제가 있습니다.");
		} finally {
			// 4-6. 자원객체를 닫아야한다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				MainController.callAlert("자원 닫기 실패 : 자원 닫기에 문제가 있습니다.");
			}
		}
		return count;
	}
	
}
