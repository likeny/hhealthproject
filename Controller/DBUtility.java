package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {

	public static Connection getConnection() {
		Connection con = null;
		try {
			// 1. MySql database class 로드한다.
			Class.forName("com.mysql.jdbc.Driver");
			// 2. 주소, ID, 비밀번호를 통해서 접속 요청한다.
			con = DriverManager.getConnection("jdbc:mysql://localhost/hhealthdb", "root", "123456");
		} catch (Exception e) {
			MainController.callAlert("연결 실패 : 데이터베이스 연결에 문제가 있습니다.");
			return null;
		}
		return con;
	}

}
