package com.jiyi.joke.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jiyi.joke.bean.SingleJoke;
import com.jiyi.joke.config.JokeConfig;
import com.jiyi.joke.dao.UserDao;
import com.jiyi.joke.outjson.OutJson;
import com.jiyi.joke.util.Base64Util;

public class PublishJoke extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PublishJoke() {
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
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String outjson=null;
		
		String userid=null;//用户 的id
		String type=null;//joke的type
		String content=null;//joke的content
		
		//保存文件的路径
		String savePath=request.getServletContext().getRealPath("/"+JokeConfig.SAVE_IMGCONTENT_FOLER_NAME);
		File saveFiledir=new File(savePath);
		if(!saveFiledir.exists()){
			saveFiledir.mkdir();
		}
		//缓存文件的路径
		String tempPath=request.getServletContext().getRealPath("/"+JokeConfig.SAVE_FILE_TEMP_NAME);
		File tempFiledir=new File(tempPath);
		if(!tempFiledir.exists()){
			tempFiledir.mkdir();
		}
		DiskFileItemFactory diskFileItemFactory=new DiskFileItemFactory();
		diskFileItemFactory.setRepository(tempFiledir);//设置缓冲区仓库
		diskFileItemFactory.setSizeThreshold(1024*6);//设置临时占用内存最多为6K,多余的存在硬盘上
		
		ServletFileUpload servletFileUpload=new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setFileSizeMax(1024*1024*2);//设置单个文件不大于2M
		servletFileUpload.setSizeMax(1024*1024*10);//设置文件总大小不超过10M
		servletFileUpload.setHeaderEncoding("utf-8");//设置传输编码
		
		List<FileItem> fileitems=null;
		FileItem mFileItem=null;
		try {
			//处理表单请求
			fileitems=servletFileUpload.parseRequest(request);
			//遍历表单项
			for(FileItem fileitem:fileitems){
				//如果表单项是普通表单控件
				if(fileitem.isFormField()){
					//获取表单控件名，就是form表单里的控件名
					String name=fileitem.getFieldName();
					//获取该控件的值
					String value=fileitem.getString("utf-8");
					String value2 = new String(fileitem.getString().getBytes(),"UTF-8");
					if(name.equals(JokeConfig.USERTOKEN)){
						userid=value;
//						System.out.print("form-userid:"+userid+" ");
					}
					if(name.equals(JokeConfig.JOKE_TYPE)){
						type=value;
//						System.out.println("form-type:"+type);
					}
					if(name.equals(JokeConfig.JOKE_CONTENT)){
//						fileitem.get();
						content=new String(Base64Util.decode(value),"utf-8");
//						System.out.println("form-content:"+content);
					}
					//分别输出
					
				}else{
				//否则如果是文件类型控件
					//获取表单标签名
//					String tagname=fileitem.getFieldName();
					//获取文件名
					String fileName=fileitem.getName();
//					System.out.println("文件标签名:"+tagname);
//					System.out.println("文件名称:"+fileName);
					
					//获取整个文件路径，有的浏览器获得的是比如C:\img\a.jpg  -IE
					//有的浏览器是用下面方法或的的是a.jpg  -Chrom或者火狐
					//String filePath=fileitem.getName();//所以叫做路径
					if(fileName==null||fileName.trim().length()==0){
						continue;
					}
					//通过路径获得文件名，这是通用的
					//String fileName=filePath.substring(filePath.lastIndexOf("\\")+1);
					//String extName=filePath.substring(filePath.lastIndexOf(".")+1);//这个也可以获得后缀
					//if(fileName.endsWith(".exe"))//这个可以获得后缀，并判断
					//写入文件
					mFileItem=fileitem;
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			outjson=OutJson.publishJoke(OutJson.FAIL);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			outjson=OutJson.publishJoke(OutJson.FAIL);
			e.printStackTrace();
		}
		if(userid!=null&&type!=null&&content!=null){
			SingleJoke sj=new SingleJoke();
			sj.setUser_id(userid);sj.setType(type);sj.setContent(content);sj.setIshasing("1");
			String re=new UserDao().publishJokes(sj);
			if(!re.equals("-1")){
//				System.out.println("jokeid:"+re);
				try {
					if(mFileItem!=null){
						mFileItem.write(new File(savePath,re+".jpg"));
						outjson=OutJson.publishJoke(OutJson.SUCCESS);
					}else{
						outjson=OutJson.publishJoke(OutJson.FAIL);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			outjson=OutJson.publishJoke(OutJson.FAIL);
		}
		out.write(outjson);
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
