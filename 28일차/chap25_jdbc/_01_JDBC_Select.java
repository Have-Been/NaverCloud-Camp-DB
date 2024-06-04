package chap25_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _01_JDBC_Select {

	public static void main(String[] args) {
		// 1. 오라클 드라이버 변수 선언 및 사용 클래스를 문자열로 지정
		// OracleDriver 클래스 찾기
		final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
		
		// 접속할 데이터 베이스 url 지정
		// 데이터베이스마다 url 형식이 다르다.
		// 오라클: jdbc:oracle:thin:@호스트:포트:sid(버전)
		// 마이sql: jdbc:myslq://호스트ip:포트/스키마명
		final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
		
		// 3. 접속할 유저정보 지정
		final String USERNAME = "c##study";
		final String PASSWORD = "!dkdlxl1234";
		
		// 4. 필요한 객체들 변수로 선언
		// 커넥션 객체
		Connection conn = null;
		// 쿼리 실행 객체
		Statement stmt = null;
		// 쿼리 결과 객체
		ResultSet rs = null;
		
		try {
			// 5. 위에서 지정한 DataBase 드라이버 로드
			Class.forName(JDBC_DRIVER);
			
			// 6. DriverManager 클래스를 통해 DB 연결 객체 얻기
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			
			// 7. Connection 객체에서 쿼리를 실행할 수 있는 Statement 객체 얻기
			stmt = conn.createStatement();
			
			// 8. 실행시킬 쿼리를 문자열로 작성
			// 엔터값을 줬다면 무조건 스페이스로 어느정도 떼줘야함 FROM 앞에
			String selectStudentInfo = "SELECT ST.SNO"
					+ " 					, ST.SNAME"
					+ "						, ROUND(AVG(SC.RESULT), 2) AS AVG_RES"
					+ "					 	FROM STUDENT ST"
					+ "						JOIN SCORE SC"
					+ "						  ON ST.SNO = SC.SNO"
					+ "						GROUP BY ST.SNO, ST.SNAME";
			
			// 9. Statement 객체로 쿼리를 실행시키고 결과를 ResultSet에 담아준다.
			// SELECT => executeQuery 메소드 사용
			// INSERT, UPDATE, DELETE => executeUpdate 메소드 사용
			rs = stmt.executeQuery(selectStudentInfo);
			
			// 10. 쿼리 결과 출력
			while(rs.next()) {
				// 11. ResultSet에서 각각의 타입에 맞게 값을 꺼낸다.
				// 문자열은 getString 메소드, 정수형은 getInteger 메소드, 실수형은 getDouble 메소드
				// 안에 들어간 컬럼명은 소문자든 대문자든 상관없음. 단, 컬럼명하고 동일해야 함
				// 동일하지 않을 시 생기는 오류 : 열 이름이 부적합합니다. 
				String sno = rs.getString("sno");
				String sname = rs.getString("sname");
				double avgRes = rs.getDouble("avg_res");
				
				System.out.println("학번: " + sno + ", 이름: " + sname + ", 기말고사 평균점수: " + avgRes);
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// 12. 사용 완료된 Connection, Statement, ResultSet 객체 닫기
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					System.out.println(se.getMessage());
				}
			}
			
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException se) {
					System.out.println(se.getMessage());
				}
			}
			
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException se) {
					System.out.println(se.getMessage());
				}
			}
		}
	}
}
