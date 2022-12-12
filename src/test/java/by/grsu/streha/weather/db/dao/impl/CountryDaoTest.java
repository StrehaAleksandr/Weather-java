package by.grsu.streha.weather.db.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import by.grsu.streha.weather.db.dao.IDao;
import by.grsu.streha.weather.db.model.Country;

public class CountryDaoTest extends AbstractTest {
	private static final IDao<Integer, Country> dao = CountryDaoImpl.INSTANCE;
	
	@Test
	public void testInsert() {
		Country entity = new Country();
		entity.setName("USA");
		dao.insert(entity);
		Assertions.assertNotNull(entity.getId());
	}

	@Test
	public void testUpdate() {
		Country entity = new Country();
		entity.setName("USA");
		dao.insert(entity);

		String newName = "USA_NEW";
		entity.setName(newName);
		dao.update(entity);

		Country updatedEntity = dao.getById(entity.getId());
		Assertions.assertEquals( newName, updatedEntity.getName());
	}

	@Test
	public void testDelete() {
		Country entity = new Country();
		entity.setName("USA");
		dao.insert(entity);

		dao.delete(entity.getId());

		Assertions.assertNull(dao.getById(entity.getId()));
	}

	@Test
	public void testGetById() {
		Country entity = new Country();
		entity.setName("USA");
		dao.insert(entity);

		Country selectedEntity = dao.getById(entity.getId());

		Assertions.assertEquals(entity.getName(), selectedEntity.getName());
	}

	@Test
	public void testGetAll() {
		int expectedCount = getRandomNumber(1, 5);
		for (int i = 1; i <= expectedCount; i = i + 1) {
			Country entity = new Country();
			entity.setName("USA" + i); // generate some random meaningless name as it is just a test (the data can be unreal)
			dao.insert(entity);
		}

		Assertions.assertEquals(expectedCount, dao.getAll().size());
	}
}
