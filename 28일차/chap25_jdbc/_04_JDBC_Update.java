package chap25_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class _04_JDBC_Update {

	public static void main(String[] args) {
		// 사용자가 입력한 sno에 해당하는 학생의 정보를
		// 사용자가 입력한 내용으로 JDBC를 이용해서 수정
		Connection conn = null;
		PreparedStatement stmt = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.println("변경하시고자 하는 학생의 학번을 입력하세요.");
			String sno = sc.nextLine();
			System.out.println("이름을 입력하세요.");
			String sname = sc.nextLine();
			System.out.println("성별을 입력하세요.");
			String gender = sc.nextLine();
			System.out.println("학년을 입력하세요.");
			int syear = sc.nextInt();
			sc.nextLine();
			System.out.println("전공을 입력하세요.");
			String major = sc.nextLine();
			System.out.println("평점을 입력하세요.");
			double avr = sc.nextDouble();
			
			conn = JDBCUtill.getConnection("jdbc:oracle:thin:@localhost:1521:xe", 
					"c##study", "!dkdlxl1234");
			
			String updateStudent = "UPDATE STUDENT"
					+ "	SET SNO = ?,"
					+ "		SNAME = ?,"
					+ "		SEX = ?,"
					+ "		SYEAR = ?,"
					+ "		MAJOR = ?,"
					+ "		AVR = ?"
					+ "		WHERE SNO = ?";
			
			stmt = conn.prepareStatement(updateStudent);
			
			stmt.setString(1, sno);
			stmt.setString(2, sname);
			stmt.setString(3, gender);
			stmt.setInt(4, syear);
			stmt.setString(5, major);
			stmt.setDouble(6, avr);
			stmt.setString(7, sno);
			
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
			
		}

	}

}
