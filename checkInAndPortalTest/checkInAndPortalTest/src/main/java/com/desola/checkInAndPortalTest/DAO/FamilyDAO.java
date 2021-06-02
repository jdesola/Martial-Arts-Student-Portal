package com.desola.checkInAndPortalTest.DAO;

import java.util.List;

import com.desola.checkInAndPortalTest.Model.Family;

public interface FamilyDAO {
		public long addFamily(Family family);
		public void markFamilyInactive(long familyId);
		public void updateFamily(Family family);
		public Family getFamily(long familyId);
		public List<Family> getAllFamilies();
}
