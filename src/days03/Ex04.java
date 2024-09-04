package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import com.util.DBConn;

public class Ex04 {

	public static void main(String[] args) {
		/*
	      [실행결과]
	    		  ACCOUNTING(3)
	    		    empno ename hiredate pay
	    		    empno ename hiredate pay
	    		    empno ename hiredate pay
	    		  RESEARCH(3)
	    		    empno ename hiredate pay
	    		    empno ename hiredate pay
	    		    empno ename hiredate pay
	    		  SALES(6)
	    		    empno ename hiredate pay
	    		    empno ename hiredate pay
	    		    empno ename hiredate pay
	    		    empno ename hiredate pay
	    		    empno ename hiredate pay
	    		    empno ename hiredate pay
	    		  OPERATIONS(1)
	    		    empno ename hiredate pay 
	    		  NULL(1)
	    		    empno ename hiredate pay
		*/
		String sql = "SELECT d.deptno, dname, count(empno) cnt "
				+ "FROM dept d FULL JOIN emp e ON d.deptno = e.deptno "
				+ "GROUP BY d.deptno, dname "
				+ "ORDER BY d.deptno ";
		String sql2 = "SELECT empno, ename, hiredate, sal+NVL(comm,0) pay "
				+ "FROM emp "
				+ "WHERE deptno ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs3 = null;
		
		int deptno;
		String dname;
		int count;
		int empno;
		String ename;
		LocalDateTime hiredate;
		int pay;

		conn = DBConn.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				deptno = rs.getInt("deptno");
				dname = rs.getString("dname");
				count = rs.getInt("cnt");
				System.out.printf("%s(%d)\n", dname,count);
				
				pstmt2 = conn.prepareStatement(sql2 + "=" + deptno);
				rs2 = pstmt2.executeQuery();
				while (rs2.next()) {
					empno = rs2.getInt("empno");
					ename = rs2.getString("ename");
					hiredate = rs2.getTimestamp("hiredate").toLocalDateTime();
					pay = rs2.getInt("pay");
					System.out.printf("%d\t%s\t%tF\t%d\n", empno, ename, hiredate, pay);
				}
			}
			pstmt3 = conn.prepareStatement(sql2 + "IS NULL");
			rs3 = pstmt3.executeQuery();
			while (rs3.next()) {
				empno = rs3.getInt("empno");
				ename = rs3.getString("ename");
				hiredate = rs3.getTimestamp("hiredate").toLocalDateTime();
				pay = rs3.getInt("pay");
				System.out.printf("%d\t%s\t%tF\t%d\n", empno, ename, hiredate, pay);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
