package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC {
	public static Credentials getCredentials() {
		String url = "jdbc:mysql://localhost:3306/mail.ru?useSSL=false";
		String user = "root";
		String password = "root";
		Credentials credentials = null;

		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			// our SQL SELECT query.
			String query = "SELECT * FROM credentials";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			
			
			// iterate through the java resultset
			while (rs.next()) {
				int id = rs.getInt("id");
				String login = rs.getString("login");
				String password1 = rs.getString("password");

				// print the results
				System.out.format("%s, %s, %s\n", id, login, password1);
				credentials = new Credentials(login, password1);
			}
			st.close();
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return credentials;
	}
}