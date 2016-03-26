package com.jiyi.joke.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jiyi.joke.bean.SingleJoke;
import com.jiyi.joke.config.JokeConfig;
import com.jiyi.joke.dao.UserDao;
import com.jiyi.joke.outjson.OutJson;

public class PublishNoImgJoke extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PublishNoImgJoke() {
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

		response.setContentType("text/html");
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

		response.setContentType("text/html,charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String result=null;
		String userid=request.getParameter(JokeConfig.USERTOKEN);
		String type=request.getParameter(JokeConfig.JOKE_TYPE);
		String content=request.getParameter(JokeConfig.JOKE_CONTENT);
		String imgname=request.getParameter(JokeConfig.JOKE_CONTENT_IMG);
System.out.println("content:"+content);
		if(userid!=null&&!userid.equals("")){
			SingleJoke sjoke=new SingleJoke();
			sjoke.setUser_id(userid);
			sjoke.setType(type);
			sjoke.setContent(content);
			if(imgname!=null&&!imgname.equals("")){
				sjoke.setImgurl(imgname);
				sjoke.setIshasing("1");
			}else{
				sjoke.setImgurl("");
				sjoke.setIshasing("0");
			}
			String re=new UserDao().publishJokes(sjoke);
			if(!re.equals("-1")){
				result=OutJson.publishJoke(OutJson.SUCCESS);
			}else{
				result=OutJson.publishJoke(OutJson.FAIL);
			}
		}else{
			result=OutJson.publishJoke(OutJson.FAIL);
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
