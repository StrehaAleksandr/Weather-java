package by.grsu.streha.weather;

import by.grsu.streha.weather.db.model.UserAccount;

public class Main {
	public static void main(String[] args) {
		UserAccount user = new UserAccount();
		user.setId(1);
		user.setLogin("user");
		user.setPassword("user");
		System.out.println(user.toString());
	}
}
