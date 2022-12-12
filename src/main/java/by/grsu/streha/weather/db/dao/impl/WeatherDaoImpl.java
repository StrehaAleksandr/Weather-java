package by.grsu.streha.weather.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.grsu.streha.weather.db.dao.AbstractDao;
import by.grsu.streha.weather.db.dao.IDao;
import by.grsu.streha.weather.db.model.Weather;
import by.grsu.streha.weather.web.dto.SortDto;
import by.grsu.streha.weather.web.dto.TableStateDto;

public class WeatherDaoImpl extends AbstractDao implements IDao<Integer, Weather> {
	public static final WeatherDaoImpl INSTANCE = new WeatherDaoImpl();

	private WeatherDaoImpl() {
		super();
	}

	@Override
	public void insert(Weather entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement(
					"insert into weather(city_id, tempreture, weather_name, date, creator_id) values(?,?,?,?,?)");
			pstmt.setInt(1, entity.getCityId());
			pstmt.setInt(2, entity.getTempreture());
			pstmt.setString(3, entity.getWeather());
			pstmt.setTimestamp(4, entity.getDate());
			pstmt.setObject(5, entity.getCreatorId());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "weather"));
		} catch (SQLException e) {
			throw new RuntimeException("can't insert Weather entity", e);
		}
	}

	@Override
	public void update(Weather entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement(
					"update weather set city_id=?, tempreture=?, weather_name=?, date=?, creator_id=? where id=?");
			pstmt.setInt(1, entity.getCityId());
			pstmt.setInt(2, entity.getTempreture());
			pstmt.setString(3, entity.getWeather());
			pstmt.setTimestamp(4, entity.getDate());
			pstmt.setObject(5, entity.getCreatorId());
			pstmt.setInt(6, entity.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("can't update Weather entity", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("delete from weather where id=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("can't delete Weather entity", e);
		}

	}

	@Override
	public Weather getById(Integer id) {
		Weather entity = null;
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from weather where id=?");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				entity = rowToEntity(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't get Weather entity by id", e);
		}

		return entity;
	}

	@Override
	public List<Weather> getAll() {
		List<Weather> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			ResultSet rs = c.createStatement().executeQuery("select * from weather");
			while (rs.next()) {
				Weather entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Weather entities", e);
		}

		return entitiesList;
	}

	private Weather rowToEntity(ResultSet rs) throws SQLException {
		Weather entity = new Weather();
		entity.setId(rs.getInt("id"));
		entity.setCityId(rs.getInt("city_id"));
		entity.setTempreture(rs.getInt("tempreture"));
		entity.setWeather(rs.getString("weather_name"));
		entity.setDate(rs.getTimestamp("date"));
		entity.setCreatorId(rs.getInt("creator_id"));
		return entity;
	}

	@Override
	public List<Weather> find(TableStateDto tableStateDto) {
		List<Weather> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			StringBuilder sql = new StringBuilder("select * from weather");

			final SortDto sortDto = tableStateDto.getSort();
			if (sortDto != null) {
				sql.append(String.format(" order by %s %s", sortDto.getColumn(), resolveSortOrder(sortDto)));
			}

			sql.append(" limit " + tableStateDto.getItemsPerPage());
			sql.append(" offset " + resolveOffset(tableStateDto));

			System.out.println("searching Weather using SQL: " + sql);
			ResultSet rs = c.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Weather entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Weather entities", e);
		}
		return entitiesList;
	}

	@Override
	public int count() {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select count(*) as c from weather");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("c");
		} catch (SQLException e) {
			throw new RuntimeException("can't get weather count", e);
		}
	}
}
