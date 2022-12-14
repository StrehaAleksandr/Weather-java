package by.grsu.streha.weather.web.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;

import by.grsu.streha.weather.db.dao.IDao;
import by.grsu.streha.weather.db.dao.impl.CountryDaoImpl;
import by.grsu.streha.weather.db.dao.impl.CityDaoImpl;
import by.grsu.streha.weather.db.model.Country;
import by.grsu.streha.weather.db.model.City;
import by.grsu.streha.weather.web.dto.CityDto;
import by.grsu.streha.weather.web.dto.CountryDto;
import by.grsu.streha.weather.web.dto.SortDto;
import by.grsu.streha.weather.web.dto.TableStateDto;

public class CityServlet extends AbstractListServlet {
	private static final IDao<Integer, City> cityDao = CityDaoImpl.INSTANCE;
	private static final IDao<Integer, Country> countryDao = CountryDaoImpl.INSTANCE;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doGet");
		String viewParam = req.getParameter("view");
		if ("edit".equals(viewParam)) {
			handleEditView(req, res);
		} else {
			handleListView(req, res);
		}
	}
	
	private void handleListView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int totalCalendar = cityDao.count(); // get count of ALL items

		final TableStateDto tableStateDto = resolveTableStateDto(req, totalCalendar);
		
		List<City> cityes = cityDao.find(tableStateDto); // get data

		List<CityDto> dtos = cityes.stream().map((entity) -> {
			CityDto dto = new CityDto();
			dto.setId(entity.getId());
			dto.setName(entity.getName());

			Country country = countryDao.getById(entity.getCountryId());
			dto.setCountryName(country.getName());
			return dto;
		}).collect(Collectors.toList());

		req.setAttribute("list", dtos);
		req.getRequestDispatcher("city-list.jsp").forward(req, res);
	}
	
	private void handleEditView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String cityIdStr = req.getParameter("id");
		CityDto dto = new CityDto();
		if (!Strings.isNullOrEmpty(cityIdStr)) {
			Integer cityId = Integer.parseInt(cityIdStr);
			City entity = cityDao.getById(cityId);
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setCountryId(entity.getCountryId());
		}
		req.setAttribute("dto", dto);
		req.setAttribute("allCountryes", getAllCountryesDtos());
		req.getRequestDispatcher("city-edit.jsp").forward(req, res);
	}
	
	private List<CountryDto> getAllCountryesDtos() {
		return countryDao.getAll().stream().map((entity) -> {
			CountryDto dto = new CountryDto();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			return dto;
		}).collect(Collectors.toList());
	}

	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doPost");
		City city = new City();
		String cityIdStr = req.getParameter("id");
		String countryIdStr = req.getParameter("countryId");
		city.setName(req.getParameter("name"));
		city.setCountryId(countryIdStr == null ? null : Integer.parseInt(countryIdStr));
		if (Strings.isNullOrEmpty(cityIdStr)) {
			cityDao.insert(city);
		} else {
			city.setId(Integer.parseInt(cityIdStr));
			cityDao.update(city);
		}
		res.sendRedirect("/city");
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doDelete");
		cityDao.delete(Integer.parseInt(req.getParameter("id")));
	}
}
