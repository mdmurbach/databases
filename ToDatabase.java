import java.io.*;
import java.util.*;
import java.sql.*;

public class ToDatabase {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");

		String url = "jdbc:postgresql://localhost/test";
		Properties props = new Properties();
		props.setProperty("user","admin");
		props.setProperty("password","0908107");
		props.setProperty("ssl","true");
		
		Connection db = DriverManager.getConnection(url,props);
		
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM test");
		while (rs.next()) {
			System.out.println("  " + rs.getString(1)+ ".  " + rs.getString(2));
		} 
		rs.close();
		st.close(); 
	}
}