package com.mytest;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.ResultSetInternalMethods;
import java.security.MessageDigest;

@SuppressWarnings("serial")
public class Connect extends HttpServlet{
		Connection conn = null;
		String sql,sql_info;
		String resultString ;
		// add the db connect string include host port dbname and ssl
		String url_read = "jdbc:mysql://db4free.net:3306/tsbx?useSSL=false";
		String username_db = "tsbx";
		String password_db = "mengwei";
		Statement  stmt;
		String [] friend_list = new String [1000];
		int result = 0,result_1 = 0;
		
		/*String url_read = "jdbc:mysql://r.rdc.sae.sona.con.cn:3307/app_tsbx";
		String url_write = "jdbc:mysql://w.rdc.sae.sona.con.cn:3307/app_tsbx";
		String username_db="wzy3mxx4yk";
		String password_db="jxh4li5xm3y4h44wmmi1y4k3x122mlwz540z04yw";
		*/
		
		public String md5string(String preString){
		
			String result = new String();
			try{
				
				MessageDigest mdInst = MessageDigest.getInstance("MD5");
				mdInst.update(preString.getBytes());
				byte[] md = mdInst.digest();
				StringBuffer buf = new StringBuffer("");
				int k ;
				for(int i=0;i<md.length;i++){
					k=md[i];
					byte byte0 = md[i];
					if(k<0)
						i+=256;
					if(k<16)
						buf.append("0");
					buf.append(Integer.toHexString(k));
				}
				result = buf.toString();
			}catch (Exception e){
				
			}
			return result;
		}
		//encryption the string by md5 
		public String encryption(String plainText) {
			String re_md5 = new String();
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(plainText.getBytes());
				byte b[] = md.digest();

				int i;

				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}

