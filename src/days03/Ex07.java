package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.util.DBConn;
import oracle.jdbc.internal.OracleTypes;

public class Ex07 {

	public static void main(String[] args) {

		String sql = "{ call up_selectdept(?) }";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		
		conn = DBConn.getConnection();
		ResultSet rs = null;
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(1);
			
			int deptno;
			String dname, loc;
			
			while(rs.next()) {
				deptno = rs.getInt("deptno");
				dname = rs.getString("dname");
				loc = rs.getString("loc");
				System.out.printf("%d\t%s\t%s\n", deptno, dname, loc);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		DBConn.close();
	}

}

/*
CREATE OR REPLACE PROCEDURE up_selectdept
(
    pdeptcursor OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN pdeptcursor FOR 
    SELECT *
    FROM dept;
END;
 */
