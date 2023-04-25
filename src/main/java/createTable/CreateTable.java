package createTable;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
	static final String DB_URL = "jdbc:mysql://localhost:3306/crud";
	static final String USER = "root";
	static final String PASS = "";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();) {
			String sql = "CREATE TABLE users " + "(id  int(3) NOT NULL AUTO_INCREMENT ," + " name VARCHAR(250) NOT NULL,"
					+ "email VARCHAR(150) NOT NULL," + " country VARCHAR(100)," + "PRIMARY KEY(id))";
			stmt.executeUpdate(sql);
			System.out.println("Table Generated in the Database");

		} catch (SQLException e) {
			System.out.println("Error Generating Table");
			e.printStackTrace();
		}

	}
}
