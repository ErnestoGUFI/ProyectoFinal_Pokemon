package DataBase;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.badlogic.gdx.ai.pfa.Connection;

public class DataBase {
	
	private Connection connection;
	
	public DataBase() {
		
		 try {
			connection = (Connection) DriverManager.getConnection("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void guardarScore() {
		
		String sql;
	}
}
