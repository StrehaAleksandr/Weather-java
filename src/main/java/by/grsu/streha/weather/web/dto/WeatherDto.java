package by.grsu.streha.weather.web.dto;

import java.sql.Timestamp;

public class WeatherDto {
	private Integer id;
	
	private Integer countryId;
	
	private String countryName;

	private Integer cityId;
	
	private String cityName;

	private Integer tempreture;

	private String weather;

	private Timestamp date;

	private Integer creatorId;
	
	private String creatorLogin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getTempreture() {
		return tempreture;
	}

	public void setTempreture(Integer tempreture) {
		this.tempreture = tempreture;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	
	public String getCreatorLogin() {
		return creatorLogin;
	}

	public void setCreatorLogin(String creatorLogin) {
		this.creatorLogin = creatorLogin;
	}
}
