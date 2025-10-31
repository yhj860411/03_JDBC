package edu.kh.jdbc.homework.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static edu.kh.jdbc.homework.common.JDBCTemplate.*;
import edu.kh.jdbc.homework.model.dto.Student;

public class StudentDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/** 1. 학생 등록하기
	 * @param conn
	 * @param std
	 * @return
	 */
	public int insertStd(Connection conn, Student std) throws Exception{
		int result = 0;
		
		try {
			String sql = """
					INSERT INTO KH_STUDENT
					VALUES(SEQ_STD_NO.NEXTVAL, ?, ?, ?, SYSDATE)
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, std.getStdName());
			pstmt.setInt(2, std.getStdAge());
			pstmt.setString(3, std.getStdMajor());
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/** 2. 전체 학생 조회하기
	 * @param conn
	 * @return
	 */
	public List<Student> selectAll(Connection conn) throws Exception{
		List<Student> stdList = new ArrayList<Student>();
		Student std = null;
		
		try {
			String sql = """
					SELECT STD_NO, STD_NAME, STD_AGE, MAJOR,
					TO_CHAR(ENT_DATE,'YYYY-MM-DD') ENROLL
					FROM KH_STUDENT
					ORDER BY 1
					""";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int stdNo = rs.getInt("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String stdMajor = rs.getString("MAJOR");
				String stdEntDate = rs.getString("ENROLL");
				
				std = new Student(stdNo, stdName, stdAge, stdMajor, stdEntDate);
				
				stdList.add(std);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return stdList;
	}

	/**3. 학번 일치 학생 조회하기
	 * @param conn
	 * @param input
	 * @return
	 */
	public int selectStdNo(Connection conn, int input) throws Exception{
		int stdNo = 0;
		
		try {
			String sql = """
					SELECT STD_NO
					FROM KH_STUDENT
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				stdNo = rs.getInt("STD_NO");
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return stdNo;
	}

	/** 3-2. 학생 정보 수정하기
	 * @param conn
	 * @param stdName
	 * @param stdAge
	 * @param stdMajor
	 * @return
	 */
	public int updateStd(Connection conn, int stdNo, String stdName, int stdAge, String stdMajor) 
		throws Exception{
		int result = 0;
		try {
			String sql = """
					UPDATE KH_STUDENT
					SET STD_NAME = ?,
					STD_AGE = ?,
					MAJOR = ?
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stdName);
			pstmt.setInt(2, stdAge);
			pstmt.setString(3, stdMajor);
			pstmt.setInt(4, stdNo);

			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteStd(Connection conn, int stdNo) throws Exception{
		int result = 0;
		try {
			String sql = """
					DELETE FROM KH_STUDENT
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stdNo);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public List<Student> selectByMajor(Connection conn, String input) throws Exception{
		List<Student> stdList = new ArrayList<Student>();
		Student std = null;
		try {
			String sql = """
					SELECT STD_NO, STD_NAME, STD_AGE, MAJOR,
					TO_CHAR(ENT_DATE,'YYYY-MM-DD') ENROLL
					FROM KH_STUDENT
					WHERE MAJOR = ?
					ORDER BY 1
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, input);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int stdNo = rs.getInt("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String stdMajor = rs.getString("MAJOR");
				String stdEntDate = rs.getString("ENROLL");
				
				std = new Student(stdNo, stdName, stdAge, stdMajor, stdEntDate);
				
				stdList.add(std);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return stdList;
	}

}
