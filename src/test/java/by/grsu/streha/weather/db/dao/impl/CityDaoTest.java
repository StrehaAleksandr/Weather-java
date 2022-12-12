package by.grsu.streha.weather.db.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import by.grsu.streha.weather.db.dao.IDao;
import by.grsu.streha.weather.db.model.Country;
import by.grsu.streha.weather.db.model.City;

public class CityDaoTest extends AbstractTest {
	private static final IDao<Integer, Country> countryDao = CountryDaoImpl.INSTANCE;
	private static final IDao<Integer, City> cityDao = CityDaoImpl.INSTANCE;

	@Test
	public void testInsert() {
		City entity = new City();
		entity.setCountryId(saveCountry("USA").getId());
		entity.setName("New-York");
		cityDao.insert(entity);
		Assertions.assertNotNull(entity.getId());
	}

	@Test
	public void testUpdate() {
		City entity = new City();
		entity.setCountryId(saveCountry("USA").getId());
		entity.setName("New-York");
		cityDao.insert(entity);

		String newName = "Los-Angeles";
		entity.setName(newName);
		cityDao.update(entity);

		City updatedEntity = cityDao.getById(entity.getId());
		Assertions.assertEquals(newName, updatedEntity.getName());
	}

	@Test
	public void testDelete() {
		City entity = new City();
		entity.setCountryId(saveCountry("USA").getId());
		entity.setName("New-York");
		cityDao.insert(entity);

		cityDao.delete(entity.getId());

		Assertions.assertNull(cityDao.getById(entity.getId()));
	}

	@Test
	public void testGetById() {
		City entity = new City();
		entity.setCountryId(saveCountry("USA").getId());
		entity.setName("New-York");
		cityDao.insert(entity);

		City selectedEntity = cityDao.getById(entity.getId());

		Assertions.assertEquals(entity.getName(), selectedEntity.getName());
		Assertions.assertEquals(entity.getCountryId(), selectedEntity.getCountryId());
	}

	@Test
	public void testGetAll() {
		int expectedCount = getRandomNumber(1, 5);
		for (int i = 1; i <= expectedCount; i = i + 1) {
			City entity = new City();
			entity.setCountryId(saveCountry("USA" + i).getId());
			entity.setName("New-York" + i);
			cityDao.insert(entity);
		}

		Assertions.assertEquals(expectedCount, cityDao.getAll().size());
	}

	private Country saveCountry(String name) {
		Country entity = new Country();
		entity.setName(name);
		countryDao.insert(entity);
		return entity;
	}
}
