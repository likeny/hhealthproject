package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Food;

public class FoodDAO {
	public static ArrayList<Food> dbArrayList = new ArrayList<>();
	public static ArrayList<Food> foodList = new ArrayList<>();
	public static ArrayList<String> sbList = new ArrayList<>();
	
	// 1. 학생 등록하는 함수
	public static int insertFoodData(Food food) {
		int count = 0;

		// 1-1. 데이터베이스에 학생테이블에 입력하는 쿼리문
		StringBuffer insertUserInfo = new StringBuffer();
		insertUserInfo.append("insert into foodtbl ");
		insertUserInfo
				.append("(kind, kategorie, no, pkno, name, caloriePerGram, volume, totalCal) ");
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
			psmt.setString(1, food.getKind());
			psmt.setString(2, food.getKategorie());
			psmt.setInt(3, food.getNo());
			psmt.setInt(4, food.getPkno());
			psmt.setString(5, food.getName());
			psmt.setInt(6, food.getCaloriePerGram());
			psmt.setInt(7, food.getVolume());
			psmt.setInt(8, food.getTotalCal());

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

	public static ArrayList<Food> getFoodData() {
		String foodData = "select * from foodtbl order by kind";
		Connection con = null;

		PreparedStatement psmt = null;

		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(foodData);
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select에 문제가 있습니다.");
				return null;
			}
			while (rs.next()) {
				Food food = new Food(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
						rs.getInt(6), rs.getInt(7), rs.getInt(8));
				dbArrayList.add(food);
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

	public static int getOneOneNo(String what) {
		// 2-1. 데이터베이스 학생테이블에 있는 레코드를 모두 가져오는 쿼리문
		String selectCount = "select * from foodtbl where kategorie ="+what;
		sbList.clear();
		// 2-2. 데이터베이스 Connection을 가져와야 한다.
		Connection con = null;

		// 2-3. 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmt = null;

		ResultSet rs;
		// 2-4. 쿼리문을 싱행하고나서 가져와야할 레코드를 담고있는 보자기 객체
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectCount);
			rs=psmt.executeQuery();
			// 2-5. 실제데이터를 연결한 쿼리문을 실행한다.(번개를 치는것)
			// executeQuery(); 쿼리문을 실행해서 결과를 가져올때 사용하는 번개문
			if (rs == null) {
				MainController.callAlert("실패");
			}
			while(rs.next()) {
				sbList.add(rs.getString(1));
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
		return sbList.size();
	}

	// 3. 테이블뷰에서 선택한 레코드를 데이터베이스에서 삭제하는 함수
	public static int deleteFoodData(String what) {

		// 3-1. 데이터베이스 학생테이블에 있는 레코드를 삭제하는 쿼리문
		String deleteFood = "delete from foodtbl where pkno = '"+what+"'";

		// 3-2. 데이터베이스 Connection을 가져와야 한다.
		Connection con = null;

		// 3-3. 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmt = null;

		// 3-4. 쿼리문을 싱행하고나서 가져와야할 레코드를 담고있는 보자기 객체
		int count = 0;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(deleteFood);
			// 3-5. 실제데이터를 연결한 쿼리문을 실행한다.(번개를 치는것)
			// executeQuery(); 쿼리문을 실행해서 결과를 가져올때 사용하는 번개문
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
