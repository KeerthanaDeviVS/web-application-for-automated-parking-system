package com.flooroperations;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dbconnection.dbconnection;

/**
 * Servlet implementation class flooroperations
 */
@WebServlet("/flooroperations")
public class flooroperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out=resp.getWriter(); 
		try {
		Connection con = dbconnection.getConnection();
		String param_function=req.getParameter("param");
		
		if(param_function.equals("add"))
		{
			String vehicle_type=req.getParameter("vehicle");
			int floor_no=Integer.parseInt(req.getParameter("floor_id"));
			int parkinglot_id=Integer.parseInt(req.getParameter("lot_id"));
			int capacity=Integer.parseInt(req.getParameter("capacity"));
			
			PreparedStatement st1=con.prepareStatement("insert into floors(floor_name,parkinglot_id,vehicle_type,capacity,is_full) values (?,?,?,?,?)");
			st1.setString(1,"base"+floor_no);
			st1.setInt(2, parkinglot_id);
			st1.setString(3, vehicle_type);
			st1.setInt(4, capacity);
			st1.setInt(5, 0);
			st1.executeUpdate();
			
			
			PreparedStatement st2=con.prepareStatement("select floor_id from floors where floor_name=? and parkinglot_id=?");
			st2.setString(1, "base"+floor_no);
			st2.setInt(2, parkinglot_id);
			ResultSet rs=st2.executeQuery();
			rs.next();
			int floor_id=rs.getInt(1);
			
            
			PreparedStatement st3=con.prepareStatement("insert into slots(slot_id,floor_id,is_booked,reserved_user_id) values(?,?,?,?)");
			for(int indx=1;indx<=capacity;indx++)
			{
				String str=floor_id+""+floor_no+""+indx;
				int id=Integer.parseInt(str);  
				st3.setInt(1, id);
				st3.setInt(2, floor_id);
				st3.setInt(3, 0);
				st3.setInt(4, 0);
				st3.executeUpdate();
			}
			
			st1.close();
			st2.close();
			
			resp.getWriter().write("success");
		}
		else if(param_function.equals("addlot"))
		{
		     String parkinglot_name=req.getParameter("lot_name"); 
		     
		     PreparedStatement st1=con.prepareStatement("insert into parkinglot(parkinglot_name) values(?)");
		     st1.setString(1, parkinglot_name);
		     st1.executeUpdate(); 
		     
		     resp.getWriter().write("success"); 
		     
		}
		else if(param_function.equals("edit"))
		{
			String add_or_delete=req.getParameter("AddDelete");
			int floor_id=Integer.parseInt(req.getParameter("floor_id"));
			int slots=Integer.parseInt(req.getParameter("slots")); 
			
			Statement st1=con.createStatement();
			ResultSet rs=st1.executeQuery("select capacity,floor_name from floors where floor_id="+floor_id);
			System.out.println(floor_id);
			
			if(!rs.next())
		    resp.getWriter().write("failure");
			else
			{
				int capacity=rs.getInt(1);
				String floor_name=rs.getString(2); 
				
				String[] str=floor_name.split("(?<=\\D)(?=\\d)");
				int floor_no=Integer.parseInt(str[1]);
				
				PreparedStatement st2=con.prepareStatement("update floors set capacity=? where floor_id=?");
			    if(add_or_delete.equals("ADD_EXTRA_SLOTS"))
			    {
				st2.setInt(1,capacity+slots);
				st2.setInt(2, floor_id);
				st2.executeUpdate();
				
				PreparedStatement st3=con.prepareStatement("insert into slots(slot_id,floor_id,is_booked,reserved_user_id) values(?,?,?,?)");
		    	for(int indx=capacity+1;indx<=capacity+slots;indx++)
				{
		    		String str1=floor_id+""+floor_no+""+indx;
					int id=Integer.parseInt(str1);  
					st3.setInt(1, id);
					st3.setInt(2, floor_id);
					st3.setInt(3, 0);
					st3.setInt(4, 0);
					st3.executeUpdate();
				}
		    	
		    	PreparedStatement st4=con.prepareStatement("update floors set is_full='0' where floor_id=?");
		    	st4.setInt(1, floor_id); 
		    	st4.executeUpdate(); 
		    	
		    	st3.close();
		    	st4.close();
		    	
		    	resp.getWriter().write("success");
		    	
			    }
			    else if(add_or_delete.equals("DELETE_SLOTS") && capacity>=slots)
			    {
			    	st2.setInt(1,capacity-slots);
			    	st2.setInt(2, floor_id);
			    	st2.executeUpdate();
			    	
			    	PreparedStatement st3=con.prepareStatement("delete from slots where floor_id=? order by slot_id desc limit 1");
			    	while(slots!=0)
			    	{
			    		st3.setInt(1, floor_id);
			    		st3.executeUpdate();
			            slots-=1;
			    	}
			    	
			    	PreparedStatement st4=con.prepareStatement("update floors set is_full='1' where ((select count(slots.is_booked) from slots where slots.is_booked='1' and slots.reserved_user_id='0' and slots.floor_id=?)+(select count(reserved_user_id) from slots where slots.reserved_user_id!='0' and slots.floor_id=?))=floors.capacity and floors.floor_id=?");
					st4.setInt(1,floor_id);
					st4.setInt(2,floor_id);
					st4.setInt(3,floor_id);
					st4.executeUpdate(); 
					
			    	st3.close();
			    	st4.close();
			    	
			    	resp.getWriter().write("success");
			    }
			    else
			    {
			    	resp.getWriter().write("slotnot");
			    }
			    
			    st2.close();
				
			}
			st1.close();
			
		}
		else if(param_function.equals("viewhistory"))
		{
			String date=req.getParameter("dateday");
			PreparedStatement st1=con.prepareStatement("SELECT DATE(entry) from parkingrecords where DATE(entry)=?");
			st1.setString(1, date); 
			ResultSet rs=st1.executeQuery();
		    if(!(rs.next()))
			{
				resp.getWriter().write("failure");
			}
		    else {
				resp.getWriter().write(date);
			
		    }
		    st1.close();
		}
		else if(param_function.equals("totalamt"))
		{
			String date=req.getParameter("dateday"); 
			int total_amount=0;
			PreparedStatement st1=con.prepareStatement("select charge from parkingrecords where DATE(exitrec) = ?");
		    st1.setString(1, date);
		    ResultSet rs=st1.executeQuery();
		    if(!(rs.next()))
		    {
				resp.getWriter().print("failure");
			}
		    else {
			do
			{
			total_amount+=rs.getInt(1);
			
			}while(rs.next());
			resp.getWriter().print(total_amount);
		    }
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
