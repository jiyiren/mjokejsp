package com.jiyi.joke.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jiyi.joke.config.JokeConfig;
import com.jiyi.joke.outjson.OutJson;

public class ReceiveUserPic extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ReceiveUserPic() {
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
		doPost(request, response);
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
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String usertoken = null;
		String outjson=null;
//		InetAddress addr = InetAddress.getLocalHost();
//		String ip = addr.getHostAddress();
//		System.out.println("ipip:"+ip);
		String localIp=request.getLocalAddr();//获取本地ip
		int localPort=request.getLocalPort();//获取本地的端口
//		System.out.println("ip:"+localIp+"--"+"port:"+localPort);
		//-------------------下面的 要改---------------------------
//		String resulturl="http://"+localIp+":"+localPort;
		//-------------------------------------------------------
		String resulturl="http://"+JokeConfig.IP_HOST;
		//---------------------------------------------------
		//保存文件的路径
		String savePath=request.getServletContext().getRealPath("/"+JokeConfig.SAVE_HEADER_FOLDER_NAME);
		//String savePath=JokeConfig.SAVE_HEADER_FOLDER_NAME;
		File saveFiledir=new File(savePath);
		if(!saveFiledir.exists()){
			saveFiledir.mkdir();
		}
		//缓存文件的路径
		String tempPath=request.getServletContext().getRealPath("/"+JokeConfig.SAVE_FILE_TEMP_NAME);
//		String tempPath=JokeConfig.SAVE_FILE_TEMP_NAME;
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
					if(name.equals("token")){
						usertoken=value;
					}
					//分别输出
					System.out.print("form-name:"+name+" ");
					System.out.println("form-value:"+value);
					System.out.println("form-token:"+usertoken);
				}else{
				//否则如果是文件类型控件
					//获取表单标签名
					String tagname=fileitem.getFieldName();
					//获取文件名
					String fileName=fileitem.getName();
					System.out.println("文件标签名:"+tagname);
					System.out.println("文件名称:"+fileName);
					
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
					fileitem.write(new File(savePath,fileName));
					resulturl=resulturl+"/"+JokeConfig.SAVE_HEADER_FOLDER_NAME+"/"+fileName;
					outjson=OutJson.receiveUserPic(OutJson.SUCCESS, resulturl);
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			outjson=OutJson.receiveUserPic(OutJson.FAIL, null);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			outjson=OutJson.receiveUserPic(OutJson.FAIL, null);
			e.printStackTrace();
		}
		
		PrintWriter out=response.getWriter();
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
