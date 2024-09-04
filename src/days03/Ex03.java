package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.util.DBConn;

/**
 * 리플렉션(reflection)
 *	ㄴ JDBC리플렉션 => 결과물(rs)에 대한 정보를 추출해서 사용
 *
 */
public class Ex03 {

	public static void main(String[] args) {
		String sql = "SELECT table_name FROM tabs";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> tnList = new ArrayList<>();
		String tableName = null;
		
		conn = DBConn.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tableName = rs.getString("table_name");
				tnList.add(tableName);
			}
			System.out.println(tnList.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("> 테이블명 입력 ? ");
		tableName = scanner.next();
		sql = "SELECT * FROM  " + tableName;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// rs.getMetaData() : rs 결과 객체로 부터 컬럼의 수, 컬럼 타입, 컬럼 속성
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			System.out.println("the number of column : " +columnCount);
			System.out.println("-".repeat(columnCount * 7));
			/*
			for (int i = 0; i < columnCount; i++) {
				String columnName = rsmd.getColumnName(i+1);
				int columnType = rsmd.getColumnType(i+1);
				String columnTypeName = rsmd.getColumnTypeName(i+1);
				int p = rsmd.getPrecision(i+1);
				int s = rsmd.getScale(i+1);
				if (columnType == 2) {
					System.out.printf("%s\t%d\t%s(%d, %d)\n", columnName, columnType, columnTypeName, p, s);
				} else if(columnType == 12) {
					System.out.printf("%s\t%d\t%s(%d)\n", columnName, columnType, columnTypeName, p);
				} else {
					System.out.printf("%s\t%d\t%s\n", columnName, columnType, columnTypeName);
				}
			}
			*/
			
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnName(i);
				System.out.printf("%s\t", columnName);
			}
			System.out.println();
			System.out.println("-".repeat(columnCount * 7));
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					int columnType = rsmd.getColumnType(i);

					if (columnType == 2) {
						int s = rsmd.getScale(i);
						if (s == 0) {
							System.out.printf("%d\t", rs.getInt(i));
						} else {
							System.out.printf("%.2f\t", rs.getDouble(i));
						}
					} else if (columnType == 12) {
						System.out.printf("%s\t", rs.getString(i));
					} else if (columnType == 93) {
						System.out.printf("%tF\t", rs.getDate(i));
					}
				}
				System.out.println();
			}
			System.out.println("-".repeat(columnCount * 7));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		DBConn.close();
	}
}
