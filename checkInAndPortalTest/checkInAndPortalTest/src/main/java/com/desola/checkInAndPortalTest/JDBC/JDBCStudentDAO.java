package com.desola.checkInAndPortalTest.JDBC;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desola.checkInAndPortalTest.DAO.StudentDAO;
import com.desola.checkInAndPortalTest.Model.Student;

@Service
@Repository
@Transactional
public class JDBCStudentDAO implements StudentDAO{
	
	private final JdbcTemplate jdbcTemplate;
	
	public JDBCStudentDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	public void addStudent(Student newStudent) {
		String addStudentSqlString = "INSERT INTO student(family_id, first_name, preferred_name, last_name, birth_date) VALUES (?, ?, ?, ?, ?);";
		jdbcTemplate.update(addStudentSqlString, newStudent.getFamilyId(), newStudent.getFirstName(), newStudent.getPreferredName(), newStudent.getLastName(),
				newStudent.getBirthDate());
		
	}
	
	public void updateStudent(Student student) {
		String updateStudentSqlString = "UPDATE student SET family_id = ?, first_name = ?, preferred_name = ?, last_name = ?, birth_date = ? WHERE student_id = ?;";
		jdbcTemplate.update(updateStudentSqlString, student.getFamilyId(), student.getFirstName(), student.getPreferredName(), student.getLastName(), student.getBirthDate(), student.getId());
	}

	public Student getStudent(Long studentId) {
		Student retrievedStudent = new Student();
		String getStudentSqlString = "SELECT student_id, student.family_id, family_name, first_name, preferred_name, last_name, birth_date FROM student, family WHERE student.student_id = ? AND family.family_id = student.family_id;";
		SqlRowSet result = jdbcTemplate.queryForRowSet(getStudentSqlString, studentId);
		
		result.next();
			retrievedStudent = mapRowToStudent(result);
		
		return retrievedStudent;
	}
	
	public List<Student> getAllStudents() {
		List<Student> allStudents = new ArrayList<Student>();
		Student theStudent = new Student();
		String getStudentsSqlString = "SELECT student_id, student.family_id, family_name, first_name, preferred_name, last_name, birth_date FROM student, family WHERE family.family_id = student.family_id;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(getStudentsSqlString);
		while(results.next()) {
			theStudent = mapRowToStudent(results);
			allStudents.add(theStudent);
		}
		return allStudents;
	}
	
	private Student mapRowToStudent(SqlRowSet result) {
		Student theStudent;
		theStudent = new Student();
		
		theStudent.setId(result.getLong("student_id"));
		theStudent.setFamilyId(result.getLong("family_id"));
		theStudent.setFamilyName(result.getString("family_name"));
		theStudent.setFirstName(result.getString("first_name"));
		theStudent.setPreferredName(result.getString("preferred_name"));
		theStudent.setLastName(result.getString("last_name"));
		theStudent.setBirthDate(result.getDate("birth_date").toLocalDate());
		
		return theStudent;
	}
}
