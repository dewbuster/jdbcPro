package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.util.DBConn;

/**
 * 트랜잭션
 *
 */
public class Ex02 {

	public static void main(String[] args) {
		// 
		String sql = "INSERT INTO dept VALUES (?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;		
		try {
			conn = DBConn.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,  50);
			pstmt.setString(2,  "QC");
			pstmt.setString(3, "SEOUL");
			
			rowCount = pstmt.executeUpdate();
			if (rowCount == 1) {
				System.out.println("첫 번째 부서 추가 성공!!");
			}
			
			pstmt.setInt(1,  50);
			pstmt.setString(2,  "QC");
			pstmt.setString(3, "SEOUL");
			rowCount = pstmt.executeUpdate();
			if (rowCount == 1) {
				System.out.println("두 번째 부서 추가 성공!!");
			}
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBConn.close();
		}
	}
}
