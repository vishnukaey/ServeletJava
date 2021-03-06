package com.Sample3.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class PainMedication
 */
public class PainMedication extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	Statement st;
	ResultSet rs;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PainMedication() {
        super();
        try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver found");
		} 
		catch (ClassNotFoundException e){
			System.out.println("Driver not found");
		}
		
		String url="jdbc:mysql://localhost:8889/ListTable";
		String user="root";
		String password="root"; 
		try{ 
			con=DriverManager.getConnection(url, user, password);
			st= con.createStatement();
			System.out.println("Connected Succesfully"); 
		}
		catch(SQLException e){
			System.out.println("Wrong");
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET working");
		DBConnector connect = new DBConnector();
		if (connect != null)
			System.out.println("Connection Created");
		Enumeration<String> parameterNames= request.getParameterNames();
		if (parameterNames != null)
			System.out.println("Connection Created");
		PrintWriter out=response.getWriter();
		String sa[]=new String[3];
		int i=0;
		PreparedStatement stmt = null;
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String[] p =request.getParameterValues(paramName);
			if(p.length>0){
			}
			sa[i]=p[0];
			i++;
		}
		try{
				stmt= con.prepareStatement("SELECT * FROM medication");
				System.out.println(stmt);
				rs= stmt.executeQuery();
		}
		catch(SQLException e){
			System.out.println(e);
		}
		try {
			String name;
			String color;
			String url;
			String form;
			Map<String, String> map = new HashMap<String, String>();
			JSONArray ar= new JSONArray();
			while(rs.next()) {
				name = rs.getString("name");
				color = rs.getString("color");
				url = rs.getString("imageUrl");
				form = rs.getString("form");
				map.put("name", name);
		        map.put("color", color);
		        map.put("imageUrl", url);
		        map.put("form", form);
				JSONObject json = new JSONObject();
		        json.accumulateAll(map);
		        ar.add(json);
				}
				out.println(ar);
				System.out.println(ar);
		} catch (SQLException e) {
			System.out.println(e);
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
