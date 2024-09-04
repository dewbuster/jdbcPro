package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

import oracle.jdbc.internal.OracleTypes;

public class Ex05 {

	public static void main(String[] args) {
		// [저장 프로시저]   - 입력받은 ID를 사용 여부 체크하는 프로시저
	      //       ㄴ 회원가입
	      //             아이디 : [   hong   ] <ID중복체크버튼>
	      //             비밀번호
	      //             이메일
	      //             주소
	      //             연락처
	      //             등등
		Scanner scanner = new Scanner(System.in);
		System.out.print("> 중복 체크할 ID 입력 ? ");
		int id = scanner.nextInt(); // 7369
		
		// UP_IDCHECK 프로시저 cstmt 사용해서 처리
		//String sql = "{ call up_idcheck(?,?) }";
		String sql = "{ call up_idcheck(pid=>?,pcheck=>?) }";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int check = -1;
		conn = DBConn.getConnection();
		
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, id);
			cstmt.registerOutParameter(2, OracleTypes.INTEGER);
			cstmt.executeQuery();
			check = cstmt.getInt(2);
			if (check == 0) {
				System.out.println("사용 가능한 ID(empno)입니다.");
			} else {
				System.out.println("이미 사용중인 ID(empno)입니다.");
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
