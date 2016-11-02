package test;
import java.sql.*;

public class DataBase {
	public static Connection getConnection(String dbName){
		Connection cn=null;
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
			cn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+dbName, "root", "123456");
		}catch(Exception e){
		}
		return cn;
	}
	public static Connection getConnection(){
		return getConnection("");
	}
}
