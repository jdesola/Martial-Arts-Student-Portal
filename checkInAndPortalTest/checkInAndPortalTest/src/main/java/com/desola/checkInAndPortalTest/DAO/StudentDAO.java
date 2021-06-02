package com.desola.checkInAndPortalTest.DAO;

import java.util.List;

import com.desola.checkInAndPortalTest.Model.Student;

public interface StudentDAO {
	public void addStudent(Student newStudent);
	public Student getStudent(Long studentId);
	public List<Student> getAllStudents();
	public void updateStudent(Student studnet);
}
