package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
	LPROD 테이블에 새로운 데이터 추가하기
	
	lprod_gu와 lprod_nm은 직접 입력받아 처리하고, lprod_id는 현재의 lprod_id들 중 제일 큰 값보다 1 증가된 값으로 한다.
	(기타사항 : lprod_gu도 중복되는지 검사한다.)
 */
public class T04_JdbcTest {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521/xe";
			String userId = "JJE";
			String password = "java";
			
			conn = DriverManager.getConnection(url, userId, password);
			
			stmt = conn.createStatement();
			
			String sql = "select max(lprod_id) from lprod";
			rs = stmt.executeQuery(sql);
			int num = 0;
			if(rs.next()) {
				num = rs.getInt("maxNum");
			}
			num++;
					
			int count;
			String sql3 = "select count(*) cnt from lprod" + 
						  "where lprod_gu = ?";
			pstmt = conn.prepareStatement(sql3);
			String gu = null;
			do {
				System.out.println("lprod_gu의 값을 입력해주세요.");
				gu = scan.next();
				pstmt.setString(1, gu);
				
				rs = pstmt.executeQuery();
				count = 0;
				if(rs.next()) {
					count = rs.getInt("cnt");
				}
				if(count > 0) {
					System.out.println(gu + "은(는) 이미 있는 상품 번호입니다.");
					System.out.println("다시 입력하세요.");
					System.out.println();
				}
			}while(count > 0);
			
			
			System.out.println("lprod_nm의 값을 입력해주세요.");
			String nm = scan.next();
			
			scan.close();
			
			String sql2 = "insert into lprod (lprod_id, lprod_gu, lprod_nm) " + 
						  " values (?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql2);
			
			pstmt.setInt(1, num);
			pstmt.setString(2, gu);
			pstmt.setString(3, nm);
			
			int cnt = pstmt.executeUpdate();
			if(cnt > 0) {
				System.out.println(gu + "를 추가했습니다.");
			}else {
				System.out.println(gu + "를 추가하는데 실패했습니다.");
			}
			
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패!!!");
			e.printStackTrace();
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}finally {
			if(rs != null)
				try {
					rs.close();
				}catch(SQLException e) {}
			
			if(stmt != null)
				try {
					stmt.close();
				}catch(SQLException e) {}
			
			if(pstmt != null)
				try {
					pstmt.close();
				}catch(SQLException e) {}
			
			if(conn != null)
				try {
					conn.close();
				}catch(SQLException e) {}
		}
	}
}
