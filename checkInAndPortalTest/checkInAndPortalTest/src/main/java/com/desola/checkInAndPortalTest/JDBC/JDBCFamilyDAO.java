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
import com.desola.checkInAndPortalTest.Model.Address;
import com.desola.checkInAndPortalTest.Model.EmailAddress;
import com.desola.checkInAndPortalTest.Model.Family;
import com.desola.checkInAndPortalTest.Model.PhoneNumber;

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
		String addAddressSqlString = "INSERT INTO address(street_address_1, street_address_2, city, state, postal_code VALUES (?, ?, ?, ?, ?) RETURNING address_id);";
		long addressId = jdbcTemplate.queryForObject(addAddressSqlString, Long.class, family.getStreetAddress1(), family.getStreetAddress2(), family.getCity(), family.getState(), family.getPostalCode());
		String addFamilyAddressEntrySqlString = "INSERT INTO family_address VALUES (?, ?);";
		jdbcTemplate.update(addFamilyAddressEntrySqlString, familyId, addressId);
		String addPhoneNumberSqlString = "INSERT INTO phone_number(phone_number, phone_number_type) VALUES (?,?);";
		long phoneNumberId = jdbcTemplate.queryForObject(addPhoneNumberSqlString, Long.class, family.getPhoneNumber(), family.getPhoneNumberType());
		String addFamilyPhoneNumberSqlString = "INSERT INTO family_phone_number VALUES (?, ?);";
		jdbcTemplate.update(addFamilyPhoneNumberSqlString, familyId, phoneNumberId);
		String addEmailAddressSqlString = "INSERT INTO email_address(family_id, email_address, email_address_type) VALUES (?, ?);";
		return familyId;
	}

	@Override
	public void markFamilyInactive(long familyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFamily(Family family) {
		// TODO Auto-generated method stub

	}

	@Override
	public Family getFamily(long familyId) {
		//gets initial family setup with the family name
		String getFamilySqlString = "SELECT family_name, active FROM family WHERE family_id = ?;";
		SqlRowSet retrievedFamily = jdbcTemplate.queryForRowSet(getFamilySqlString, familyId);
		retrievedFamily.next();
		Family theFamily = mapRowSetToFamily(retrievedFamily);
		theFamily.setFamilyId(familyId);
		//gets and sets list of all addresses for family based on family id
		String getFamilyAddressSqlString = "SELECT address_id, street_address_1, street_address_2, city, state, " +
				"postal_code FROM address WHERE family_id = ?"; 
		SqlRowSet retrievedAddresses = jdbcTemplate.queryForRowSet(getFamilyAddressSqlString, familyId);
		List<Address> familyAddresses = new ArrayList<Address>(); 
		while (retrievedAddresses.next()) {
			familyAddresses.add(mapRowSetToAddress(retrievedAddresses)); 
			}
		theFamily.setAddresses(familyAddresses);
		//gets and sets list of all phone numbers based on family id
		String getFamilyPhoneNumberSqlString = "SELECT phone_number_id, phone_number, phone_number_type "
				+ "FROM phone_number WHERE family_id = ?";
		SqlRowSet retrievedPhoneNumbers  = jdbcTemplate.queryForRowSet(getFamilyPhoneNumberSqlString, familyId);
		List<PhoneNumber> familyPhoneNumbers = new ArrayList<PhoneNumber>();
		while(retrievedPhoneNumbers.next()) {
			familyPhoneNumbers.add(mapRowSetToPhoneNumber(retrievedPhoneNumbers));
		}
		theFamily.setPhoneNumbers(familyPhoneNumbers); 
		 //gets and sets list of all email addresses based on family id
		String getFamilyEmailAddressSqlString = "SELECT email_address_id, email_address, email_address_type FROM "
				+ "email_address WHERE family_id = ?;";
		SqlRowSet retrievedEmailAddresses = jdbcTemplate.queryForRowSet(getFamilyEmailAddressSqlString, familyId);
		List<EmailAddress> familyEmailAddresses = new ArrayList<EmailAddress>();
		while(retrievedEmailAddresses.next()) {
			familyEmailAddresses.add(mapRowSetToEmailAddress(retrievedEmailAddresses));
		}
		theFamily.setEmailAddresses(familyEmailAddresses);
		return theFamily;

	}

	@Override
	public List<Family> getAllFamilies() {
		List<Family> allFamilies = new ArrayList<Family>();
		Family theFamily = new Family();
		String getFamilySqlString = "SELECT family.family_id, family_name, address.address_id, street_address_1, "
				+ "street_address_2, city, state, postal_code, phone_number.phone_number_id, phone_number, "
				+ "phone_number_type, email_address.email_address_id, email_address, email_address_type"
				+ " FROM family" + " INNER JOIN family_address ON family.family_id = family_address.family_id"
				+ " INNER JOIN address ON address.address_id = family_address.address_id"
				+ " INNER JOIN family_phone_number ON family.family_id = family_phone_number.family_id"
				+ " INNER JOIN phone_number ON phone_number.phone_number_id = family_phone_number.phone_number_id"
				+ " INNER JOIN family_email_address ON family_email_address.family_id = family_email_address.email_address_id"
				+ " INNER JOIN email_address ON email_address.email_address_id = family_email_address.email_address_id;";

		SqlRowSet allFamiliesRows = jdbcTemplate.queryForRowSet(getFamilySqlString);
		while (allFamiliesRows.next()) {
			theFamily = mapRowSetToFamily(allFamiliesRows);
			allFamilies.add(theFamily);
		}
		return allFamilies;
	}

	private Family mapRowSetToFamily(SqlRowSet results) {
		Family theFamily = new Family();
		theFamily.setFamilyName(results.getString("family_name"));
		theFamily.setActive(results.getBoolean("active"));
		return theFamily;
	}

	private Address mapRowSetToAddress(SqlRowSet results) {
		Address theAddress = new Address();
		theAddress.setAddressId(results.getLong("address_id"));
		theAddress.setStreetAddress1(results.getString("street_address_1"));
		theAddress.setStreetAddress2(results.getString("street_address_2"));
		theAddress.setCity(results.getString("city"));
		theAddress.setState(results.getString("state"));
		theAddress.setPostalCode(results.getString("postal_code"));
		return theAddress;
	}
	
	private PhoneNumber mapRowSetToPhoneNumber(SqlRowSet results) {
		PhoneNumber thePhoneNumber = new PhoneNumber();
		thePhoneNumber.setPhoneNumberId(results.getLong("phone_number_id"));
		thePhoneNumber.setPhoneNumber(results.getString("phone_number"));
		thePhoneNumber.setPhoneNumberType(results.getString("phone_number_type"));
		return thePhoneNumber;
	}
	private EmailAddress mapRowSetToEmailAddress(SqlRowSet results) {
		EmailAddress theEmailAddress = new EmailAddress();
		theEmailAddress.setEmailAddressId(results.getLong("email_address_id"));
		theEmailAddress.setEmailAddress(results.getString("email_address"));
		theEmailAddress.setEmailAddressType(results.getString("email_address_type"));
		return theEmailAddress;
		
	}

}
