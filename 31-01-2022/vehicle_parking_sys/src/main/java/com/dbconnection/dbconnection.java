package com.dbconnection;
import java.sql.Connection;
import java.sql.DriverManager;
public class  dbconnection{
   static Connection con = null;
   public static Connection getConnection() {
      if (con != null)
    	  return con;
      String DBname = "parkingsystem";
      String User_name = "root";
      String password = "Magicalkd@11";
      return getConnection(DBname, User_name, password);
   }
   private static Connection getConnection(String databaseName, String User_Name, String password) {
      try {
         Class.forName("com.mysql.jdbc.Driver");
         con = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName + "?user=" + User_Name + "&password=" + password);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return con;
   }
}