package by.grsu.streha.weather.web.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;

import by.grsu.streha.weather.db.dao.IDao;
import by.grsu.streha.weather.db.dao.impl.WeatherDaoImpl;
import by.grsu.streha.weather.db.dao.impl.CountryDaoImpl;
import by.grsu.streha.weather.db.dao.impl.CityDaoImpl;
import by.grsu.streha.weather.db.dao.impl.UserAccountDaoImpl;
import by.grsu.streha.weather.db.model.Weather;
import by.grsu.streha.weather.db.model.Country;
import by.grsu.streha.weather.db.model.City;
import by.grsu.streha.weather.db.model.UserAccount;
import by.grsu.streha.weather.web.dto.WeatherDto;
import by.grsu.streha.weather.web.dto.CountryDto;
import by.grsu.streha.weather.web.dto.CityDto;
import by.grsu.streha.weather.web.dto.TableStateDto;

public class WeatherServlet extends AbstractListServlet {
	private static final IDao<Integer, Weather> weatherDao = WeatherDaoImpl.INSTANCE;
	private static final IDao<Integer, Country> countryDao = CountryDaoImpl.INSTANCE;
	private static final IDao<Integer, City> cityDao = CityDaoImpl.INSTANCE;
	private static final IDao<Integer, UserAccount> userAccountDao = UserAccountDaoImpl.INSTANCE;

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
		int totalCalendar = weatherDao.count(); // get count of ALL items

		final TableStateDto tableStateDto = resolveTableStateDto(req, totalCalendar); // init TableStateDto for specific
																					// Servlet and saves it in current
																					// request using key
																					// "currentPageTableState" to be
																					// used by 'paging' component

		List<Weather> weather = weatherDao.find(tableStateDto); // get data using paging and sorting params

		List<WeatherDto> dtos = weather.stream().map((entity) -> {
			WeatherDto dto = new WeatherDto();
			// copy necessary fields as-is
			dto.setId(entity.getId());
			dto.setTempreture(entity.getTempreture());
			dto.setWeather(entity.getWeather());			
			dto.setDate(entity.getDate());

			// build data for complex fields
			City city = cityDao.getById(entity.getCityId());
			dto.setCityName(city.getName());
						
			UserAccount user = userAccountDao.getById(entity.getCreatorId());
			dto.setCreatorLogin(user.getLogin());
			return dto;
		}).collect(Collectors.toList());

		req.setAttribute("list", dtos); // set data as request attribute (like "add to map") to be used later in JSP
		req.getRequestDispatcher("list.jsp").forward(req, res); // delegate request processing to JSP
	}
	
	private void handleEditView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String weatherIdStr = req.getParameter("id");
		WeatherDto dto = new WeatherDto();
		if (!Strings.isNullOrEmpty(weatherIdStr)) {
			// object edit
			Integer weatherId = Integer.parseInt(weatherIdStr);
			Weather entity = weatherDao.getById(weatherId);
			dto.setId(entity.getId());
			dto.setCityId(entity.getCityId());
			dto.setTempreture(entity.getTempreture());
			dto.setWeather(entity.getWeather());			
			dto.setDate(entity.getDate());			
			dto.setCreatorId(entity.getCreatorId());
		}
		req.setAttribute("dto", dto);
		req.setAttribute("allCountryes", getAllCountryesDtos());
		req.setAttribute("allCityes", getAllCityesDtos());
		req.getRequestDispatcher("edit.jsp").forward(req, res);
	}
	
	private List<CountryDto> getAllCountryesDtos() {
		return countryDao.getAll().stream().map((entity) -> {
			CountryDto dto = new CountryDto();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			return dto;
		}).collect(Collectors.toList());
	}
	
	private List<CityDto> getAllCityesDtos() {
		return cityDao.getAll().stream().map((entity) -> {
			CityDto dto = new CityDto();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			return dto;
		}).collect(Collectors.toList());
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doPost");
		Weather weather = new Weather();
		String weatherIdStr = req.getParameter("id");
		String cityIdStr = req.getParameter("cityId");
		String creatorIdStr = req.getParameter("creatorId");
		String tempreture = req.getParameter("tempreture");

		weather.setCityId(cityIdStr == null ? null : Integer.parseInt(cityIdStr));
		weather.setTempreture(tempreture == null ? null : Integer.parseInt(tempreture));
		weather.setWeather(req.getParameter("weather"));
		weather.setCreatorId(creatorIdStr == null ? null : Integer.parseInt(creatorIdStr));
		if (Strings.isNullOrEmpty(weatherIdStr)) {
			// new entity
			weather.setDate(new Timestamp(new Date().getTime()));
			weatherDao.insert(weather);
		} else {
			// updated entity
			weather.setDate(new Timestamp(new Date().getTime()));
			weather.setId(Integer.parseInt(weatherIdStr));
			weatherDao.update(weather);
		}
		res.sendRedirect("/weather"); // will send 302 back to client and client will execute GET /car
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doDelete");
		weatherDao.delete(Integer.parseInt(req.getParameter("id")));
	}
}
