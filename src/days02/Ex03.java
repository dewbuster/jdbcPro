package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.doit.domain.EmpDeptSalGradeVO;

import com.util.DBConn;

public class Ex03 {

	public static void main(String[] args) {
		
		// 사원번호, 사원명, 부서명, 입사일자, pay, 등급  조회
		// 조인 : emp, dept, salgrade
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT empno, ename, dname, hiredate , sal+NVL(comm,0) pay, grade "
				+ "FROM emp e JOIN dept d ON e.deptno = d.deptno "
				+ "           JOIN salgrade s ON e.sal BETWEEN s.losal AND s.hisal";
		int empno;
		String ename;
		LocalDateTime hiredate;
		double pay;
		String dname;
		int grade;
		
		ArrayList<EmpDeptSalGradeVO> list = new ArrayList<>();
		EmpDeptSalGradeVO vo = null;
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	  // select
			while (rs.next()) {
				empno = rs.getInt("empno");
				ename = rs.getString("ename");
				dname = rs.getString("dname");
				hiredate = rs.getTimestamp("hiredate").toLocalDateTime();
				pay = rs.getDouble("pay");
				grade = rs.getInt("grade");
				
				vo = new EmpDeptSalGradeVO(empno, ename, hiredate, pay, dname, grade);
				list.add(vo);
			}
			list.forEach(evo -> System.out.println(evo));
			
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
			try {
				// 4. Connection 객체 닫기 - close()
				rs.close();
				stmt.close();
				// conn.close();  싱글톤 x
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
