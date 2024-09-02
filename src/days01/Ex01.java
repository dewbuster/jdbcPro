package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ex01 {

	public static void main(String[] args) {
		
		// oracle.jdbc.driver.OracleDriver;
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
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
