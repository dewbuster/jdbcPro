package org.doit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeVO {
	
	private int grade;
	private int losal;
	private int hisal;
	private int count;
	private int deptno;
	private String dname;
	private int empno;
	private String ename;
	private double sal;
	
} // class
