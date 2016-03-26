package com.jiyi.joke.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jiyi.joke.bean.OneCommentBean;
import com.jiyi.joke.config.JokeConfig;
import com.jiyi.joke.dao.UserDao;
import com.jiyi.joke.outjson.OutJson;
import com.jiyi.joke.util.Base64Util;

public class InsertComment extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public InsertComment() {
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
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
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
		PrintWriter out = response.getWriter();
		String result=null;
		String joke_id=request.getParameter(JokeConfig.JOKE_ID);
		String token=request.getParameter(JokeConfig.USERTOKEN);
		String user_name=request.getParameter(JokeConfig.USERNAME);
		//解密评论内容
		String content=request.getParameter(JokeConfig.COMMENT_CONTENT);
		if(joke_id!=null&&!joke_id.equals("")&&token!=null&&!token.equals("")&&user_name!=null){
			OneCommentBean ocb=new OneCommentBean();
			ocb.setJoke_id(joke_id);ocb.setUser_id(token);ocb.setUser_name(user_name);ocb.setCom_content(content);
			if(new UserDao().insertComment(ocb)){
				result=OutJson.outInsertCom(OutJson.SUCCESS);
			}else{
				result=OutJson.outInsertCom(OutJson.FAIL);
			}
		}else{
			result=OutJson.outInsertCom(OutJson.FAIL);
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
