package com.desola.CheckInAndPortalTest.DAO;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.desola.checkInAndPortalTest.JDBC.JDBCStudentDAO;
import com.desola.checkInAndPortalTest.Model.Student;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class JDBCStudentDAOIntegrationTest {
	private static SingleConnectionDataSource dataSource;
	private JDBCStudentDAO dao;
	private long testStudentId = 1;
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/set_tkd");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
		dao = new JDBCStudentDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void retrieve_one_student() {
		Student testStudent = dao.getStudent(testStudentId);
		long retrievedId = testStudent.getId();
		Assert.assertEquals(testStudentId, retrievedId);
		
	}
}
