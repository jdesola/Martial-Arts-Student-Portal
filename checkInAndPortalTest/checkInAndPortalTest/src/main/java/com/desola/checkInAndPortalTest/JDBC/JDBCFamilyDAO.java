package com.desola.checkInAndPortalTest.JDBC;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desola.checkInAndPortalTest.DAO.FamilyDAO;
import com.desola.checkInAndPortalTest.Model.Family;

@Service
@Repository
@Transactional
public class JDBCFamilyDAO implements FamilyDAO {

	private final JdbcTemplate jdbcTemplate;

	public JDBCFamilyDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public long addFamily(Family family) {
		String addFamilySqlString = "INSERT INTO family(family_name) VALUES (?) RETURNING family_id";
		long familyId = jdbcTemplate.queryForObject(addFamilySqlString, Long.class, family.getFamilyName());
		return familyId;
	}

	@Override
	public void toggleFamilyActiveStatus(Family family) {
		boolean active = false;
		if (family.getActive()) {
			active = false;
		}
		else {
			active = true;
		}
		String markFamilyInactive = "UPDATE family SET active = ? WHERE family_id = ?;";
		jdbcTemplate.update(markFamilyInactive, active, family.getFamilyId());

	}

	@Override
	public void updateFamily(Family family) {
		// TODO Auto-generated method stub

	}

	@Override
	public Family getFamily(long familyId) {
		String getFamilySqlString = "SELECT * FROM family WHERE family_id = ?;";
		SqlRowSet retrievedFamily = jdbcTemplate.queryForRowSet(getFamilySqlString, familyId);
		retrievedFamily.next();
		Family theFamily = mapRowSetToFamily(retrievedFamily);
		return theFamily;

	}

	@Override
	public List<Family> getAllFamilies() {
		List<Family> allFamilies = new ArrayList<Family>();
		Family theFamily = new Family();
		String getFamilySqlString = "SELECT * FROM family;";
		SqlRowSet allFamiliesRows = jdbcTemplate.queryForRowSet(getFamilySqlString);
		while (allFamiliesRows.next()) {
			theFamily = mapRowSetToFamily(allFamiliesRows);
			allFamilies.add(theFamily);
		}
		return allFamilies;
	}

	private Family mapRowSetToFamily(SqlRowSet results) {
		Family theFamily = new Family();
		theFamily.setFamilyId(results.getLong("family_id"));
		theFamily.setFamilyName(results.getString("family_name"));
		theFamily.setActive(results.getBoolean("active"));
		return theFamily;
	}



}
