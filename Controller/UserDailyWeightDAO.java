package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.UserDailyWeight;

public class UserDailyWeightDAO {
	public static ArrayList<UserDailyWeight> dbArrayList = new ArrayList<>();
	public static ArrayList<UserDailyWeight> UserDailyWeightList = new ArrayList<>();
	public static ArrayList<String> sbList = new ArrayList<>();


	// 1. 칼로리 등록하는 함수
	public static int insertUserDailyWeightData(UserDailyWeight udw) {
		int count = 0;

		// 1-1. 데이터베이스에 학생테이블에 입력하는 쿼리문
		StringBuffer insertUserDailyWeight = new StringBuffer();
		insertUserDailyWeight.append("insert into userdailyweighttbl ");
		insertUserDailyWeight.append("values ");
		insertUserDailyWeight.append("(?,?,?) ");

		// 1-2. 데이터베이스 Connection을 가져와야 한다.
		Connection con = null;

		// 1-3. 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(insertUserDailyWeight.toString());
			// 1-4. 쿼리문에 실제 데이터를 연결한다.
			psmt.setString(1, udw.getUserid());
			psmt.setDate(2, udw.getDay());
			psmt.setInt(3, udw.getTodayWeight());
		
			// 1-5. 실제데이터를 연결한 쿼리문을 실행한다.
			// executeUpdate(); 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("삽입 쿼리실패 : 삽입 쿼리문에 문제가 있습니다.");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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

	public static int getUserDailyWeightData(String userid, String day) {
		String plusCal = "select todayweight from userdailyweighttbl where userid = '"+userid+"' AND day = '"+day+"'";
		int result = 0;
		Connection con = null;
		
		PreparedStatement psmt = null;

		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(plusCal);
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select에 문제가 있습니다.");
				return 0;
			}
			while (rs.next()) {
				result = rs.getInt(1);
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
		return result;
	}

	public static int getUserPlusCalData(String userid, String day) {
		String plusCal = "select sum(pluscal) from usercaltbl where userid = '"+userid+"' AND day = '"+day+"'";
		int result = 0;
		Connection con = null;
		
		PreparedStatement psmt = null;

		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(plusCal);
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select에 문제가 있습니다.");
				return 0;
			}
			while (rs.next()) {
				result = rs.getInt(1);
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
		return result;
	}
	
	public static int getUserMinusCalData(String userid, String day) {
		String minusCal = "select sum(minuscal) from usercaltbl where userid = '"+userid+"' AND day = '"+day+"'";
		int result = 0;
		Connection con = null;
		
		PreparedStatement psmt = null;

		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(minusCal);
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select에 문제가 있습니다.");
				return 0;
			}
			while (rs.next()) {
				result = rs.getInt(1);
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
		return result;
	}
	
	public static int getUserTotalCalData(String userid, String day) {
		String totalCal = "select sum(pluscal)-sum(minuscal) from usercaltbl where userid = '"+userid+"' AND day = '"+day+"'";
		int result = 0;
		Connection con = null;
		
		PreparedStatement psmt = null;

		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(totalCal);
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select에 문제가 있습니다.");
				return 0;
			}
			while (rs.next()) {
				result = rs.getInt(1);
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
		return result;
	}
	
}
