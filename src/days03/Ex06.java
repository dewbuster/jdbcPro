package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;
import oracle.jdbc.internal.OracleTypes;

/**
 * 로그인(인증) / 인가
 * 아이디/비밀번호 입력
 * [로그인][회원가입]
 */
public class Ex06 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("> 로그인할 ID, PWD 입력 ? ");
		int id = scanner.nextInt(); // 7369
		String pwd = scanner.next();

		String sql = "{ call up_login(?, ?, ?) }";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int check = -1;
		conn = DBConn.getConnection();
		
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, id);
			cstmt.setString(2, pwd);
			cstmt.registerOutParameter(3, OracleTypes.INTEGER);
			cstmt.executeQuery();
			check = cstmt.getInt(3);
			if (check == 0) {
				System.out.println("로그인 성공!!!");
			} else if (check == 1) {
				System.out.println("아이디는 존재하지만 비밀번호가 잘못되었다.");
			} else if (check == -1){
				System.out.println("존재하지 않는 아이디 입니다.");
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
CREATE OR REPLACE PROCEDURE up_login
(
    pid IN emp.empno%TYPE
    , ppwd IN emp.ename%TYPE
    , pcheck OUT NUMBER -- 0(인증성공), 1(ID 존재,PWD x), -1(ID존재x)
)
IS
    vpwd emp.ename%TYPE;
BEGIN
    SELECT COUNT(*) INTO pcheck
    FROM emp
    WHERE empno = pid;
    
    IF pcheck = 1 THEN
        SELECT ename INTO vpwd
        FROM emp
        WHERE empno = pid;
        
        IF vpwd = ppwd THEN
            pcheck := 0;
        ELSE
            pcheck := 1;
        END IF;
    ELSE
        pcheck := -1;
    END IF;
--EXCEPTION
--    WHEN OTHERS THEN
--        RAISE_
END;
*/