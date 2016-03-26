package com.jiyi.joke.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jiyi.joke.bean.SingleJoke;
import com.jiyi.joke.config.JokeConfig;
import com.jiyi.joke.dao.UserDao;
import com.jiyi.joke.outjson.OutJson;

public class GetTopJokes extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetTopJokes() {
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

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String result=null;
		String lookcount=request.getParameter(JokeConfig.DB_JOKE_LOOK);
		String scroll_type=null;int count=-1;
		String mcount=request.getParameter(JokeConfig.COUNT);
		String mtype=request.getParameter(JokeConfig.FRAGMENT_TYPE);
		
		if(lookcount==null||lookcount.equals("")||mcount==null||mcount.equals("")){
			result=OutJson.getJokes(OutJson.FAIL_NODATA, null);
		}else{
			scroll_type=request.getParameter(JokeConfig.SCROLL_TYPE);
			count=Integer.parseInt(mcount);
			List<SingleJoke> mjokes=new ArrayList<SingleJoke>();
			mjokes=new UserDao().getTopJokes(lookcount, scroll_type, count,mtype);
			if(mjokes==null){
				result=OutJson.getJokes(OutJson.FAIL_NODATA, null);
			}else{
				result=OutJson.getJokes(OutJson.SUCCESS, mjokes);
			}
		}
//		System.out.println("joke_id:"+joke_id);
//		System.out.println("scroll_type:"+scroll_type);
//		System.out.println("count:"+count);
		
		out.println(result);
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
