package com.log.dao;

import java.sql.*;

import javax.servlet.annotation.WebServlet;

public class LoginDAO {
	String query="select * from users where uname=? and passwd=?";
	String url="jdbc:mysql://localhost:3306/parkingsystem";
	String usrname="root";
    String passwd="Magicalkd@11";
	public boolean checkcredentials(String user_name,String pass) throws SQLException 
	{
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection(url, usrname, passwd);
				PreparedStatement st=con.prepareStatement(query);
				st.setString(1, user_name);
				st.setString(2, pass);
				ResultSet rs=st.executeQuery();
				if(rs.next())
					return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
	    
		return false;
	}

}
