package edu.kh.jdbc.homework.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Student {
	
	private int stdNo;
	private String stdName;
	private int stdAge;
	private String stdMajor;
	private String stdEntDate;
	
	@Override
	public String toString() {
		return "[학번 : " + stdNo + " / 이름 : " + stdName + " / 나이 : " + stdAge + " / 전공 : " + stdMajor
				+ " / 등록일 : " + stdEntDate + "]";
	}

}
