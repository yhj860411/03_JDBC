package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample1 {

	public static void main(String[] args) {
		
		/* JDBC (Java DataBase Connectivity)
		 * 
		 * - Java에서 DB에 연결 할 수 있게 해주는
		 * Java API (Java에서 제공하는 코드)
		 * 
		 * -> java.sql 패키지에 존재함
		 * 
		 */
		
		// Java 코드를 이용해
		// EMPLOYEE 테이블에서
		// 사번, 이름, 부서코드, 직급코드, 급여, 입사일 조회 후
		// 이클립스 콘솔에 출력
		
		// 1. JDBC 객체 참조용 변수 선언
		
		// java.sql.Connection
		// 특정 DBMS와 연결하기 위한 정보를 저장하는 객체
		// == DBeaver에서 사용하는 DB 연결과 같은 역할의 객체
		// (DB 서버 주소, 포트번호, DB이름, 계정명, 비밀번호)
		Connection conn = null;
		
		// java.sql.Statement
		// - 1) SQL을 Java -> DB 에 전달
		// - 2) DB에서 SQL 수행한 결과를 반환 받아옴(DB -> Java)
		Statement stmt = null;
		
		// java.sql.ResultSet
		// - SELECT 조회 후 결과(RESULT SET)를 저장하는 객체
		ResultSet rs = null;
		
		
		try {
			// 2. DriverManager 객체를 이용해서 Connection 객체 생성하기
			// java.sql.DriverManager
			// - DB 연결 정보와 JDBC 드라이버를 이용해서
			// 원하는 DB와 연결할 수 있는 Connection 객체를 생성하는 객체
			
			// 2-1) Oracle JDBC Driver 객체를 메모리에 로드 하기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Class.forName("패키지명+클래스명");
			// - 해당 클래스를 읽어 메모리에 적재
			// -> JVM이 프로그램 동작에 사용할 객체를 생성하는 구문
			
			// oracle.jdbc.driver.OracleDriver
			// - Oracle DBMS 연결 시 필요한 코드가 담겨있는 클래스
			//   ojdbc 라이브러리 파일 내에 존재함
			
			// 2-2) DB 연결 정보 작성
			String type = "jdbc:oracle:thin:@"; // 드라이버 종류
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인주소
										// localhost == 현재 컴퓨터
			String port = ":1521"; // DB 서버에 연결하기 위한 PORT 번호
			String dbName = ":XE"; // DBMS 이름 (XE == eXpress Edition)
			
			// jdbc:oracle:thin:@localhost:1521:XE
			
			String userName = "kh_yhj"; // 사용자 계정명
			String password = "kh1234"; // 계정 비밀번호
			
			// 2-3) DB연결 정보와 DriverManager를 이용해서 Connection 객체 생성
			conn = DriverManager.getConnection(type+host+port+dbName, userName, password);
			
			// Connection 객체가 잘 생성되었는지 확인
			System.out.println(conn);
			
			// 3. SQL 작성
			// 주의사항!
			// -> JDBC 코드에서 SQL 작성시 세미콜론 작성하면 안된다!
			// -> "SQL 명령어가 올바르게 종료되지 않았습니다" 오류 발생
			
			// EMPLOYEE 테이블에서
			// 사번, 이름, 부서코드, 직급코드, 급여, 입사일 조회
			String sql = "SELECT EMP_ID, EMP_NAME, DEPT_CODE,"
					+ " JOB_CODE, SALARY, HIRE_DATE FROM EMPLOYEE";
			
			// 4. Statement 객체 생성
			stmt = conn.createStatement();
			// 연결된 DB에 SQL을 전달하고
			// 결과를 반환 받을 역할을 함
			
			// 5. Statement 객체를 이용해서 SQL 수행 후 결과를 반환 받기
			
			// 1) ResultSet Statement.executeQuery(sql)
			// -> sql이 SELECT 문일 때 결과로 ResultSet 객체 반환
			
			// 2) int Statement.executeUpdate(sql);
			// -> sql이 DML(INSERT/UPDATE/DELETE)일 때 실행 메서드
			// -> 결과로 int 반환(삽입, 수정, 삭제된 행의 갯수)
			
			rs = stmt.executeQuery(sql);
			
			// 6. 조회 결과가 담겨있는 rs를
			// 커서(cursor)를 이용해서
			// 1행 씩 접근해 각 행에 작성된 컬럼 값 얻어오기
			
			// boolean ResultSet.next() :
			// 커서를 다음 행으로 이동 시킨 후
			// 이동된 행에 값이 있으면 true, 없으면 false 반환
			// 맨 처음 호출 시 1행부터 시작
			
			while(rs.next()) {
				// 자료형 ResultSet.get자료형(컬럼명 | 순서);
				// - 현재 행에서 지정된 컬럼의 값을 얻어와 반환
				// -> 지정된 자료형 형태로 값이 반환됨
				// (자료형을 잘못 지정하면 예외 발생)
				
				// [java]                      [db]
				// String                  CHAR, VARCHAR2, NVARCHAR2
				// int, long               NUMBER (정수만 저장된 컬럼)
				// float, double           NUMBER (정수 + 실수)
				// java.sql.Date           DATE
				String empId = rs.getString("EMP_ID"); // "200"
				String empName = rs.getString("EMP_NAME"); // "선동일"
				String deptCode = rs.getString("DEPT_CODE"); // "D9"
				String jobCode = rs.getString("JOB_CODE"); // "J1"
				int salary = rs.getInt("SALARY"); // 8,000,000
				Date hireDate = rs.getDate("HIRE_DATE"); // 1990-02-06
				
				System.out.printf("사번 : %s / 이름 : %s / 부서코드 : %s / "
						+ "직급코드 : %s / 급여 : %d / 입사일 : %s \n", 
						empId, empName, deptCode, jobCode, salary, hireDate.toString());
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("해당 클래스를 찾을 수 없음");
			e.printStackTrace();
		} catch (SQLException e) {
			// SQLException : DB 연결과 관련된 모든 예외의 최상위 부모
			e.printStackTrace();
		} finally {
			// 7. 사용 완료된 jdbc 객체 자원 반환 (close)
			// -> 자원반환을 하지 않으면 DB와 연결된 Connection이 그대로 남아있어서
			// 다른 클라이언트(ex. Java 프로그램)가 추가적으로 연결되지 못하는 문제 발생할 수 있음!
			// -> DBMS는 최대 Connection 개수 제한을 하고 있기 때문에
			
			try {
				// conn, stmt, rs (만들어진 역순으로 close 수행하는 것을 권장!)
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		

	}

}
