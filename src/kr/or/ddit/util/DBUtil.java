package kr.or.ddit.util;
/**
 * JDBC드라이버를 로딩하고 Connection객체를 생성하는 메서드 제공
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 완료!!!");
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패!!!");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {	//객체 안만들고 불러다가 쓸 수 있음
		try {
			return DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"JJE",
					"java");
			
		}catch(SQLException e) {
			System.out.println("DB연결 실패!!!");
			e.printStackTrace();
			return null;
		}
	}
}
