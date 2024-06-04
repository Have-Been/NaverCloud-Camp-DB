package chap25_jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _02_JDBC_Select {

	public static void main(String[] args) {
		// JDBC를 이용해서 과목별 기말고사의 평균 성적 조회
		// (과목번호, 과목이름, 교수번호, 교수이름, 기말고사 성적의 평균점수)
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = JDBCUtill.getConnection("jdbc:oracle:thin:@localhost:1521:xe", 
					"c##study", "!dkdlxl1234");
			
			stmt = conn.createStatement();
			
			String info = "SELECT C.CNO"
					+ "			, C.CNAME"
					+ "			, P.PNO"
					+ "			, P.PNAME"
					+ "			, ROUND(AVG(SC.RESULT), 2) AS AVG_SCORE"
					+ "			FROM COURSE C"
					+ "			JOIN PROFESSOR P"
					+ "			  ON C.PNO = P.PNO"
					+ "			JOIN SCORE SC"
					+ "			  ON SC.CNO = C.CNO"
					+ "			GROUP BY C.CNO, C.CNAME, P.PNO, P.PNAME";
			
			rs = stmt.executeQuery(info);
			
			while(rs.next()) {
				String cno = rs.getString("cno");
				String cname = rs.getString("cname");
				String pno = rs.getString("pno");
				String pname = rs.getString("pname");
				double avgScore = rs.getDouble("avg_score");
				
				System.out.println("과목번호: " + cno + ", 과목이름: " + cname + 
						", 교수번호: " + pno + ", 교수이름: " + pname + ", 기말평균: " + avgScore
						);
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			JDBCUtill.close(stmt, rs, conn);
		}

	}

}
