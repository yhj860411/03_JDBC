package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JDBCExample7 {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Scanner sc = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			
			String userName = "kh_yhj";
			String password = "kh1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			sc = new Scanner(System.in);
			
			System.out.print("조회할 성별(M/F) : " );
			String gender = sc.next().toUpperCase();
			System.out.println("급여 범위(최소, 최대 순서로 작성) :");
			int min = sc.nextInt();
			int max = sc.nextInt();
			System.out.print("급여 정렬(1.ASC, 2.DESC) : ");
			int arrange = sc.nextInt();
			
			String sql = """
					SELECT EMP_ID, EMP_NAME, 
					DECODE(SUBSTR(EMP_NO,8,1),'1','M','2','F') GENDER, 
					SALARY, JOB_NAME, NVL(DEPT_TITLE, '없음') DEPT_TITLE
					FROM EMPLOYEE
					JOIN JOB USING(JOB_CODE)
					LEFT JOIN DEPARTMENT ON(DEPT_ID = DEPT_CODE) 
					WHERE DECODE(SUBSTR(EMP_NO,8,1),'1','M','2','F') = ?
					AND SALARY BETWEEN ? AND ?
					ORDER BY SALARY 
					""";
			// ORDER BY 절에 ? (위치홀더) 사용 시 오류 : SQL 명령어가 올바르게 종료되지 않았습니다.
			// PreparedStatement의 위치홀더(?)는 ** 데이터 값(리터럴) ** 을 대체하는 용도로만 사용가능
			// -> SQL에서 ORDER BY절의 정렬 기준 (ASC/DESC)과 같은
			// SQL 구문(문법)의 일부는 PreparedStatement의 위치 홀더(?)로 대체될 수 없음
			
			// 급여의 오름차순인지 내림차순인지 조건에 따라 SQL 보완하기
			if(arrange == 1) sql += " ASC";
			else             sql += " DESC";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, gender);
			pstmt.setInt(2, min);
			pstmt.setInt(3, max);
			
			rs = pstmt.executeQuery();
			
			boolean flag = true; // true : 조회결과가 없음, false : 조회결과 존재함!
			
			System.out.println("사번 | 이름   | 성별 | 급여    | 직급명 | 부서명");
			System.out.println("--------------------------------------------------------");
			
			while(rs.next()) {
				flag = false;
				// while문이 1회 이상 반복됨 == 조회 결과가 1행이라도 있다
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String gen = rs.getString("GENDER");
				int salary = rs.getInt("SALARY");
				String jobName = rs.getString("JOB_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				
				System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
						empId, empName, gen, salary, jobName, deptTitle);
			}
			if(flag) {// flag == true인 경우 -> while문 안쪽 수행 x -> 조회결과가 1행도 없음
				System.out.println("조회 결과 없음");
			}		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(sc != null) sc.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}	
		}
	}
}
