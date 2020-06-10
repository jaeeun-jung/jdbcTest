package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Select 예제
 */
public class T02_JdbcTest {
/*
	문제1) 사용자로부터 lprod_id값을 입력받아 입력한 값보다 lprod_id가 큰 자료를 출력하시오.
	
	문제2) lprod_id값을 2개 입력받아서 두 값 중 작은 값부터 큰 값 사이의 자료를 출력하시오.
 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
//		System.out.print("lprod_id의 값을 입력해주세요.");
//		int num = scan.nextInt();
		
		System.out.println("lprod_id의 값을 입력해주세요.");
		int num1 = scan.nextInt();
		System.out.println("lprod_id의 값을 입력해주세요.");
		int num2 = scan.nextInt();
		
		scan.close();
		
		int max, min;
		if(num1 > num2) {
			max = num1;
			min = num2;
		}else {
			max = num2;
			min = num1;
		}
		
		max = Math.max(num1, num2);
		min = Math.min(num1, num2);
		
		// DB작업에 필요한 객체변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {	// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. DB에 접속 (Connection객체 생성)
			String url = "jdbc:oracle:thin:@localhost:1521/xe";
			String userId = "JJE";
			String password = "java";
			
			conn = DriverManager.getConnection(url, userId, password);
			
			// 3. statement객체 생성
			stmt = conn.createStatement();
			
			// 4. SQL문을 실행하고 실행결과를 ResultSet에 저장
			String sql = "select * from lprod" + " where lprod_id >= " + min + " and lprod_id <=" + max;
			rs = stmt.executeQuery(sql);
			
			// 5. ResultSet객체에 저장되어 있는 자료를 반복문과 next()메서드로 읽어와 저장
			System.out.println("=== 쿼리문 실행 결과 ===");
			
			while(rs.next()) {
				System.out.println("lprod_id : " + rs.getInt("lprod_id"));
				System.out.println("lprod_gu : " + rs.getString("lprod_gu"));
				System.out.println("lprod_nm : " + rs.getString("lprod_nm"));
				System.out.println("---------------------------------");				
			}
			
			System.out.println("출력 완료.");
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			// 6. 종료(사용했던 자원을 모두 반납한다.)
			if(rs != null)
				try {
					rs.close();
				}catch(SQLException e) {}
			
			if(stmt != null)
				try {
					stmt.close();
				}catch(SQLException e) {}
			
			if(conn != null)
				try {
					conn.close();
				}catch(SQLException e) {}
		}
	
	}
	
}
