package by.grsu.streha.weather.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.grsu.streha.weather.db.dao.AbstractDao;
import by.grsu.streha.weather.db.dao.IDao;
import by.grsu.streha.weather.db.model.UserAccount;
import by.grsu.streha.weather.web.dto.TableStateDto;

public class UserAccountDaoImpl extends AbstractDao implements IDao<Integer, UserAccount> {
	public static final UserAccountDaoImpl INSTANCE = new UserAccountDaoImpl();

	private UserAccountDaoImpl() {
		super();
	}

	@Override
	public void insert(UserAccount entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("insert into user_account(login, password) values(?,?)");
			pstmt.setString(1, entity.getLogin());
			pstmt.setString(2, entity.getPassword());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "user_account"));
		} catch (SQLException e) {
			throw new RuntimeException("can't insert UserAccount entity", e);
		}
	}

	@Override
	public void update(UserAccount entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("update user_account set login=?, password=? where id=?");
			pstmt.setString(1, entity.getLogin());
			pstmt.setString(2, entity.getPassword());
			pstmt.setInt(3, entity.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("can't update UserAccount entity", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("delete from user_account where id=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("can't delete UserAccount entity", e);
		}

	}

	@Override
	public UserAccount getById(Integer id) {
		UserAccount entity = null;
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from user_account where id=?");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				entity = rowToEntity(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't get UserAccount entity by id", e);
		}

		return entity;
	}

	@Override
	public List<UserAccount> getAll() {
		List<UserAccount> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			ResultSet rs = c.createStatement().executeQuery("select * from user_account");
			while (rs.next()) {
				UserAccount entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select UserAccount entities", e);
		}

		return entitiesList;
	}

	private UserAccount rowToEntity(ResultSet rs) throws SQLException {
		UserAccount entity = new UserAccount();
		entity.setId(rs.getInt("id"));
		entity.setLogin(rs.getString("login"));
		entity.setPassword(rs.getString("password"));
		return entity;
	}

	@Override
	public List<UserAccount> find(TableStateDto tableStateDto) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public int count() {
		throw new RuntimeException("not implemented");
	}
}
