package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * [jdbc] emp 테이블의 모든 사원 정보 조회
 */
public class Ex01 {

	public static void main(String[] args) {
		
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * "
				+ " FROM emp";
		int empno, mgr, deptno;
		String ename, job;
		String hiredate;
		double comm;
		// Date hiredate;
		// LocalDateTime hiredate;
		
		try {
			// 1. JDBC 드라이버 로딩 - Class.forName
			Class.forName(className);
			// 2. Connection 객체 - DriverManager 
			conn = DriverManager.getConnection(url, user, password);
			// 3. CRUD 작업 - Statement 객체
			stmt = conn.createStatement();
			// stmt.executeUpdate(sql);  // insert, update, delete
			rs = stmt.executeQuery(sql);	  // select
			while (rs.next()) {
				empno = rs.getInt("empno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getString("hiredate");
				comm = rs.getDouble("comm");
				deptno = rs.getInt("deptno");
				System.out.printf("%d\t%s\t%s\t%d\t%s\t%f\t%d\n"
						, empno, ename, job, mgr, hiredate.substring(0, 10), comm, deptno);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
			try {
				// 4. Connection 객체 닫기 - close()
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
		
	}

}
