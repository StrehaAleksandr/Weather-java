package by.grsu.streha.weather.db.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import by.grsu.streha.weather.db.dao.IDao;
import by.grsu.streha.weather.db.model.Country;
import by.grsu.streha.weather.db.model.City;
import by.grsu.streha.weather.db.model.Weather;
import by.grsu.streha.weather.db.model.UserAccount;

public class WeatherDaoTest extends AbstractTest {
	private static final IDao<Integer, Country> countryDao = CountryDaoImpl.INSTANCE;
	private static final IDao<Integer, City> cityDao = CityDaoImpl.INSTANCE;
	private static final IDao<Integer, Weather> weatherDao = WeatherDaoImpl.INSTANCE;
	private static final IDao<Integer, UserAccount> userAccountDao = UserAccountDaoImpl.INSTANCE;

	
	@Test
	public void testInsert() {
		Weather entity = new Weather();
		
		entity.setCityId(saveCity("USA", "New-York").getId());
		entity.setTempreture(20);
		entity.setWeather("Rain");
		entity.setDate(getCurrentTime());
		entity.setCreatorId(saveUserAccount().getId());
		weatherDao.insert(entity);
		Assertions.assertNotNull(entity.getId());
	}

	/*@Test
	public void testInsertWithoutOwner() {
		Weather entity = new Weather();
		entity.setCityId(saveCity("USA", "New-York").getId());
		entity.setTempreture(20);
		entity.setWeather("Rain");
		entity.setDate(getCurrentTime());
		entity.setCreatorId(saveUserAccount().getId());
		weatherDao.insert(entity);
		Assertions.assertNotNull(entity.getId());
	}*/
	
	@Test
	public void testUpdate() {
		Weather entity = new Weather();
		entity.setCityId(saveCity("USA", "New-York").getId());
		entity.setTempreture(20);
		entity.setWeather("Rain");
		entity.setDate(getCurrentTime());
		entity.setCreatorId(saveUserAccount().getId());
		weatherDao.insert(entity);

		City newCity = saveCity("Belarusian", "Minsk");
		entity.setTempreture(10);
		entity.setWeather("Sun");
		entity.setCityId(newCity.getId());
		entity.setDate(getCurrentTime());
		entity.setCreatorId(saveUserAccount().getId());
		weatherDao.update(entity);

		Weather updatedEntity = weatherDao.getById(entity.getId());
		Assertions.assertEquals(newCity.getId(), updatedEntity.getCityId());
		Assertions.assertEquals(10, updatedEntity.getTempreture());
		Assertions.assertEquals("Sun", updatedEntity.getWeather());
	}
	
	@Test
	public void testDelete() {
		Weather entity = new Weather();
		entity.setCityId(saveCity("USA", "New-York").getId());
		entity.setTempreture(20);
		entity.setWeather("Rain");
		entity.setDate(getCurrentTime());
		entity.setCreatorId(saveUserAccount().getId());
		weatherDao.insert(entity);

		weatherDao.delete(entity.getId());

		Assertions.assertNull(weatherDao.getById(entity.getId()));
	}
	
	@Test
	public void testGetById() {
		Weather entity = new Weather();
		entity.setCityId(saveCity("USA", "New-York").getId());
		entity.setTempreture(20);
		entity.setWeather("Rain");
		entity.setDate(getCurrentTime());
		entity.setCreatorId(saveUserAccount().getId());
		weatherDao.insert(entity);

		Weather selectedEntity = weatherDao.getById(entity.getId());

		Assertions.assertEquals(entity.getCityId(), selectedEntity.getCityId());
		Assertions.assertEquals(entity.getTempreture(), selectedEntity.getTempreture());
		Assertions.assertEquals(entity.getWeather(), selectedEntity.getWeather());
		Assertions.assertEquals(1, selectedEntity.getCreatorId());
		Assertions.assertEquals(entity.getDate(), selectedEntity.getDate());
	}

	@Test
	public void testGetAll() {
		int expectedCount = getRandomNumber(1, 5);
		for (int i = 1; i <= expectedCount; i = i + 1) {
			Weather entity = new Weather();
			entity.setCityId(saveCity("USA"+i, "New-York"+i).getId());
			entity.setTempreture(20 + i);
			entity.setWeather("Rain"+i);
			entity.setDate(getCurrentTime());
			entity.setCreatorId(saveUserAccount().getId());
			weatherDao.insert(entity);
		}

		Assertions.assertEquals(expectedCount, weatherDao.getAll().size());
	}
	
	private UserAccount saveUserAccount() {
		UserAccount entity = new UserAccount();
		entity.setLogin("Ivan");
		entity.setPassword("Ivanov");
		userAccountDao.insert(entity);
		return entity;
	}

	private City saveCity(String country, String city) {
		Country countryEntity = new Country();
		countryEntity.setName(country);
		countryDao.insert(countryEntity);

		City cityEntity = new City();
		cityEntity.setName(city);
		cityEntity.setCountryId(countryEntity.getId());
		cityDao.insert(cityEntity);

		return cityEntity;
	}
}
