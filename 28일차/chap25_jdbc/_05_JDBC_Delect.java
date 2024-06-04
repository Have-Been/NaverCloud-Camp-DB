package chap25_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class _05_JDBC_Delect {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.println("삭제하시고자 하는 학생의 학번을 입력하세요.");
			String sno = sc.nextLine();
			
			
			conn = JDBCUtill.getConnection("jdbc:oracle:thin:@localhost:1521:xe", 
					"c##study", "!dkdlxl1234");
			
			String updateStudent = "DELETE"
					+ " 				FROM STUDENT"
					+ "					WHERE SNO = ?";
			
			stmt = conn.prepareStatement(updateStudent);
			
			stmt.setString(1, sno);
			
			int result = stmt.executeUpdate();
			
			if(result != 0) {
				System.out.println("쿼리가 정상적으로 동작했습니다.");
			} else {
				System.out.println("영향받은 행이 존재하지 않습니다.");
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			JDBCUtill.close(stmt, conn);
		}
	}

}
