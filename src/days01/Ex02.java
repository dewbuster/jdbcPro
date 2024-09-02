package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ex02 {

	public static void main(String[] args) {
		
		// oracle.jdbc.driver.OracleDriver;
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			// 3. CRUD 작업
			String sql = "SELECT * FROM dept";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);  // select executeQuery , insert, update, delete executeUpdate
			int deptno;
			String dname, loc;
			while( rs.next() ) {
				deptno = rs.getInt("deptno");   // == rs.getInt(1)
				dname = rs.getString("dname");
				loc = rs.getString("loc");
				System.out.printf("%d\t%s\t%s\n", deptno, dname, loc);
			}
			
			rs.close();
			stmt.close();
			System.out.println(conn);
			if (conn != null) {
				conn.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
}
