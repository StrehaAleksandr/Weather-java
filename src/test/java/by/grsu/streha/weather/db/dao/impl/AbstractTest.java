package by.grsu.streha.weather.db.dao.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import by.grsu.streha.weather.db.dao.AbstractDao;

public abstract class AbstractTest {
	@BeforeAll
	private static void setup() {
		AbstractDao.init("test-db");
	}

	@BeforeEach
	private void setupThis() {
		AbstractDao.deleteDb();
		AbstractDao.createDbSchema();
	}

	protected Timestamp getCurrentTime() {
		return new Timestamp(new Date().getTime());
	}

	// "31/12/1998";
	protected Date getDateFromString(String dateStr) {
		try {
			return new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(dateStr).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
