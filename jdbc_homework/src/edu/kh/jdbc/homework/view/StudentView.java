package edu.kh.jdbc.homework.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.homework.model.dto.Student;
import edu.kh.jdbc.homework.model.service.StudentService;

public class StudentView {
	
	private StudentService service = new StudentService();
	private Scanner sc = new Scanner(System.in);
	
	int input = 0;
	
	public void mainMenu() {
		do {
			try {
				System.out.println("\n====== 학생(Student)관리 ======\n");
				System.out.println("1. 학생 등록");
				System.out.println("2. 전체 학생 조회");
				System.out.println("3. 학생 정보 수정");
				System.out.println("4. 학생 삭제");
				System.out.println("5. 전공별 학생 조회");
				System.out.println("0. 프로그램 종료");
				System.out.println("=============================\n");
				
				System.out.print("메뉴 번호 입력 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1 : insertStd(); break;
				case 2 : selectAll(); break;
				case 3 : updateStd(); break;
				case 4 : deleteStd(); break;
				case 5 : selectByMajor(); break;
				case 0 : System.out.println("프로그램을 종료합니다..."); break;
				default : System.out.println("메뉴 번호만 입력해주세요!");
				}
				
			} catch (InputMismatchException e) {
				System.out.println("메뉴 번호를 입력해주세요!");
				input = -1;
				sc.nextLine();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} while (input != 0);
	}
	
	/** 1. 학생 등록
	 * 
	 */
	private void insertStd() throws Exception{
		System.out.println("\n******* 학생 등록하기 *******\n");
		
		Student std = new Student();
		
		System.out.print("이름 : ");
		String stdName = sc.next();
		System.out.print("나이 : ");
		int stdAge = sc.nextInt();
		System.out.print("전공 : ");
		String stdMajor = sc.next();
		
		std.setStdName(stdName);
		std.setStdAge(stdAge);
		std.setStdMajor(stdMajor);
		
		int result = service.insertStd(std);
		
		if(result > 0) {
			System.out.println("\n" + stdName + " 님이 등록되었습니다.\n");
		} else {
			System.out.println("\n등록 실패ㅠㅠ\n");
		}
	}
	
	/** 2. 전체 학생 조회
	 * 
	 */
	private void selectAll() throws Exception{
		System.out.println("\n******* 전체 학생 조회하기 *******\n");
		
		List<Student> stdList = service.selectAll();
		
		if(stdList.isEmpty()) {
			System.out.println("조회결과가 없습니다.");
			return;
		}
		
		for(Student std : stdList) {
			System.out.println(std);
		}
	}
	
	/** 3. 학생 정보 수정
	 * 
	 */
	private void updateStd() throws Exception{
		System.out.println("\n******* 학생 정보 수정하기 *******\n");
		
		System.out.print("수정할 학생의 학번 : ");
		int input = sc.nextInt();
		int stdNo = service.selectStdNo(input);
		
		if(stdNo == 0) {
			System.out.println("해당 학번의 학생이 없음");
			return;
		}
		
		System.out.print("수정할 이름 : ");
		String stdName = sc.next();
		System.out.print("수정할 나이 : ");
		int stdAge = sc.nextInt();
		System.out.print("수정할 전공 : ");
		String stdMajor = sc.next();
		
		int result = service.updateStd(stdNo, stdName, stdAge, stdMajor);
		
		if(result > 0) {
			System.out.println("\n성공적으로 수정되었습니다.\n");
		} else {
			System.out.println("\n수정 실패ㅠㅠ\n");
		}
	}
	
	/** 4. 학생 삭제
	 * 
	 */
	private void deleteStd() throws Exception{
		System.out.println("\n******* 학생 삭제하기 *******\n");
		
		System.out.print("삭제할 학생의 학번 : ");
		int stdNo = sc.nextInt();
		
		int result = service.deleteStd(stdNo);
		
		if(result > 0) {
			System.out.println("\n성공적으로 삭제되었습니다.\n");
		} else {
			System.out.println("\n해당 학번의 학생이 없음\n");
		}
	}

	/** 5. 전공별 학생 조회
	 * 
	 */
	private void selectByMajor() throws Exception{
		System.out.println("\n******* 전공별 학생 조회하기 *******\n");
		
		System.out.print("전공명 입력 : ");
		String input = sc.next();
		List<Student> stdList = service.selectByMajor(input);
		
		if(stdList.isEmpty()) {
			System.out.println("조회결과가 없습니다.");
			return;
		}
		
		for(Student std : stdList) {
			System.out.println(std);
		}
	}

	

	

	


}
	
