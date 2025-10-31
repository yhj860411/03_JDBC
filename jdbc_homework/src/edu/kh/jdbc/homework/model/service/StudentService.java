package edu.kh.jdbc.homework.model.service;

import java.sql.Connection;
import java.util.List;

import static edu.kh.jdbc.homework.common.JDBCTemplate.*;
import edu.kh.jdbc.homework.dao.StudentDAO;
import edu.kh.jdbc.homework.model.dto.Student;

public class StudentService {
	
	private StudentDAO dao = new StudentDAO();

	/** 1. 학생 등록하기
	 * @param std
	 * @return
	 */
	public int insertStd(Student std) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.insertStd(conn, std);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	/** 2. 전체 학생 조회하기
	 * @return
	 */
	public List<Student> selectAll() throws Exception{
		Connection conn = getConnection();
		
		List<Student> stdList = dao.selectAll(conn);
		
		close(conn);
		
		return stdList;
	}

	/** 3. 학번 일치 학생 조회하기
	 * @param input
	 * @return
	 */
	public int selectStdNo(int input) throws Exception{
		Connection conn = getConnection();
		
		int stdNo = dao.selectStdNo(conn, input);
		close(conn);
		return stdNo;
	}

	/** 3-2. 학생 정보 수정하기
	 * @param stdName
	 * @param stdAge
	 * @param stdMajor
	 * @return
	 */
	public int updateStd(int stdNo, String stdName, int stdAge, String stdMajor) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.updateStd(conn, stdNo, stdName, stdAge, stdMajor);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	/** 4. 학생 삭제하기
	 * @param stdNo
	 * @return
	 */
	public int deleteStd(int stdNo) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.deleteStd(conn, stdNo);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public List<Student> selectByMajor(String input) throws Exception{
		Connection conn = getConnection();
		
		List<Student> stdList = dao.selectByMajor(conn, input);
		close(conn);
		return stdList;
	}

}
