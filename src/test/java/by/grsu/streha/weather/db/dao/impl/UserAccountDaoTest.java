package by.grsu.streha.weather.db.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import by.grsu.streha.weather.db.dao.IDao;
import by.grsu.streha.weather.db.model.UserAccount;

public class UserAccountDaoTest extends AbstractTest {
	private static final IDao<Integer, UserAccount> dao = UserAccountDaoImpl.INSTANCE;

	@Test
	public void testInsert() {
		UserAccount entity = new UserAccount();
		entity.setLogin("User");
		entity.setPassword("User");
		dao.insert(entity);
		Assertions.assertNotNull(entity.getId());
	}

	@Test
	public void testUpdate() {
		UserAccount entity = new UserAccount();
		entity.setLogin("User");
		entity.setPassword("User");
		dao.insert(entity);

		String newName = "User_NEW";
		entity.setLogin(newName);
		String newPassword = "User_NEW";
		entity.setPassword(newPassword);
		dao.update(entity);

		UserAccount updatedEntity = dao.getById(entity.getId());
		Assertions.assertEquals( newName, updatedEntity.getLogin());
		Assertions.assertEquals( newPassword, updatedEntity.getPassword());
	}

	@Test
	public void testDelete() {
		UserAccount entity = new UserAccount();
		entity.setLogin("User");
		entity.setPassword("User");
		dao.insert(entity);

		dao.delete(entity.getId());

		Assertions.assertNull(dao.getById(entity.getId()));
	}

	@Test
	public void testGetById() {
		UserAccount entity = new UserAccount();
		entity.setLogin("User");
		entity.setPassword("User");
		dao.insert(entity);

		UserAccount selectedEntity = dao.getById(entity.getId());

		Assertions.assertEquals(entity.getLogin(), selectedEntity.getLogin());
		Assertions.assertEquals(entity.getPassword(), selectedEntity.getPassword());
	}

	@Test
	public void testGetAll() {
		int expectedCount = getRandomNumber(1, 5);
		for (int i = 1; i <= expectedCount; i = i + 1) {
			UserAccount entity = new UserAccount();
			entity.setLogin("User" + i); // generate some random meaningless name as it is just a test (the data can be unreal)
			entity.setPassword("User" + i);
			dao.insert(entity);
		}

		Assertions.assertEquals(expectedCount, dao.getAll().size());
	}
}
