package PFA;

import java.sql.DriverManager; 
import java.sql.*;
public class Connect {
	public String url; //"jdbc:post gresql://localhost:54/Ecole";
	public String user; //= "DB1";
	public String pwd;// = "database";
   public Connection conn;	
    
    public Connect(){
    	
     	this.url="jdbc:oracle:thin:@localhost:1521:xe";
    	this.user="PFA";
    	this.pwd="pfa";
    	
    	try {
    		Class.forName("oracle.jdbc.OracleDriver");
    	 conn=(Connection) DriverManager.getConnection(url, user, pwd);
    		
    		
    	}catch(Exception ex) {
    		//System.out.println("Erreur de connexion "+ ex.getMessage());
    		System.out.println(ex);
    	}
    	

}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	} }  
