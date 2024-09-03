package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.doit.domain.EmpVO;

import com.util.DBConn;

/**
 * [jdbc] emp 테이블의 모든 사원 정보 조회
 * org.doit.domain 패키지
 * 		ㄴ EmpVO.java ( Value Object )
 * 		ArrayList<EmpVO> list
 * 		dispEmp() 출력함수
 * 		com.util 패키지
 * 			ㄴ DBConn.java
 * 					ㄴ Connection getConnection() 메서드 구현
 * 					ㄴ Connection getConnection() 메서드 구현
 * 					ㄴ close() 메서드 구현
 * 
 */
public class Ex01_04 {

	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * "
				+ " FROM emp";
		int empno, mgr, deptno;
		String ename, job;
		//String hiredate;
		double comm;
		double sal;
		//Date hiredate;
		LocalDateTime hiredate;
		
		ArrayList<EmpVO> list = new ArrayList<>();
		EmpVO vo;
		
		try {
			conn = DBConn.getConnection();
			// 3. CRUD 작업 - Statement 객체
			stmt = conn.createStatement();
			// stmt.executeUpdate(sql);  // insert, update, delete
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
				
				vo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
				list.add(vo);

			}
			dispEmp(list);
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
	public static void dispEmp(ArrayList<EmpVO> list) {
			if (list.size() == 0) {
				System.out.println("사원이 존재하지 않습니다.");
				return;
			}
			// 사원 정보 출력
			// 2.
			list.forEach(vo->{
				System.out.printf("%d\t%s\t%s\t%d\t%tF\t%f\t%f\t%d\n"
						, vo.getEmpno(), vo.getEname(), vo.getJob(), vo.getMgr()
						, vo.getHiredate(), vo.getSal(), vo.getComm(), vo.getDeptno());
			});
			/* 1.
			Iterator<EmpVO> ir = list.iterator();
			while (ir.hasNext()) {
				EmpVO vo = ir.next();
				//System.out.println(vo.toString());
				System.out.printf("%d\t%s\t%s\t%d\t%tF\t%f\t%f\t%d\n"
						, vo.getEmpno(), vo.getEname(), vo.getJob(), vo.getMgr()
						, vo.getHiredate(), vo.getSal(), vo.getComm(), vo.getDeptno());
			}
			*/
	}
}