package com.visitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dbconnection.dbconnection;

/**
 * Servlet implementation class vehicleoperations
 */
@WebServlet("/vehicleoperations")
public class vehicleoperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		try
		{
			Connection con = dbconnection.getConnection();
			Date date=new Date();
			Date date1=new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
			String action=req.getParameter("func");
			
			
			if(action.equals("register"))
			{
				int slot_id,floor_id,user_id;
				String vehicle_type=req.getParameter("vehicle");
				String name=req.getParameter("user_name");
				String email=req.getParameter("usr_id");
				String passwd=req.getParameter("passwd");
				String phone=req.getParameter("phone");
				String query;
				if(vehicle_type.equals("Bike"))
					query="select slots.slot_id,floors.floor_id from slots inner join floors on floors.floor_id=slots.floor_id where slots.is_booked='0' and floors.capacity>'0' and slots.reserved_user_id='0' and floors.vehicle_type='Bike' and floors.is_full!='1'";
				else 
					query="select slots.slot_id,floors.floor_id from slots inner join floors on floors.floor_id=slots.floor_id where slots.is_booked='0' and floors.capacity>'0' and slots.reserved_user_id='0' and floors.vehicle_type='Car' and floors.is_full!='1'";
				
				PreparedStatement st1=con.prepareStatement(query);
				ResultSet rs1=st1.executeQuery();
				if(!(rs1.next()))
				{
					resp.getWriter().write("failure");
				}
				else
				{
					slot_id=rs1.getInt(1);
					floor_id=rs1.getInt(2);
				
				PreparedStatement st2=con.prepareStatement("insert into users(uname,passwd,contact_num) values(?,?,?)");
				st2.setString(1, name);
				st2.setString(2,passwd);
				st2.setString(3, phone);
				st2.executeUpdate(); 
				
				
				Statement st3=con.createStatement();
				ResultSet rs=st3.executeQuery("select user_id from users where contact_num="+phone);
				rs.next();
				user_id=rs.getInt(1);
				
				
				PreparedStatement st4=con.prepareStatement("update slots set reserved_user_id=? where slot_id=? and floor_id=?");
				st4.setInt(1,user_id);
				st4.setInt(2,slot_id);
				st4.setInt(3,floor_id);
				st4.executeUpdate(); 
				
				PreparedStatement st5=con.prepareStatement("update floors set is_full='1' where ((select count(slots.is_booked) from slots where slots.is_booked='1' and slots.reserved_user_id='0' and slots.floor_id=?)+(select count(reserved_user_id) from slots where slots.reserved_user_id!='0' and slots.floor_id=?))=floors.capacity and floors.floor_id=?");
				st5.setInt(1, floor_id);
				st5.setInt(2, floor_id);
				st5.setInt(3, floor_id);
				st5.executeUpdate();
				
				HttpSession session=req.getSession();
				session.setAttribute("usr_id",user_id);
				
				resp.getWriter().write(user_id+" - "+slot_id); 
				
				st2.close();
				st3.close();
				st4.close();
				st5.close();
				
				}
				st1.close(); 
			}
			
			else if(action.equals("addnewslots"))
			{
				String query,qry;int count,slot_id,floor_id;
				String vehicle_type=req.getParameter("vehicle");
				int  user_id=Integer.parseInt(req.getParameter("usr_id"));
				if(vehicle_type.equals("Bike"))
				query="select count(slots.slot_id) from slots inner join floors on slots.floor_id=floors.floor_id where floors.vehicle_type='Bike' and slots.reserved_user_id="+user_id;
				else 
				query="select count(slots.slot_id) from slots inner join floors on slots.floor_id=floors.floor_id where floors.vehicle_type='Car' and slots.reserved_user_id="+user_id;
				
			Statement st1=con.createStatement();
			ResultSet rs=st1.executeQuery(query);
			rs.next();
			count=rs.getInt(1);
			System.out.println(count);
			
			if(count>=2)
			{
				System.out.println(count);
				resp.getWriter().write("failure"); 
			}
			else
			{
				if(vehicle_type.equals("Bike"))
					qry="select slots.slot_id,floors.floor_id from slots inner join floors on floors.floor_id=slots.floor_id where slots.is_booked='0' and floors.capacity>'0' and slots.reserved_user_id='0' and floors.vehicle_type='Bike' and floors.is_full!='1'";
				else 
					qry="select slots.slot_id,floors.floor_id from slots inner join floors on floors.floor_id=slots.floor_id where slots.is_booked='0' and floors.capacity>'0' and slots.reserved_user_id='0' and floors.vehicle_type='Car' and floors.is_full!='1'";
				Statement st2=con.createStatement();
				ResultSet rs1=st1.executeQuery(qry);
				if(!(rs1.next()))
				{
					resp.getWriter().write("failure1");
				}
				else
				{
					slot_id=rs1.getInt(1);
					floor_id=rs1.getInt(2);
					
					PreparedStatement st3=con.prepareStatement("update slots set reserved_user_id=? where slot_id=? and floor_id=?");
					st3.setInt(1, user_id);
					st3.setInt(2, slot_id);
					st3.setInt(3, floor_id); 
					st3.executeUpdate(); 
					
					PreparedStatement st4=con.prepareStatement("update floors set is_full='1' where ((select count(slots.is_booked) from slots where slots.is_booked='1' and slots.reserved_user_id='0' and slots.floor_id=?)+(select count(reserved_user_id) from slots where slots.reserved_user_id!='0' and slots.floor_id=?))=floors.capacity and floors.floor_id=?");
					st4.setInt(1,floor_id);
					st4.setInt(2,floor_id);
					st4.setInt(3,floor_id);
					st4.executeUpdate(); 
					
					HttpSession session=req.getSession();
					session.setAttribute("usr_id",user_id);
					resp.getWriter().print(slot_id); 
					
					st3.close();
					st4.close();
				}
				st2.close();
			}
			st1.close();
		
			}
			else if(action.equals("signin"))
			{
				int  user_id=Integer.parseInt(req.getParameter("user_id"));
				String password=req.getParameter("passwd");
				String method=req.getParameter("method");
				
				PreparedStatement st=con.prepareStatement("select * from users where user_id=? and passwd=?");
				st.setInt(1, user_id);
				st.setString(2, password);
				ResultSet rs=st.executeQuery();
				if(!(rs.next()))
				resp.getWriter().write("failure"); 
				else
				{
					HttpSession session=req.getSession();
					session.setAttribute("user_id",user_id);
				    if(method.equals("register"))
				    resp.getWriter().write("success1");
					else
					resp.getWriter().write("success2");
				}
				st.close();
			}
			else if(action.equals("park"))
			{
				String vehicle_type=req.getParameter("vehicle");
				String vehicle_num=req.getParameter("vehicle_no");
				String contact_num=req.getParameter("telphone");
				String query;int slot_id,floor_id;
				if(vehicle_type.equals("Bike"))
					query="select slots.slot_id,floors.floor_id from slots inner join floors on floors.floor_id=slots.floor_id where slots.is_booked='0' and floors.capacity>'0' and slots.reserved_user_id='0' and floors.vehicle_type='Bike' and floors.is_full!='1'";
				else 
					query="select slots.slot_id,floors.floor_id from slots inner join floors on floors.floor_id=slots.floor_id where slots.is_booked='0' and floors.capacity>'0' and slots.reserved_user_id='0' and floors.vehicle_type='Car' and floors.is_full!='1'";
				
				PreparedStatement st1=con.prepareStatement(query);
				ResultSet rs1=st1.executeQuery();
				if(!(rs1.next()))
				{
					resp.getWriter().write("failure");
				}
				else
				{
					slot_id=rs1.getInt(1);
					floor_id=rs1.getInt(2);
					PreparedStatement st2=con.prepareStatement("update slots set is_booked='1' where slot_id=? and floor_id=?");
					st2.setInt(1,slot_id);
					st2.setInt(2,floor_id);
					st2.executeUpdate(); 
					
					PreparedStatement st3=con.prepareStatement("update floors set is_full='1' where ((select count(slots.is_booked) from slots where slots.is_booked='1' and slots.reserved_user_id='0' and slots.floor_id=?)+(select count(reserved_user_id) from slots where slots.reserved_user_id!='0' and slots.floor_id=?))=floors.capacity and floors.floor_id=?");
					st3.setInt(1, floor_id);
					st3.setInt(2, floor_id);
					st3.setInt(3, floor_id);
					st3.executeUpdate();
					
					PreparedStatement st4=con.prepareStatement("insert into parkingrecords (slot_id,user_id,vehicle_num,contact_num,entry,charge) values(?,?,?,?,?,?)");
					st4.setInt(1,slot_id);
					st4.setInt(2,0);
					st4.setString(3, vehicle_num);
					st4.setString(4,contact_num); 
					st4.setString(5, formatter.format(date));
					st4.setInt(6, 0);
					st4.executeUpdate();
					resp.getWriter().print(slot_id);
					
					st2.close();
					st3.close();
					st4.close();
				}
				st1.close();
				
				
				
			}
			else if(action.equals("parkuser"))
			{
				int  user_id=Integer.parseInt(req.getParameter("email"));
				String vehicle_num=req.getParameter("vehic_num");
				String vehicle_type=req.getParameter("vehic_typ");
				String query1,contact_num;int slot_id,floor_id;
				if(vehicle_type.equals("Bike"))
					query1="select slots.slot_id,floors.floor_id from slots inner join floors on floors.floor_id=slots.floor_id where slots.is_booked='0' and floors.capacity>'0' and floors.vehicle_type='Bike' and slots.reserved_user_id="+user_id;
				else
					query1="select slots.slot_id,floors.floor_id from slots inner join floors on floors.floor_id=slots.floor_id where slots.is_booked='0' and floors.capacity>'0' and floors.vehicle_type='Car' and slots.reserved_user_id="+user_id;
				
				PreparedStatement st=con.prepareStatement(query1);
				ResultSet rs=st.executeQuery(); 
				
				if(!(rs.next()))
				{
					resp.getWriter().write("failure");
				}
				else
				{
					slot_id=rs.getInt(1);
					floor_id=rs.getInt(2); 
					
					Statement st1=con.createStatement();
					ResultSet rs1=st1.executeQuery("select contact_num from users where user_id="+user_id);
					rs1.next();
					contact_num=rs1.getString(1);
					System.out.println(contact_num);
					PreparedStatement st2=con.prepareStatement("insert into parkingrecords (slot_id,user_id,vehicle_num,contact_num,entry,charge) values(?,?,?,?,?,?)");
					st2.setInt(1, slot_id);
					st2.setInt(2,user_id);
					st2.setString(3, vehicle_num);
					st2.setString(4,contact_num); 
					st2.setString(5, formatter.format(date));
					st2.setInt(6, 0);
					st2.executeUpdate();
					
					PreparedStatement st3=con.prepareStatement("update slots set is_booked='1' where slot_id=? and floor_id=?");
					st3.setInt(1, slot_id); 
					st3.setInt(2, floor_id);
					st3.executeUpdate(); 
					
					resp.getWriter().print(slot_id);
					
					st1.close();
					st2.close();
					st3.close();
				}
				
				st.close(); 
			}
			else if(action.equals("exitvehicle"))
			{
				String vehic_num=req.getParameter("vehic_num"); 
				int parkingrec_id,slot_id,charge=0;
				
				PreparedStatement st1=con.prepareStatement("select parkingrec_id,slot_id,entry from parkingrecords where charge='0' and vehicle_num=?");
				st1.setString(1, vehic_num);
				ResultSet rs1=st1.executeQuery();
				
				if(!(rs1.next()))
				{
					resp.getWriter().write("failure");
				}
				else
				{
					parkingrec_id=rs1.getInt(1);
					slot_id=rs1.getInt(2); 
					String entry_time=rs1.getString(3);
					
					Statement st2=con.createStatement();
					ResultSet rs2=st2.executeQuery("select floors.vehicle_type from floors inner join slots on floors.floor_id=slots.floor_id where slots.slot_id="+slot_id);
					rs2.next();
					String vehic_type=rs2.getString(1);
					
					
					String exittime=formatter.format(date1);
					Date enter_time=formatter.parse(entry_time);
					Date exit_time=formatter.parse(exittime); 
					long time_diff=exit_time.getTime()-enter_time.getTime();
					long time_diff_Mins = (time_diff / (60 * 1000))% 60;
					int mins=(int)time_diff_Mins;
					
					if(vehic_type.equals("Bike"))
					charge=50+mins*10;
					else
					charge=100+mins*10;
					
					PreparedStatement st3=con.prepareStatement("update slots set is_booked='0' where slot_id="+slot_id);
					st3.executeUpdate(); 
					
					PreparedStatement st4=con.prepareStatement("update parkingrecords set exitrec=?,charge=? where charge='0' and vehicle_num=?");
					st4.setString(1, exittime);
					st4.setInt(2, charge);
					st4.setString(3, vehic_num);
					st4.executeUpdate(); 
					
					resp.getWriter().print(parkingrec_id);
					
					st2.close();
					st3.close();
					st4.close();
					
				}
				st1.close();
				
				
			}
			else if(action.equals("exit"))
			{
				String vehic_num=req.getParameter("slotno");
                int parkingrec_id,slot_id,charge=0;
				
				PreparedStatement st1=con.prepareStatement("select parkingrec_id,slot_id,entry from parkingrecords where charge='0' and vehicle_num=?");
				st1.setString(1, vehic_num);
				ResultSet rs1=st1.executeQuery();
				
				if(!(rs1.next()))
				{
					resp.getWriter().write("failure");
				}
				else
				{
					parkingrec_id=rs1.getInt(1);
					slot_id=rs1.getInt(2); 
					String entry_time=rs1.getString(3);
					
					Statement st2=con.createStatement();
					ResultSet rs2=st2.executeQuery("select floors.vehicle_type,floors.floor_id from floors inner join slots on floors.floor_id=slots.floor_id where slots.slot_id="+slot_id);
					rs2.next();
					String vehic_type=rs2.getString(1);
					int floor_id=rs2.getInt(2);
				
					String exittime=formatter.format(date1);
					Date enter_time=formatter.parse(entry_time);
					Date exit_time=formatter.parse(exittime); 
					long time_diff=exit_time.getTime()-enter_time.getTime();
					long time_diff_Mins = (time_diff / (60 * 1000))% 60;
					int mins=(int)time_diff_Mins;
					
					if(vehic_type.equals("Bike"))
					charge=50+mins*10;
					else
					charge=100+mins*10;
					
					PreparedStatement st3=con.prepareStatement("update slots set is_booked='0' where slot_id="+slot_id);
					st3.executeUpdate();
					
					PreparedStatement st4=con.prepareStatement("update parkingrecords set exitrec=?,charge=? where charge='0' and vehicle_num=?");
					st4.setString(1, exittime);
					st4.setInt(2, charge);
					st4.setString(3, vehic_num);
					st4.executeUpdate(); 
					
					PreparedStatement st5=con.prepareStatement("update floors set is_full='0' where ((select count(slots.is_booked) from slots where slots.is_booked='1' and slots.floor_id=?)+(select count(reserved_user_id) from slots where slots.reserved_user_id!='0' and slots.floor_id=?))!=floors.capacity and floors.floor_id=?");
					st5.setInt(1,floor_id);
					st5.setInt(2, floor_id);
					st5.setInt(3, floor_id);
					st5.executeUpdate();
					
					resp.getWriter().print(parkingrec_id);
					
					st2.close();
					st3.close();
					st4.close();
					st5.close();
				}
				st1.close();
			}
			
		}
		catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
