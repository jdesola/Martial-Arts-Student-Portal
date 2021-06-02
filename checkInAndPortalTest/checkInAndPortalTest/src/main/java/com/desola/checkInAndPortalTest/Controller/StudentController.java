package com.desola.checkInAndPortalTest.Controller;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.desola.checkInAndPortalTest.DAO.StudentDAO;
import com.desola.checkInAndPortalTest.Model.Student;


@RestController
public class StudentController {
	
	private StudentDAO dao;
	
	public StudentController(StudentDAO studentDao) {
		this.dao = studentDao;
	}
	
	@RequestMapping(path = "/students/{studentId}", method = RequestMethod.GET)
	public Student getStudent(@PathVariable Long studentId){
		Student singleStudent = dao.getStudent(studentId);
		return singleStudent;
	}
	
	@RequestMapping(path="/students", method=RequestMethod.GET)
	public List<Student> getAllStudents(){
		return dao.getAllStudents();
	}
	
	@RequestMapping(path="/students", method=RequestMethod.POST)
	public void addStudent(@RequestBody Student student) {
		dao.addStudent(student);
	}
	
	@RequestMapping(path="/students/{studentId}", method=RequestMethod.PUT)
	public void updateStudent( @RequestBody Student student) {
		dao.updateStudent(student);
	}

}
