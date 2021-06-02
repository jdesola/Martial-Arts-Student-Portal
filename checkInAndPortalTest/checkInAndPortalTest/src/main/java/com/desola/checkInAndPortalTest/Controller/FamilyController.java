package com.desola.checkInAndPortalTest.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.desola.checkInAndPortalTest.JDBC.JDBCFamilyDAO;
import com.desola.checkInAndPortalTest.Model.Family;

@RestController
public class FamilyController {
	private JDBCFamilyDAO familyDao;
	
	public FamilyController(JDBCFamilyDAO familyDao) {
		this.familyDao = familyDao;
	}
	
	@RequestMapping(path="/families/", method=RequestMethod.GET)
	public List<Family> getFamilies() {
		return familyDao.getAllFamilies();
	}
	
	@RequestMapping(path="/families/{familyId}", method=RequestMethod.GET)
	public Family getFamily(@PathVariable long familyId) {
		return familyDao.getFamily(familyId);
	}
}
