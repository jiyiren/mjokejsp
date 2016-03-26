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

public class UserLogin extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UserLogin() {
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
		System.out.println("登录使用了get方法");
		out.write(OutJson.userLogin(OutJson.FAIL_NOMAN,null));
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

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String result=null;
		PrintWriter out = response.getWriter();
		String type=request.getParameter(JokeConfig.LOGINTYPE);
//		System.out.println("type:"+type);
		if(type.equals(JokeConfig.LOGINTYPE_NAME)){
			String username=new String(Base64Util.decode(request.getParameter(JokeConfig.USERNAME)),"UTF-8");
//			String username=request.getParameter(JokeConfig.USERNAME);
			String userpwd=request.getParameter(JokeConfig.USERPWD);
//			System.out.println("name:"+username+"pwd:"+userpwd);
			if(username==null||userpwd==null){
				out.write(OutJson.userLogin(OutJson.FAIL_PWDFAULT, null));
				out.flush();
				out.close();
				return;
			}
			User us=new User();
			us.setUsername(username);
			us.setUserpwd(userpwd);
			int id=new UserDao().userLogin(us, type);
			if(id==-1){
				result=OutJson.userLogin(OutJson.FAIL_NOMAN, null);
			}else if(id==-2){
				result=OutJson.userLogin(OutJson.FAIL_PWDFAULT, null);
			}else{
				User muser=new User();
				muser=new UserDao().searchById(id+"");
				result=OutJson.userLogin(OutJson.SUCCESS, muser);
			}
		}
		out.write(result);
		out.flush();
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
