package com.jiyi.joke.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jiyi.joke.bean.User;
import com.jiyi.joke.config.JokeConfig;
import com.jiyi.joke.dao.UserDao;
import com.jiyi.joke.outjson.OutJson;
import com.jiyi.joke.util.Base64Util;

public class UserRegister extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UserRegister() {
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

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		System.out.println("注册使用了get方法");
		out.write(OutJson.userReg(OutJson.FAIL));
		out.close();
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

		//		response.setContentType("text/html");
		//		PrintWriter out = response.getWriter();
		//		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		//		out.println("<HTML>");
		//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		//		out.println("  <BODY>");
		//		out.print("    This is ");
		//		out.print(this.getClass());
		//		out.println(", using the POST method");
		//		out.println("  </BODY>");
		//		out.println("</HTML>");
		//		out.flush();
		//		out.close();
		response.setContentType("text/html;charset=UTF-8");
		//		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String username=new String(Base64Util.decode(request.getParameter(JokeConfig.USERNAME)),"UTF-8");
		String userpwd=request.getParameter(JokeConfig.USERPWD);
		System.out.println("UserName:"+username);
		if(username==null||userpwd==null){
			out.write(OutJson.userReg(OutJson.FAIL));
			out.close();
			return;
		}
		User u=new User();
		u.setUsername(username);
		u.setUserpwd(userpwd);
		if(new UserDao().userRegister(u)){
			out.write(OutJson.userReg(OutJson.SUCCESS));
		}else{
			out.write(OutJson.userReg(OutJson.FAIL));
		}
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
