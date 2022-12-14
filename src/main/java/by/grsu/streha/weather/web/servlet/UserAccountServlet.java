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
import by.grsu.streha.weather.db.dao.impl.UserAccountDaoImpl;
import by.grsu.streha.weather.db.model.UserAccount;
import by.grsu.streha.weather.web.dto.UserAccountDto;
import by.grsu.streha.weather.web.dto.SortDto;
import by.grsu.streha.weather.web.dto.TableStateDto;

public class UserAccountServlet extends AbstractListServlet {
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
		int totalCalendar = userAccountDao.count(); // get count of ALL items

		final TableStateDto tableStateDto = resolveTableStateDto(req, totalCalendar);
		
		List<UserAccount> accounts = userAccountDao.find(tableStateDto); // get data

		List<UserAccountDto> dtos = accounts.stream().map((entity) -> {
			UserAccountDto dto = new UserAccountDto();
			dto.setId(entity.getId());
			dto.setLogin(entity.getLogin());
			dto.setPassword(entity.getPassword());
			return dto;
		}).collect(Collectors.toList());

		req.setAttribute("list", dtos);
		req.getRequestDispatcher("userAccount-list.jsp").forward(req, res);
	}
	
	private void handleEditView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String userAccountIdStr = req.getParameter("id");
		UserAccountDto dto = new UserAccountDto();
		if (!Strings.isNullOrEmpty(userAccountIdStr)) {
			Integer userAccountId = Integer.parseInt(userAccountIdStr);
			UserAccount entity = userAccountDao.getById(userAccountId);
			dto.setId(entity.getId());
			dto.setLogin(entity.getLogin());
			dto.setPassword(entity.getPassword());
		}
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("userAccount-edit.jsp").forward(req, res);
	}
		
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doPost");
		UserAccount userAccount = new UserAccount();
		String userAccountIdStr = req.getParameter("id");
		userAccount.setLogin(req.getParameter("login"));
		userAccount.setPassword(req.getParameter("password"));
		if (Strings.isNullOrEmpty(userAccountIdStr)) {
			userAccountDao.insert(userAccount);
		} else {
			userAccount.setId(Integer.parseInt(userAccountIdStr));
			userAccountDao.update(userAccount);
		}
		res.sendRedirect("/userAccount");
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doDelete");
		userAccountDao.delete(Integer.parseInt(req.getParameter("id")));
	}
}
