package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import com.util.DBConn;

public class Ex07_03 {

	public static void main(String[] args) {
		// UP_INSERTDEPT
		// 새로운 부서 추가
		Scanner scanner = new Scanner(System.in);
		System.out.print("> 부서번호, 부서명, 지역명 입력하세요 ? ");
		int deptno = scanner.nextInt();
		String dname = scanner.next();
		String loc = scanner.next();
		String sql = "{CALL UP_INSERTDEPT(?, ?, ?)}"; 
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int rowCount = 0;
		conn =  DBConn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);    
			cstmt.setInt(1, deptno);
			cstmt.setString(2, dname);
			cstmt.setString(3, loc);
			rowCount = cstmt.executeUpdate();
			if (rowCount == 1) {
				System.out.println("부서 추가 성공!");
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
