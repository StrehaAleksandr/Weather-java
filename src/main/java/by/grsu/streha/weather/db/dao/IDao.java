package by.grsu.streha.weather.db.dao;

import java.util.List;

import by.grsu.streha.weather.web.dto.TableStateDto;

public interface IDao<ID, TYPE> {
	void insert(TYPE t);

	void update(TYPE t);

	void delete(ID id);

	TYPE getById(ID id);

	List<TYPE> getAll();

	List<TYPE> find(TableStateDto tableStateDto);

	int count();
}
