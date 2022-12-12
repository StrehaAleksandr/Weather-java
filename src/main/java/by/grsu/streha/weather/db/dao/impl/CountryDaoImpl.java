package by.grsu.streha.weather.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.grsu.streha.weather.db.dao.AbstractDao;
import by.grsu.streha.weather.db.dao.IDao;
import by.grsu.streha.weather.db.model.Country;
import by.grsu.streha.weather.web.dto.TableStateDto;

public class CountryDaoImpl extends AbstractDao implements IDao<Integer, Country> {
	// single instance of this class to be used by the all consumers
	public static final CountryDaoImpl INSTANCE = new CountryDaoImpl();

	// private constructor disallows instantiation of this class ('Singleton'
	// pattern) outside of current class
	private CountryDaoImpl() {
		super();
	}

	@Override
	public void insert(Country entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("insert into country(name) values(?)");
			pstmt.setString(1, entity.getName());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "country"));
		} catch (SQLException e) {
			throw new RuntimeException("can't insert Country entity", e);
		}
	}

	@Override
	public void update(Country entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("update country set name=? where id=?");
			pstmt.setString(1, entity.getName());
			pstmt.setInt(2, entity.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("can't update Country entity", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("delete from country where id=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("can't delete Country entity", e);
		}

	}

	@Override
	public Country getById(Integer id) {
		Country entity = null;
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from country where id=?");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				entity = rowToEntity(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't get Country entity by id", e);
		}

		return entity;
	}

	@Override
	public List<Country> getAll() {
		List<Country> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			ResultSet rs = c.createStatement().executeQuery("select * from country");
			while (rs.next()) {
				Country entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Country entities", e);
		}

		return entitiesList;
	}

	private Country rowToEntity(ResultSet rs) throws SQLException {
		Country entity = new Country();
		entity.setId(rs.getInt("id"));
		entity.setName(rs.getString("name"));
		return entity;
	}

	@Override
	public List<Country> find(TableStateDto tableStateDto) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public int count() {
		throw new RuntimeException("not implemented");
	}
}
