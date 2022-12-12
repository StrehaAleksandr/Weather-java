package by.grsu.streha.weather.web.context;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.grsu.streha.weather.db.dao.AbstractDao;
import by.grsu.streha.weather.db.dao.IDao;
import by.grsu.streha.weather.db.dao.impl.CountryDaoImpl;
import by.grsu.streha.weather.db.dao.impl.WeatherDaoImpl;
import by.grsu.streha.weather.db.dao.impl.CityDaoImpl;
import by.grsu.streha.weather.db.dao.impl.UserAccountDaoImpl;
import by.grsu.streha.weather.db.model.Country;
import by.grsu.streha.weather.db.model.Weather;
import by.grsu.streha.weather.db.model.City;
import by.grsu.streha.weather.db.model.UserAccount;

public class AppStartupListener implements ServletContextListener {
	private static final IDao<Integer, Country> countryDao = CountryDaoImpl.INSTANCE;
	private static final IDao<Integer, City> cityDao = CityDaoImpl.INSTANCE;
	private static final IDao<Integer, Weather> weatherDao = WeatherDaoImpl.INSTANCE;
	private static final IDao<Integer, UserAccount> userAccountDao = UserAccountDaoImpl.INSTANCE;

	private static final String DB_NAME = "production-db";
	
	private void initDb() throws SQLException {
		AbstractDao.init(DB_NAME);
		if (!AbstractDao.isDbExist()) {
			System.out.println(String.format("DB '%s' doesn't exist and will be created", DB_NAME));
			AbstractDao.createDbSchema();
			loadInitialData();
		} else {
			System.out.println(String.format("DB '%s' exists", DB_NAME));
		}
	}
	
	private void loadInitialData() {
		Country countryEntity = new Country();
		countryEntity.setName("USA");
		countryDao.insert(countryEntity);
		System.out.println("created: " + countryEntity);
		countryEntity.setName("Russian");
		countryDao.insert(countryEntity);
		System.out.println("created: " + countryEntity);
		countryEntity.setName("Belarusian");
		countryDao.insert(countryEntity);
		System.out.println("created: " + countryEntity);

		City cityEntity = new City();
		cityEntity.setName("New-York");
		cityEntity.setCountryId(countryEntity.getId());
		cityDao.insert(cityEntity);
		System.out.println("created: " + cityEntity);
		cityEntity.setName("Moskow");
		cityEntity.setCountryId(countryEntity.getId());
		cityDao.insert(cityEntity);
		System.out.println("created: " + cityEntity);
		cityEntity.setName("Minsk");
		cityEntity.setCountryId(countryEntity.getId());
		cityDao.insert(cityEntity);
		System.out.println("created: " + cityEntity);

		UserAccount userAccountEntity = new UserAccount();
		userAccountEntity.setLogin("Ivan");
		userAccountEntity.setPassword("Ivanov");
		userAccountDao.insert(userAccountEntity);
		System.out.println("created: " + userAccountEntity);

		Weather weatherEntity = new Weather();
		weatherEntity.setCityId(cityEntity.getId());
		weatherEntity.setTempreture(20);
		weatherEntity.setWeather("Sunny");
		weatherEntity.setDate(getCurrentTime());
		weatherEntity.setCreatorId(userAccountEntity.getId());
		weatherDao.insert(weatherEntity);
		System.out.println("created: " + weatherEntity);
		System.out.println("initial data created");
	}
	
	private Timestamp getCurrentTime() {
		return new Timestamp(new Date().getTime());
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("contextInitialized");
		try {
			initDb();
		} catch (SQLException e) {
			throw new RuntimeException("can't init DB", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("contextDestroyed");
	}

}
