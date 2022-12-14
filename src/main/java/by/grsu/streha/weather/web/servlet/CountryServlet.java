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
import by.grsu.streha.weather.db.model.Country;
import by.grsu.streha.weather.web.dto.CountryDto;
import by.grsu.streha.weather.web.dto.SortDto;
import by.grsu.streha.weather.web.dto.TableStateDto;

public class CountryServlet extends AbstractListServlet {
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
		int totalCalendar = countryDao.count(); // get count of ALL items

		final TableStateDto tableStateDto = resolveTableStateDto(req, totalCalendar);
		
		List<Country> countryes = countryDao.find(tableStateDto); // get data

		List<CountryDto> dtos = countryes.stream().map((entity) -> {
			CountryDto dto = new CountryDto();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			
			return dto;
		}).collect(Collectors.toList());

		req.setAttribute("list", dtos);
		req.getRequestDispatcher("country-list.jsp").forward(req, res);
	}
	
	private void handleEditView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String countryIdStr = req.getParameter("id");
		CountryDto dto = new CountryDto();
		if (!Strings.isNullOrEmpty(countryIdStr)) {
			Integer countryId = Integer.parseInt(countryIdStr);
			Country entity = countryDao.getById(countryId);
			dto.setId(entity.getId());
			dto.setName(entity.getName());
		}
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("country-edit.jsp").forward(req, res);
	}
		
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doPost");
		Country country = new Country();
		String countryIdStr = req.getParameter("id");
		country.setName(req.getParameter("name"));
		if (Strings.isNullOrEmpty(countryIdStr)) {
			countryDao.insert(country);
		} else {
			country.setId(Integer.parseInt(countryIdStr));
			countryDao.update(country);
		}
		res.sendRedirect("/country");
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doDelete");
		countryDao.delete(Integer.parseInt(req.getParameter("id")));
	}
}
