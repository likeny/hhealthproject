package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Exercise;
import Model.Food;

public class ExerciseDAO {
	public static ArrayList<Exercise> dbArrayList = new ArrayList<>();
	public static ArrayList<Food> foodList = new ArrayList<>();
	public static ArrayList<String> sbList = new ArrayList<>();
	
	// 1. 학생 등록하는 함수
	public static int insertExerciseData(Exercise ec) {
		int count = 0;

		// 1-1. 데이터베이스에 학생테이블에 입력하는 쿼리문
		StringBuffer insertUserInfo = new StringBuffer();
		insertUserInfo.append("insert into exercisetbl ");
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
			psmt.setString(1, ec.getKind());
			psmt.setInt(2, ec.getNo());
			psmt.setInt(3, ec.getPkno());
			psmt.setString(4, ec.getName());
			psmt.setInt(5, ec.getFirecal());
			psmt.setInt(6, ec.getTime());
			psmt.setInt(7, ec.getTotalcal());
			psmt.setString(8, ec.getImages());

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

	// 운동 DB가져오기
	public static ArrayList<Exercise> getExerciseData() {
		String exerData = "select * from exercisetbl order by kind";
		Connection con = null;

		PreparedStatement psmt = null;

		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(exerData);
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select에 문제가 있습니다.");
				return null;
			}
			while (rs.next()) {
				Exercise exer = new Exercise(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5),
						rs.getInt(6), rs.getInt(7), rs.getString(8));
				dbArrayList.add(exer);
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

	// 운동 DB에서 항목이 what인 애들의 갯수 뽑기
	public static int getExerNo(String what) {
		String selectCount = "select * from foodtbl where kind =" + what;
		sbList.clear();
		Connection con = null;
		PreparedStatement psmt = null;

		ResultSet rs;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectCount);
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("실패");
			}
			while (rs.next()) {
				sbList.add(rs.getString(1));
			}
		} catch (SQLException e) {
			MainController.callAlert("삽입 실패 : 데이터베이스 삽입에 문제가 있습니다.");
		} finally {
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
		return sbList.size();
	}

	// 3. 테이블뷰에서 선택한 레코드를 데이터베이스에서 삭제하는 함수
	public static int deleteExerData(String what) {

		String deleteExer = "delete from exercisetbl where pkno = '" + what + "'";

		Connection con = null;

		PreparedStatement psmt = null;

		int count = 0;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(deleteExer);
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("delete 실패 : delete에 문제가 있습니다.");
				return count;
			}

		} catch (SQLException e) {
			MainController.callAlert("delete 실패 : delete에 문제가 있습니다.");
		} finally {
			// 3-6. 자원객체를 닫아야한다.
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
