package by.grsu.streha.weather.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.grsu.streha.weather.db.dao.AbstractDao;
import by.grsu.streha.weather.db.dao.IDao;
import by.grsu.streha.weather.db.model.City;
import by.grsu.streha.weather.db.model.Weather;
import by.grsu.streha.weather.web.dto.SortDto;
import by.grsu.streha.weather.web.dto.TableStateDto;

public class CityDaoImpl extends AbstractDao implements IDao<Integer, City> {
	public static final CityDaoImpl INSTANCE = new CityDaoImpl();

	private CityDaoImpl() {
		super();
	}

	@Override
	public void insert(City entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("insert into city(name, country_id) values(?,?)");
			pstmt.setString(1, entity.getName());
			pstmt.setInt(2, entity.getCountryId());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "city"));
		} catch (SQLException e) {
			throw new RuntimeException("can't insert City entity", e);
		}
	}

	@Override
	public void update(City entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("update city set name=?, country_id=? where id=?");
			pstmt.setString(1, entity.getName());
			pstmt.setInt(2, entity.getCountryId());
			pstmt.setInt(3, entity.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("can't update City entity", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("delete from city where id=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("can't delete City entity", e);
		}

	}

	@Override
	public City getById(Integer id) {
		City entity = null;
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from city where id=?");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				entity = rowToEntity(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't get City entity by id", e);
		}

		return entity;
	}

	@Override
	public List<City> getAll() {
		List<City> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			ResultSet rs = c.createStatement().executeQuery("select * from city");
			while (rs.next()) {
				City entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select City entities", e);
		}

		return entitiesList;
	}

	private City rowToEntity(ResultSet rs) throws SQLException {
		City entity = new City();
		entity.setId(rs.getInt("id"));
		entity.setName(rs.getString("name"));
		entity.setCountryId(rs.getInt("country_id"));
		return entity;
	}

	@Override
	public List<City> find(TableStateDto tableStateDto) {
		List<City> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			StringBuilder sql = new StringBuilder("select * from city");

			final SortDto sortDto = tableStateDto.getSort();
			if (sortDto != null) {
				sql.append(String.format(" order by %s %s", sortDto.getColumn(), resolveSortOrder(sortDto)));
			}

			sql.append(" limit " + tableStateDto.getItemsPerPage());
			sql.append(" offset " + resolveOffset(tableStateDto));

			System.out.println("searching City using SQL: " + sql);
			ResultSet rs = c.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				City entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select City entities", e);
		}
		return entitiesList;
	}

	@Override
	public int count() {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select count(*) as c from city");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("c");
		} catch (SQLException e) {
			throw new RuntimeException("can't get city count", e);
		}
	}
}

