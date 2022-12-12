package by.grsu.streha.weather.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.grsu.streha.weather.db.dao.IDao;
import by.grsu.streha.weather.db.dao.impl.CountryDaoImpl;
import by.grsu.streha.weather.db.model.Country;
import by.grsu.streha.weather.web.ValidationUtils;

public class CountryServlet extends HttpServlet {
	private static final IDao<Integer, Country> countryDao = CountryDaoImpl.INSTANCE;

	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String paramId = req.getParameter("id");

		// validation
		if (!ValidationUtils.isInteger(paramId)) {
			res.sendError(400); // send HTTP status 400 and close response
			return;
		}

		Integer countryId = Integer.parseInt(paramId); // read request parameter
		Country countryById = countryDao.getById(countryId); // from DB

		res.setContentType("text/html");// setting the content type

		PrintWriter pw = res.getWriter();// get the stream to write the data

		// writing html in the stream
		pw.println("<html><body>");

		if (countryById == null) {
			pw.println("no country by id=" + countryId);
		} else {
			pw.println(countryById.toString());
		}

		pw.println("</body></html>");
		pw.close();// closing the stream
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();// get the stream to write the data
		pw.println("<html><body>");
		try {
			String paramName = req.getParameter("name");
			Country countryEntity = new Country();
			countryEntity.setName(paramName);
			countryDao.insert(countryEntity);
			pw.println("Saved:" + countryEntity);

		} catch (Exception e) {
			pw.println("Error:" + e.toString());
		}

		pw.println("</body></html>");
		pw.close();
	}
}
