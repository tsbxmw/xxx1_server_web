package com.json.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.PrintWriter;

import com.json.service.JsonService;
import com.mytest.Connect;
import com.json.service.*;
public class JsonServlet extends HttpServlet {
	private JsonService service;
	private Connect conn = new Connect();
	
	/**
	 * Constructor of the object.
	 */
	public JsonServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
	
		String jsonString = "hello world";
		String username= "debug";
		String password= "debug";
		String usercity= "debug";
		String createdatebase = "debug";
		String friends = "debug";
		
	
		
		PrintWriter out = response.getWriter();
		///////
		boolean result ;

		String user = request.getParameter("user");
		if(user != null)
		{
			username = conn.get_username(user);
			out.println(username);
		}
		String pass = request.getParameter("pass");
		if(pass!=null){
			password = conn.get_password(pass);
			out.println(password);
		}
			
		
		String user_register = request.getParameter("user_register");
		String pass_register = request.getParameter("pass_register");
		String user_city = request.getParameter("user_city");
		if(user_register !=null && pass_register!=null && user_city != null){
			result = conn.get_userregister(user_register,pass_register,user_city);
			if(result!=true)
				out.println(false);
			else
				out.println(true);
			
		}else if(user_city != null){
			usercity = conn.get_usercity(user_city);
			out.println(usercity);
		}
		
		String friend1 = request.getParameter("friend1");
 		String friend2 = request.getParameter("friend2");
		if(friend1 != null && friend2 != null){
			friends = conn.get_friends(friend1, friend2);
			out.println(friends	);
		}
		
		String databasename= request.getParameter("datebase");
		if(databasename != null){
			boolean hello = conn.create_table(databasename);
			out.println(hello);
		}
		String databasename2 = request.getParameter("databasename2");
		if(databasename2 != null){
			boolean hello = conn.create_table_2lines(databasename2);
			out.println(hello);
		}
		
		String makefriend1 = request.getParameter("makefriend1");
		String makefriend2 = request.getParameter("makefriend2");
		if(makefriend1 != null && makefriend2 != null){
			boolean hello = conn.makefriend(makefriend1,makefriend2);
			out.println(hello);
		}
		
		String deletefriend1 = request.getParameter("deletefriend1");
		String deletefriend2 = request.getParameter("deletefriend2");
		if(deletefriend1 != null && deletefriend2 != null){
			boolean hello = conn.delete_friends(deletefriend1, deletefriend2);
			out.println(hello);
		}
		
		String friendall = request.getParameter("friendall");
		if(friendall != null){
			String [] friend_list = new String [1000];
			for(int i=0;i<1000;i++)
				friend_list[i] = null;
			friend_list = conn.get_all_friends(friendall);
			for (int i=0;i<friend_list.length;i++){
				if(friend_list[i]!=null)
					out.println(friend_list[i]);
			}
				
		}
		
		String user1_msg = request.getParameter("msg_user1");
		String user2_msg = request.getParameter("msg_user2");
		String message = request.getParameter("message");
		if(user1_msg!=null && user2_msg!=null ){
			boolean hello = false;
			String message_get = null;
			if(message != null){
				hello = conn.send_message(user1_msg, user2_msg, message);
				out.println(hello);
			}
			else{
				message_get = conn.get_message(user1_msg, user2_msg);
				out.println(message_get);
			}
		}
		String user1_delmsg = request.getParameter("msg_deluser1");
		String user2_delmsg = request.getParameter("msg_deluser2");
		if(user1_delmsg!=null && user2_delmsg!=null){
			boolean hello = conn.delete_message(user1_delmsg, user2_delmsg);
			out.println(hello);
		}
		
		String user1_upmsg = request.getParameter("msg_updateuser1");
		String user2_upmsg = request.getParameter("msg_updateuser2");
		String msg_update = request.getParameter("msg_update");
		if(user1_upmsg!=null && user2_upmsg!=null && msg_update !=null){
			boolean hello = conn.update_message(user1_upmsg, user2_upmsg,msg_update);
			
			out.println(hello);
		}
		
		String drop_table = request.getParameter("drop_table");
		if(drop_table != null){
			boolean hello = conn.drop_table(drop_table);
			out.println(hello);
		}
		
		String deletealltableinfo = request.getParameter("delete_all_userinfo");
		if(deletealltableinfo!=null)
		{
			boolean hello = conn.delete_all();
			out.print(hello);
		}
		
		
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		service = new JsonService();
	}

}