				re_md5 = buf.toString();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return re_md5;
		}
		
		//add	get user name func to the conncet	
                public String get_username(String username){
			try{
				connect();
				sql = "select username from login  where username='"+username+"'";
				//sql = "select password from login where username='"+username+"'";;
				if(-1 == -1){
					System.out.println("success select");
					ResultSet rs = (ResultSet) stmt.executeQuery(sql);
					while(rs.next()){
						resultString = rs.getString(1);
						System.out.println(rs.getString(1));
					}
					
				}
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			return resultString;
		}
		public String get_usercity(String username){
			try{
				
				connect();
				sql = "select usercity from userinfo where username='"+username+"'";
				//sql = "select * from login";
				if(-1 == -1){
					System.out.println("success select");
					ResultSet rs = (ResultSet) stmt.executeQuery(sql);
					while(rs.next()){
						resultString = rs.getString(1);
						System.out.println(rs.getString(1));
					}
					
				}
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			System.out.println(resultString);
			return resultString;
		}

		public String  get_password(String username){
			try{
			
				connect();
				sql = "select password from login where username='"+username+"'";
				//sql = "select * from login";
				if(-1 == -1){
					System.out.println("success select");
					ResultSet rs = (ResultSet) stmt.executeQuery(sql);
					while(rs.next()){
						resultString = rs.getString(1);
						System.out.println(rs.getString(1));
					}
					
				}
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			resultString = encryption(resultString);
			System.out.println(resultString);
			return resultString;
		}
		
		public String get_friends(String user1,String user2){
			
			try{
					
					connect();
					sql = "select user2 from friends where ( user1='"+user1+"' and user2='"+user2+"') or ( user2='"+user1+"' and user1='"+user2+"')";
					
					//sql = "select * from login";
					if(-1 == -1){
						System.out.println("success select");
						ResultSet rs = (ResultSet) stmt.executeQuery(sql);
						while(rs.next()){
							resultString = rs.getString(1);
							System.out.println(rs.getString(1));
						}
						
					}
					conn.close();
				}catch(Exception e){
					e.printStackTrace();
				}
	
				return resultString;
		}
		public String[] get_all_friends(String user1){
			 String [] friend_list_1 = new String [1000];
			try{
				
					for(int i=0;i<1000;i++)
						friend_list_1[i] = null;
					connect();
					sql = "select user2 from friends where user1='"+user1+"'";
					String sql_get = "select user1 from friends where user2='"+user1+"'";
					int number = 0;
					//sql = "select * from login";
					if(-1 == -1){
						System.out.println("success select - get all friends");
						ResultSet rs = (ResultSet) stmt.executeQuery(sql);
						while(rs.next()){
							friend_list_1[number] = rs.getString(1);
							number ++ ;
							System.out.println(rs.getString(1));
						}
						ResultSet rs2 = (ResultSet) stmt.executeQuery(sql_get);
						while(rs2.next()){
							friend_list_1[number] = rs2.getString(1);
							number ++ ;
							System.out.println(rs2.getString(1));
						}
					}
					conn.close();
				}catch(Exception e){
					e.printStackTrace();
				}
	
				return friend_list_1;
		}
		
		public boolean delete_friends(String user1,String user2){
			connect();
			try{
				sql = "delete from friends where ( user1='"+user1+"' and user2='"+user2+"') or ( user2='"+user1+"' and user1='"+user2+"')";
				
				//sql = "select * from login";
				if(-1 == -1){
					result = 0;
					System.out.println("success insert");
					result = stmt.executeUpdate(sql);
				}
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(result == 1)
				return true;
			return false;
		}
		
		public boolean makefriend(String user1,String user2){
			
			connect();
			try{
				sql = "insert into friends values('"+user1+"','"+user2+"')";
				
				//sql = "select * from login";
				if(-1 == -1){
					result = 0;
					System.out.println("success insert");
					result = stmt.executeUpdate(sql);
				}
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(result == 1)
				return true;
			return false;
	}
		
		public boolean get_userregister(String username,String password,String usercity){
			try{
				
				connect();
				sql = "insert into login values('"+username+"','"+password+"')";
				sql_info =  "insert into userinfo values('"+username+"','"+usercity+"')";
				//sql = "select * from login";
				result = 0;
				result_1 = 0;
				result = stmt.executeUpdate(sql);
				result_1 = stmt.executeUpdate(sql_info);
				System.out.println(result+result_1);
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(result == 1)
				return true;
			return false;
		}
		
		public boolean create_table(String databasename) {
			// TODO Auto-generated method stub
			try{
				
				connect();
				sql = "create table friends (user1 VARCHAR(100) NOT NULL , user2 VARCHAR(100) NOT NULL ,PRIMARY KEY ( user1 , user2 ))";
				result = stmt.executeUpdate(sql);
				System.out.println(result);
				conn.close();
				
			} catch (Exception e){
				e.printStackTrace();
			}
			if(result == 1 )
				return true;
			return false;
		}
		public boolean create_table_2lines(String databasename) {
			// TODO Auto-generated method stub
			try{
				
				connect();
				sql = "create table message ( user1 VARCHAR(100) NOT NULL , user2 VARCHAR(100) NOT NULL , msg VARCHAR(100) NOT NULL ,sendkey VARCHAR(100) NOT NULL,PRIMARY KEY ( user1 , user2 ) )";
				result = stmt.executeUpdate(sql);
				System.out.println(result);
				conn.close();
				
			} catch (Exception e){
				e.printStackTrace();
			}
			if(result == 1 )
				return true;
			return false;
		}
		public boolean drop_table(String databasename) {
			// TODO Auto-generated method stub
			try{
				
				connect();
				sql = "drop table message";
				result = stmt.executeUpdate(sql);
				System.out.println(result);
				conn.close();
				
			} catch (Exception e){
				e.printStackTrace();
			}
			if(result == 1 )
				return true;
			return false;
		}
		public String  get_message(String user1,String user2){
			String key = "0";
			try{
				resultString = null;
				connect();
				String get_sendkey = "select sendkey from message where user1='"+user1+"' and user2='"+user2+"'";
				ResultSet sendkey = (ResultSet) stmt.executeQuery(get_sendkey);
				while(sendkey.next()){
					key = sendkey.getString(1);
					System.out.println(key);
				}
				
				sql = "select msg from message where user1='"+user1+"' and user2='"+user2+"'";
				//sql = "select * from login";
				if(-1 == -1){
					System.out.println("success select msg");
					ResultSet rs = (ResultSet) stmt.executeQuery(sql);
					while(rs.next()){
						resultString = null;
						resultString = rs.getString(1);
						System.out.println(resultString);
					}
					
				}
				
				String setsendkey = "update message set sendkey='0' where user1='"+user1+"' and user2='"+user2+"'";
				stmt.executeUpdate(setsendkey);
				System.out.println("sendkey is 0 now");
				
				conn.close();
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
			if(key!=null && key.equals("0"))
			{
				System.out.println("message is already read");
				
				return key;
			}
			else
				return resultString;
		
			
		
		}
		
		public boolean send_message(String user1,String user2,String msg){
			try{
				
				connect();
				
				sql = "insert into message values('"+user1+"','"+user2+"','"+msg+"','1')";
				
				//sql = "select * from login";
				result = 0;
				result = stmt.executeUpdate(sql);
				System.out.println(result);
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(result == 1)
				return true;
			return false;
		}
		
		
		public boolean update_message(String user1,String user2,String msg){
			try{
				
				connect();
				
				sql = "update message set sendkey='1',msg='"+msg+"' where user1='"+user1+"' and user2='"+user2+"'";
				
				
				//sql = "select * from login";
				result = 0;
				result = stmt.executeUpdate(sql);
				System.out.println(result);
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(result == 1)
				return true;
			return false;
		}
		
		
		
		public boolean delete_message(String user1,String user2){
			try{
				
				connect();
				sql = "delete from message where user1='"+user1+"' and user2='"+user2+"'";
				//sql = "select * from login";
				result = 0;
				result_1 = 0;
				result = stmt.executeUpdate(sql);
				System.out.println(result);
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(result == 1)
				return true;
			return false;
		}
		public boolean delete_all(){
			try{
				
				connect();
				sql = "delete from message ";
				//sql = "select * from login";
				result = 0;
				result_1 = 0;
				result = stmt.executeUpdate(sql);
				
				System.out.println(result);
				sql = "delete from login";
				//sql = "select * from login";
				result = 0;
				result_1 = 0;
				result = stmt.executeUpdate(sql);
				
				System.out.println(result);
				sql = "delete from friends";
				//sql = "select * from login";
				result = 0;
				result_1 = 0;
				result = stmt.executeUpdate(sql);
				
				System.out.println(result);
				sql = "delete from userinfo";
				//sql = "select * from login";
				result = 0;
				result_1 = 0;
				result = stmt.executeUpdate(sql);
				
				System.out.println(result);
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(result == 1)
				return true;
			return false;
		}
		
		
		
		public boolean create_table_3lines(String databasename) {
			// TODO Auto-generated method stub
			try{
				
				connect();
				sql = "create table " + databasename + " (user VARCHAR(100) NOT NULL , user2 VARCHAR(100) NOT NULL ,PRIMARY KEY ( user1 , user2 ))";
				result = stmt.executeUpdate(sql);
				System.out.println(result);
				conn.close();
				
			} catch (Exception e){
				e.printStackTrace();
			}
			if(result == 1 )
				return true;
			return false;
		}
		
		
		public void connect(){
			try{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = (Connection) DriverManager.getConnection(url_read,username_db,password_db);
				stmt = (Statement) conn.createStatement();
				sql = "use tsbx";
				stmt.executeQuery(sql);
				result = 0;
				result_1 = 0;
				resultString = null;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	
}
