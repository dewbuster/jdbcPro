package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.doit.domain.DeptVO;
import org.doit.domain.EmpVO;

import com.util.DBConn;

/**
 * org.doit.domain.DeptVO.java
 *	1. SELECT * FROM dept 쿼리실행 ArrayList<DeptVO> deptList
 *	2. 부서정보 출력
 *	3. 부서를 선택하세요 ? 20
 *	4. SELECT * FROM emp WHERE deptno = 20;
 *	5. ArrayList<EmpVO> empList 저장
 *	6. 해당 사원의 정보를 출력
 */
public class Ex02 {

	public static void main(String[] args) {
		String sql = "SELECT * FROM dept";
		ArrayList<DeptVO> deptList = null;
		ArrayList<EmpVO> empList = new ArrayList<EmpVO>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//DeptVO
		int deptno;
		String dname, loc;
		//EmpVO
		int empno;
		String ename; 
		String job;
		int mgr;
		LocalDateTime hiredate;
		double sal;
		double comm;
		
		EmpVO evo = null;
		DeptVO dvo = null;
		
		conn = DBConn.getConnection();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				deptList = new ArrayList<DeptVO>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");
					
					dvo = new DeptVO(deptno, dname, loc, 0);
					// @Builder
					dvo = new DeptVO().builder()
							.deptno(deptno)
							.dname(dname)
							.loc(loc)
							.build();
					deptList.add(dvo);
				} while (rs.next());
				
				deptList.forEach(vo -> {System.out.println(vo);});
				
				
			}
			while (rs.next()) {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Scanner scanner = new Scanner(System.in);
		System.out.print("> 부서번호를 입력하세요 ? ");
		String strDeptno = scanner.nextLine();
		sql = "SELECT * FROM emp "
				+ "WHERE deptno IN ("+ strDeptno + ")";
		//
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	  // select
			while (rs.next()) {
				empno = rs.getInt("empno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getTimestamp("hiredate").toLocalDateTime();
				//Instant.ofEpochMilli(rs.getDate("hiredate").getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm");
				deptno = rs.getInt("deptno");
				
				evo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
				empList.add(evo);

			}
			Ex01_04.dispEmp(empList);
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
		//
		DBConn.close();
	} // main

} // class
