package by.grsu.streha.weather.db.model;

import java.sql.Timestamp;

public class Weather {
	private Integer id;
	
	private Integer cityId;

	private Integer tempreture;

	private String weather;

	private Timestamp date;

	private Integer creatorId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
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

	@Override
	public String toString() {
		return "Weather [id=" + id + ", cityId=" + cityId + ", tempreture=" + tempreture + ", weather=" + weather
				+ ", date=" + date + ", creatorId=" + creatorId + "]";
	}
}
