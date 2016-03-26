package com.jiyi.joke.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jiyi.joke.bean.OneCommentBean;
import com.jiyi.joke.bean.QuesBean;
import com.jiyi.joke.bean.SingleJoke;
import com.jiyi.joke.bean.User;
import com.jiyi.joke.bean.UserCollectBean;
import com.jiyi.joke.bean.UserCommentBean;
import com.jiyi.joke.bean.UserLookBean;
import com.jiyi.joke.config.JokeConfig;
import com.jiyi.joke.util.Base64Util;
import com.jiyi.joke.util.ConnectionFactory;

public class UserDao {
	private Connection conn;
	private PreparedStatement pstmt;
	public UserDao(){
		conn= ConnectionFactory.getInstance().makeConnection();
	}

	/**
	 * 注册  成功-true,失败-false
	 * @param user
	 * @return
	 */
	public boolean userRegister(User user){
		if(user.getUsername()==null||user.getUserpwd()==null){
			return false;
		}
		if(!userIsexistByName(user.getUsername())){
			int i=0;
			try {
				pstmt=conn.prepareStatement("insert into tb_user(user_name,user_pwd) values (?,?)");
				pstmt.setString(1, user.getUsername());
				pstmt.setString(2, user.getUserpwd());
				i=pstmt.executeUpdate();
				if(i==1){
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 这个给注册使用
	 * @param username
	 * @return
	 */
	public boolean userIsexistByName(String username){
		boolean isexist=false;
		ResultSet set=null;
		try {
			pstmt=conn.prepareStatement("SELECT * FROM tb_user WHERE user_name=?");
			pstmt.setString(1, username);
			set=pstmt.executeQuery();
			if(set.next()){
				isexist=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isexist;
	}
	
	
	
	/**
	 * 只给修改用户信息方法使用
	 * 根据用户名和用户id检查是否存在用户，存在-true,不存在-false
	 * @param user
	 * @return
	 */
	public boolean userIsexist(String username,String useid){
		boolean isexist=false;
		ResultSet set=null;
		try {
			pstmt=conn.prepareStatement("SELECT * FROM tb_user WHERE user_name=? AND user_id<>?");
			pstmt.setString(1, username);
			pstmt.setString(2,useid);
			set=pstmt.executeQuery();
			if(set.next()){
				isexist=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isexist;
	}

	/**
	 * 是否登录成功
	 * -2 密码错误
	 * -1查无此人
	 * -id>0登录成功
	 * @param user
	 * @param type
	 * @return
	 */
	public int userLogin(User user,String type){
		ResultSet set=null;
		if(type.equals(JokeConfig.LOGINTYPE_NAME)){
			if(!userIsexistForLogin(user.getUsername())){
//				System.out.println("hello");
				return -1;//不存在此人
			}
			
			try {
				pstmt=conn.prepareStatement("SELECT user_id FROM tb_user WHERE user_name=? and user_pwd=?");
				pstmt.setString(1, user.getUsername());
				pstmt.setString(2, user.getUserpwd());
				set=pstmt.executeQuery();
//				System.out.println("set:"+set.toString());
				
				if(set.next()){
					return set.getInt(1);
				}else{
					return -2;//密码错误
				}
			} catch (SQLException e) {
//				System.out.println("error");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(set!=null){
					try {
						set.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return -1;
	}

	/**
	 * 为登录查询是否有用户的
	 * @param username
	 * @param userpwd
	 * @return
	 */
	
	public boolean userIsexistForLogin(String username){
		boolean isexist=false;
		ResultSet set=null;
		try {
			pstmt=conn.prepareStatement("SELECT * FROM tb_user WHERE user_name=?");
			pstmt.setString(1, username);
			set=pstmt.executeQuery();
//			System.out.println("username:"+username);
//			System.out.println("Userisexist"+set.next());
			if(set.next()){
				isexist=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isexist;
	}
	
	/**
	 * 根据用户token或者id查询出用户信息
	 * username和motto base64加密
	 * @param userid
	 * @return
	 */
	public User searchById(String userid){
		if(userid==null||userid.equals("")){
			return null;
		}
		ResultSet set=null;
		User user=new User();
		try {
			pstmt=conn.prepareStatement("SELECT * FROM tb_user WHERE user_id=?");
			pstmt.setString(1, userid);
			set=pstmt.executeQuery();
			if(set.next()){
				String username=set.getString(JokeConfig.DB_USERNAME);
				String sex=set.getString(JokeConfig.DB_USER_SEX);
				String motto=set.getString(JokeConfig.DB_USER_MOTTO);
				//				System.out.println("username:"+username+"---");
				//				System.out.println("sex:"+sex+"---");
				//				System.out.println("motto:"+motto+"---");
				if(sex==null){
					sex="";
				}
				if(motto==null){
					motto="";
				}
				user.setId(userid);
				try {
					user.setUsername(Base64Util.encode(username.getBytes("UTF-8")));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				user.setSex(sex);
				try {
					user.setMotto(Base64Util.encode(motto.getBytes("UTF-8")));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//暂时没用，直接在客户端调用url+token.png的
	//根据userid插入头像图片地址
	//存的地址应该是项目内地址比较好
	public boolean saveUserHeadimg(String userid,String headurl){
		try {
			int i=0;
			pstmt=conn.prepareStatement("UPDATE tb_user SET headurl=? WHERE user_id=?");
			pstmt.setString(1, headurl);
			pstmt.setString(2, userid);
			i=pstmt.executeUpdate();
			if(i==1){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 修改用户信息
	 * 传入user_id,user_name,user_sex,user_motto
	 * 更新成功-true
	 * 更新失败-false
	 * @param user
	 * @return
	 */
	public boolean modifyUserInfo(User user){
		String id=user.getId();
		String name=user.getUsername();
		if(id==null||id.equals("")||name==null||name.equals("")){
			return false;
		}
		if(userIsexist(user.getUsername(),user.getId())){
			return false;
		}
		try {
			int i=0;
			pstmt=conn.prepareStatement("UPDATE tb_user SET user_name=?,user_sex=?,user_motto=? WHERE user_id=?");
			pstmt.setString(1, name);
			pstmt.setString(2, user.getSex());
			pstmt.setString(3, user.getMotto());
			pstmt.setString(4, id);
			i=pstmt.executeUpdate();
			if(i==1){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param cur_jokeid 当前joke的id
	 * @param scroll_type  滑动的方式（下拉刷新/上拉加载）
	 * @param count 加载的个数
	 * @return
	 */

	public List<SingleJoke> getJokes(String cur_jokeid,String scroll_type,int count,String fragtype){
		//		System.out.println("Dao_joke_id:"+cur_jokeid);
		//		System.out.println("Dao_scroll_type:"+scroll_type);
		//		System.out.println("Dao_count:"+count);
		if(count<0||cur_jokeid==null||fragtype==null){
			return null;
		}
		List<SingleJoke> mjokes=null;
		ResultSet set;
		if(scroll_type.equals(JokeConfig.SCROLL_REFRESH)){
			try {
				//下面语句是获取最新的最多count条消息
				if(cur_jokeid.equals("0")){
					if(fragtype.equals(JokeConfig.FRAGMENT_TYPE_NEW)){
						pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id>? ORDER BY joke_id DESC LIMIT 0,?");
					}else if(fragtype.equals(JokeConfig.FRAGMENT_TYPE_JOKE)){
						pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id>? AND type=0 ORDER BY joke_id DESC LIMIT 0,?");
					}else if(fragtype.equals(JokeConfig.FRAGMENT_TYPE_GIRL)){
						pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id>? AND type=1 ORDER BY joke_id DESC LIMIT 0,?");
					}else{
						return null;
					}
				}else{
					if(fragtype.equals(JokeConfig.FRAGMENT_TYPE_NEW)){
						pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id>? LIMIT 0,?");
					}else if(fragtype.equals(JokeConfig.FRAGMENT_TYPE_JOKE)){
						pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id>? AND type=0 LIMIT 0,?");
					}else if(fragtype.equals(JokeConfig.FRAGMENT_TYPE_GIRL)){
						pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id>? AND type=1 LIMIT 0,?");
					}else{
						return null;
					}
				}
				//下面语句将最新的消息全部发送给客户端
				//				pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id>? ORDER BY joke_id DESC");
				pstmt.setString(1, cur_jokeid);
				pstmt.setInt(2, count);
				set=pstmt.executeQuery();
				while(set.next()){
					if(mjokes==null){
						mjokes=new ArrayList<SingleJoke>();
					}
					SingleJoke joke=new SingleJoke();
					joke.setJoke_id(set.getString(JokeConfig.DB_JOKE_ID));
					String userid=set.getString(JokeConfig.DB_JOKE_USER_ID);
					joke.setUser_id(userid);
					joke.setType(set.getString(JokeConfig.DB_JOKE_TYPE));
					joke.setCreatetime(set.getString(JokeConfig.DB_JOKE_TIME));
					try {
						joke.setContent(Base64Util.encode(set.getString(JokeConfig.DB_JOKE_CONTENT).getBytes("UTF-8")));
					} catch (UnsupportedEncodingException e) {
						System.out.println("base64 error");
						e.printStackTrace();
					}
					String mimgurl=set.getString(JokeConfig.DB_JOKE_IMGURL);
					if(mimgurl==null){
						joke.setImgurl("");
					}else{
						joke.setImgurl(mimgurl);
					}
					joke.setIshasing(set.getString(JokeConfig.DB_JOKE_ISHASIMG));
					joke.setShare_count(set.getString(JokeConfig.DB_JOKE_SHARE));
					joke.setCollect_count(set.getString(JokeConfig.DB_JOKE_COLLECT));
					joke.setLook_count(set.getString(JokeConfig.DB_JOKE_LOOK));
					if(!userid.equals("0")){
						User u=searchById(userid);
						if(u==null){
							joke.setUser_id("0");
							joke.setUsername("");
							joke.setSex("");
						}else{
							joke.setUsername(u.getUsername());
							joke.setSex(u.getSex());
						}
					}else{
						joke.setUsername("");
						joke.setSex("");
					}
					//					System.out.println("joke:"+joke.toString());
					mjokes.add(joke);
				}
				return mjokes;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			try {
				if(fragtype.equals(JokeConfig.FRAGMENT_TYPE_NEW)){
					pstmt=conn.prepareStatement("SELECT * FROM tb_joke where joke_id<? ORDER BY joke_id DESC LIMIT 0,?");
				}else if(fragtype.equals(JokeConfig.FRAGMENT_TYPE_JOKE)){
					pstmt=conn.prepareStatement("SELECT * FROM tb_joke where joke_id<? AND type=0 ORDER BY joke_id DESC LIMIT 0,?");
				}else if(fragtype.equals(JokeConfig.FRAGMENT_TYPE_GIRL)){
					pstmt=conn.prepareStatement("SELECT * FROM tb_joke where joke_id<? AND type=1 ORDER BY joke_id DESC LIMIT 0,?");
				}else{
					return null;
				}
				pstmt.setString(1, cur_jokeid);
				pstmt.setInt(2, count);
				set=pstmt.executeQuery();
				while(set.next()){
					if(mjokes==null){
						mjokes=new ArrayList<SingleJoke>();
					}
					SingleJoke joke=new SingleJoke();
					joke.setJoke_id(set.getString(JokeConfig.DB_JOKE_ID));

					String userid=set.getString(JokeConfig.DB_JOKE_USER_ID);

					joke.setUser_id(userid);
					joke.setType(set.getString(JokeConfig.DB_JOKE_TYPE));
					joke.setCreatetime(set.getString(JokeConfig.DB_JOKE_TIME));
//					joke.setContent(set.getString(JokeConfig.DB_JOKE_CONTENT));
					try {
						joke.setContent(Base64Util.encode(set.getString(JokeConfig.DB_JOKE_CONTENT).getBytes("UTF-8")));
					} catch (UnsupportedEncodingException e) {
						System.out.println("base64 error");
						e.printStackTrace();
					}
					String mimgurl=set.getString(JokeConfig.DB_JOKE_IMGURL);
					if(mimgurl==null){
						joke.setImgurl("");
					}else{
						joke.setImgurl(mimgurl);
					}
					joke.setIshasing(set.getString(JokeConfig.DB_JOKE_ISHASIMG));
					joke.setShare_count(set.getString(JokeConfig.DB_JOKE_SHARE));
					joke.setCollect_count(set.getString(JokeConfig.DB_JOKE_COLLECT));
					joke.setLook_count(set.getString(JokeConfig.DB_JOKE_LOOK));
					//					System.out.println("joke:"+joke.toString());
					if(!userid.equals("0")){
						User u=searchById(userid);
						if(u==null){
							joke.setUser_id("0");
							joke.setUsername("");
							joke.setSex("");
						}else{
							joke.setUsername(u.getUsername());
							joke.setSex(u.getSex());
						}
					}else{
						joke.setUsername("");
						joke.setSex("");
					}
					mjokes.add(joke);
				}
				return mjokes;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}

	/**
	 * 获取高赞的joke
	 * @param cur_lookcount
	 * @param scroll_type
	 * @param count
	 * @param fragtype
	 * @return
	 */
	public List<SingleJoke> getTopJokes(String cur_lookcount,String scroll_type,int count,String fragtype){
		//		System.out.println("Dao_joke_id:"+cur_jokeid);
		//		System.out.println("Dao_scroll_type:"+scroll_type);
		//		System.out.println("Dao_count:"+count);
		if(count<0||cur_lookcount==null){
			return null;
		}
		List<SingleJoke> mjokes=null;
		ResultSet set;
		if(scroll_type.equals(JokeConfig.SCROLL_REFRESH)){
			try {
				//下面语句是获取最新的最多count条消息
				if(cur_lookcount.equals("no")){
						pstmt=conn.prepareStatement("SELECT * FROM tb_joke ORDER BY look_count DESC LIMIT 0,?");
						pstmt.setInt(1, count);
				}else{
					pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE look_count>? ORDER BY look_count DESC LIMIT 0,?");
					pstmt.setString(1, cur_lookcount);
					pstmt.setInt(2, count);
				}
				set=pstmt.executeQuery();
				while(set.next()){
					if(mjokes==null){
						mjokes=new ArrayList<SingleJoke>();
					}
					SingleJoke joke=new SingleJoke();
					joke.setJoke_id(set.getString(JokeConfig.DB_JOKE_ID));
					String userid=set.getString(JokeConfig.DB_JOKE_USER_ID);
					joke.setUser_id(userid);
					joke.setType(set.getString(JokeConfig.DB_JOKE_TYPE));
					joke.setCreatetime(set.getString(JokeConfig.DB_JOKE_TIME));
//					joke.setContent(set.getString(JokeConfig.DB_JOKE_CONTENT));
					try {
						joke.setContent(Base64Util.encode(set.getString(JokeConfig.DB_JOKE_CONTENT).getBytes("UTF-8")));
					} catch (UnsupportedEncodingException e) {
						System.out.println("base64 error");
						e.printStackTrace();
					}
					String mimgurl=set.getString(JokeConfig.DB_JOKE_IMGURL);
					if(mimgurl==null){
						joke.setImgurl("");
					}else{
						joke.setImgurl(mimgurl);
					}
					joke.setIshasing(set.getString(JokeConfig.DB_JOKE_ISHASIMG));
					joke.setShare_count(set.getString(JokeConfig.DB_JOKE_SHARE));
					joke.setCollect_count(set.getString(JokeConfig.DB_JOKE_COLLECT));
					joke.setLook_count(set.getString(JokeConfig.DB_JOKE_LOOK));
					if(!userid.equals("0")){
						User u=searchById(userid);
						if(u==null){
							joke.setUser_id("0");
							joke.setUsername("");
							joke.setSex("");
						}else{
							joke.setUsername(u.getUsername());
							joke.setSex(u.getSex());
						}
					}else{
						joke.setUsername("");
						joke.setSex("");
					}
					//					System.out.println("joke:"+joke.toString());
					mjokes.add(joke);
				}
				return mjokes;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			try {
				pstmt=conn.prepareStatement("SELECT * FROM tb_joke where look_count<? ORDER BY look_count DESC LIMIT 0,?");
				pstmt.setString(1, cur_lookcount);
				pstmt.setInt(2, count);
				set=pstmt.executeQuery();
				while(set.next()){
					if(mjokes==null){
						mjokes=new ArrayList<SingleJoke>();
					}
					SingleJoke joke=new SingleJoke();
					joke.setJoke_id(set.getString(JokeConfig.DB_JOKE_ID));

					String userid=set.getString(JokeConfig.DB_JOKE_USER_ID);

					joke.setUser_id(userid);
					joke.setType(set.getString(JokeConfig.DB_JOKE_TYPE));
					joke.setCreatetime(set.getString(JokeConfig.DB_JOKE_TIME));
//					joke.setContent(set.getString(JokeConfig.DB_JOKE_CONTENT));
					try {
						joke.setContent(Base64Util.encode(set.getString(JokeConfig.DB_JOKE_CONTENT).getBytes("UTF-8")));
					} catch (UnsupportedEncodingException e) {
						System.out.println("base64 error");
						e.printStackTrace();
					}
					String mimgurl=set.getString(JokeConfig.DB_JOKE_IMGURL);
					if(mimgurl==null){
						joke.setImgurl("");
					}else{
						joke.setImgurl(mimgurl);
					}
					joke.setIshasing(set.getString(JokeConfig.DB_JOKE_ISHASIMG));
					joke.setShare_count(set.getString(JokeConfig.DB_JOKE_SHARE));
					joke.setCollect_count(set.getString(JokeConfig.DB_JOKE_COLLECT));
					joke.setLook_count(set.getString(JokeConfig.DB_JOKE_LOOK));
					//					System.out.println("joke:"+joke.toString());
					if(!userid.equals("0")){
						User u=searchById(userid);
						if(u==null){
							joke.setUser_id("0");
							joke.setUsername("");
							joke.setSex("");
						}else{
							joke.setUsername(u.getUsername());
							joke.setSex(u.getSex());
						}
					}else{
						joke.setUsername("");
						joke.setSex("");
					}
					mjokes.add(joke);
				}
				return mjokes;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}
	
	/**
	 * 插入joke
	 * 参数是SingleJoke<里面只需要有userid,type,content>就行
	 * 返回插入后的jokeid
	 * @param singlejoke
	 * @return
	 */
	public String publishJokes(SingleJoke singlejoke){
		String mContent=singlejoke.getContent();
		if(singlejoke==null||mContent.equals("")||mContent==null){
			return "-1";
		}
		String userid=singlejoke.getUser_id();
		String type=singlejoke.getType();
		String ishasimg=singlejoke.getIshasing();
		if(userid.equals("")||userid==null){
			userid="0";
		}
		if(type.equals("")||type==null){
			type=SingleJoke.TYPE_JOKE;
		}
		if(ishasimg.equals("")||ishasimg==null){
			ishasimg="0";
		}
		
		//		
		int share=(int)(96+Math.random()*(131-96+1));//96-131
		int collect=(int)(51+Math.random()*(121-51+1));//51-121
//		int look_zan=(int)(234+Math.random()*(899-234+1));//234-899
		int look_zan=(int)(101+Math.random()*(161-101+1));//111-151
		//		System.out.println("share_collect_look"+share+" "+collect+" "+look_zan);
		try {
			int i=0;
			pstmt=conn.prepareStatement("INSERT INTO tb_joke (user_id,type,timecreate,content,imgurl,ishasimg,share_count,collect_count,look_count) VALUES(?,?,current_timestamp(),?,?,?,?,?,?)");
			pstmt.setString(1, userid);
			pstmt.setString(2, type);
			pstmt.setString(3, mContent);
			pstmt.setString(4, singlejoke.getImgurl());
			pstmt.setString(5, ishasimg);
			pstmt.setString(6, share+"");
			pstmt.setString(7, collect+"");
			pstmt.setString(8, look_zan+"");
			i=pstmt.executeUpdate();
			if(i==1){
				ResultSet set=pstmt.executeQuery("SELECT LAST_INSERT_ID()");
				if(set.next()){
					return set.getString(1);
				}
				return "-1";
			}else{
				return "-1";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "-1";
	}

	/**
	 * 收藏的方法
	 * 参数UserCollectBean(joke_id,token,type(0,1))
	 * 返回类型String 三个值，0收藏失败，1收藏成功，2已经收藏
	 * @param usercollect
	 */
	public String DoUserCollect(UserCollectBean usercollect){
		String joke_id=usercollect.getJoke_id();
		if(joke_id==null||joke_id.equals("")){
			return "0";
		}
		String user_id=usercollect.getUser_id();
		if(user_id==null||user_id.equals("")){
			return "0";
		}
		String type=usercollect.getType();
		if(type==null||user_id.equals(""))
		{
			return "0";
		}
		try {
			if(type.equals("0")){
				//删除收藏
				pstmt=conn.prepareStatement("DELETE FROM tb_usercollect WHERE joke_id=? AND user_id=?");
				pstmt.setString(1, joke_id);
				pstmt.setString(2, user_id);
				pstmt.executeUpdate();
				return "1";//删除成功
			}else{
				ResultSet set;
				pstmt=conn.prepareStatement("SELECT * FROM tb_usercollect WHERE joke_id=? AND user_id=?");
				pstmt.setString(1, joke_id);
				pstmt.setString(2, user_id);
				set=pstmt.executeQuery();
				if(set.next()){
					//				System.out.println("UserCollect:joke_id:"+joke_id+"--user_id"+":"+user_id+"已经存在");
					return "2";
				}
				pstmt=conn.prepareStatement("INSERT INTO tb_usercollect(joke_id,user_id) VALUES(?,?)");
				pstmt.setString(1, joke_id);
				pstmt.setString(2, user_id);
				int i=pstmt.executeUpdate();
				if(i==1){
					//				System.out.println("UserCollect:joke_id:"+joke_id+"--user_id"+":"+user_id+"插入成功");
					return "1";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		System.out.println("UserCollect:joke_id:"+joke_id+"--user_id"+":"+user_id+"插入失败");
		return "0";
	}


	/**
	 * 查看joke是
	 * 返回类型String 三个值，0插入失败，1插入成功，2已经查看过
	 * @param userLookBean
	 * @return
	 */
	public String DoUserLook(UserLookBean userLookBean){
		String joke_id=userLookBean.getJoke_id();
		if(joke_id==null||joke_id.equals("")){
			return "0";
		}
		String user_id=userLookBean.getUser_id();
		if(user_id==null||user_id.equals("")){
			return "0";
		}
		try {
			ResultSet set;
			pstmt=conn.prepareStatement("SELECT * FROM tb_userlook WHERE joke_id=? AND user_id=?");
			pstmt.setString(1, joke_id);
			pstmt.setString(2, user_id);
			set=pstmt.executeQuery();
			if(set.next()){
				//				System.out.println("UserCollect:joke_id:"+joke_id+"--user_id"+":"+user_id+"已经存在");
				return "2";
			}
			pstmt=conn.prepareStatement("INSERT INTO tb_userlook(joke_id,user_id) VALUES(?,?)");
			pstmt.setString(1, joke_id);
			pstmt.setString(2, user_id);
			int i=pstmt.executeUpdate();
			if(i==1){
				//				System.out.println("UserCollect:joke_id:"+joke_id+"--user_id"+":"+user_id+"插入成功");
				return "1";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "0";
	}
	
	
	/**
	 * 个人中心查询评论,此处是插入单独的评论表
	 * 0--插入失败
	 * 2--已经存在
	 * 1--插入成功
	 * @param userLookBean
	 * @return
	 */
	public String DoUserComment(UserCommentBean userCommentBean){
		String joke_id=userCommentBean.getJoke_id();
		if(joke_id==null||joke_id.equals("")){
			return "0";
		}
		String user_id=userCommentBean.getUser_id();
		if(user_id==null||user_id.equals("")){
			return "0";
		}
		try {
			ResultSet set;
			pstmt=conn.prepareStatement("SELECT * FROM tb_usercomment WHERE joke_id=? AND user_id=?");
			pstmt.setString(1, joke_id);
			pstmt.setString(2, user_id);
			set=pstmt.executeQuery();
			if(set.next()){
				//				System.out.println("UserCollect:joke_id:"+joke_id+"--user_id"+":"+user_id+"已经存在");
				return "2";
			}
			pstmt=conn.prepareStatement("INSERT INTO tb_usercomment(joke_id,user_id,com_time) VALUES(?,?,current_timestamp())");
			pstmt.setString(1, joke_id);
			pstmt.setString(2, user_id);
			int i=pstmt.executeUpdate();
			if(i==1){
				//				System.out.println("UserCollect:joke_id:"+joke_id+"--user_id"+":"+user_id+"插入成功");
				return "1";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "0";
	}
	
	/**
	 * 查询出所有的收藏jokes
	 * @param cur_jokeid
	 * @param scroll_type
	 * @param count
	 * @return
	 */
	public List<SingleJoke> getCollectJokes(String cur_jokeid,String token,int count){
		//		System.out.println("Dao_joke_id:"+cur_jokeid);
		//		System.out.println("Dao_scroll_type:"+scroll_type);
		//		System.out.println("Dao_count:"+count);
		if(count<0||cur_jokeid==null||token==null){
			return null;
		}
		List<SingleJoke> mjokes=null;
		ResultSet set;
		if(cur_jokeid.equals("0")){
			try {
				//下面语句是获取最新的最多count条消息
				pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id IN (SELECT joke_id FROM tb_usercollect WHERE user_id=?) ORDER BY joke_id DESC LIMIT 0,?");
				//下面语句将最新的消息全部发送给客户端
				//				pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id>? ORDER BY joke_id DESC");
				pstmt.setString(1, token);
				pstmt.setInt(2, count);
				set=pstmt.executeQuery();
				while(set.next()){
					if(mjokes==null){
						mjokes=new ArrayList<SingleJoke>();
					}
					SingleJoke joke=new SingleJoke();
					joke.setJoke_id(set.getString(JokeConfig.DB_JOKE_ID));
					String userid=set.getString(JokeConfig.DB_JOKE_USER_ID);
					joke.setUser_id(userid);
					joke.setType(set.getString(JokeConfig.DB_JOKE_TYPE));
					joke.setCreatetime(set.getString(JokeConfig.DB_JOKE_TIME));
//					joke.setContent(set.getString(JokeConfig.DB_JOKE_CONTENT));
					try {
						joke.setContent(Base64Util.encode(set.getString(JokeConfig.DB_JOKE_CONTENT).getBytes("UTF-8")));
					} catch (UnsupportedEncodingException e) {
						System.out.println("base64 error");
						e.printStackTrace();
					}
					String mimgurl=set.getString(JokeConfig.DB_JOKE_IMGURL);
					if(mimgurl==null){
						joke.setImgurl("");
					}else{
						joke.setImgurl(mimgurl);
					}
					joke.setIshasing(set.getString(JokeConfig.DB_JOKE_ISHASIMG));
					joke.setShare_count(set.getString(JokeConfig.DB_JOKE_SHARE));
					joke.setCollect_count(set.getString(JokeConfig.DB_JOKE_COLLECT));
					joke.setLook_count(set.getString(JokeConfig.DB_JOKE_LOOK));
					if(!userid.equals("0")){
						User u=searchById(userid);
						if(u==null){
							joke.setUser_id("0");
							joke.setUsername("");
							joke.setSex("");
						}else{
							joke.setUsername(u.getUsername());
							joke.setSex(u.getSex());
						}
					}else{
						joke.setUsername("");
						joke.setSex("");
					}
					//					System.out.println("joke:"+joke.toString());
					mjokes.add(joke);
				}
				return mjokes;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			try {
				pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id IN (SELECT joke_id FROM tb_usercollect WHERE user_id=? AND joke_id<?) ORDER BY joke_id DESC LIMIT 0,?");
				pstmt.setString(1, token);
				pstmt.setString(2, cur_jokeid);
				pstmt.setInt(3, count);
				set=pstmt.executeQuery();
				while(set.next()){
					if(mjokes==null){
						mjokes=new ArrayList<SingleJoke>();
					}
					SingleJoke joke=new SingleJoke();
					joke.setJoke_id(set.getString(JokeConfig.DB_JOKE_ID));

					String userid=set.getString(JokeConfig.DB_JOKE_USER_ID);

					joke.setUser_id(userid);
					joke.setType(set.getString(JokeConfig.DB_JOKE_TYPE));
					joke.setCreatetime(set.getString(JokeConfig.DB_JOKE_TIME));
//					joke.setContent(set.getString(JokeConfig.DB_JOKE_CONTENT));
					try {
						joke.setContent(Base64Util.encode(set.getString(JokeConfig.DB_JOKE_CONTENT).getBytes("UTF-8")));
					} catch (UnsupportedEncodingException e) {
						System.out.println("base64 error");
						e.printStackTrace();
					}
					String mimgurl=set.getString(JokeConfig.DB_JOKE_IMGURL);
					if(mimgurl==null){
						joke.setImgurl("");
					}else{
						joke.setImgurl(mimgurl);
					}
					joke.setIshasing(set.getString(JokeConfig.DB_JOKE_ISHASIMG));
					joke.setShare_count(set.getString(JokeConfig.DB_JOKE_SHARE));
					joke.setCollect_count(set.getString(JokeConfig.DB_JOKE_COLLECT));
					joke.setLook_count(set.getString(JokeConfig.DB_JOKE_LOOK));
					//					System.out.println("joke:"+joke.toString());
					if(!userid.equals("0")){
						User u=searchById(userid);
						if(u==null){
							joke.setUser_id("0");
							joke.setUsername("");
							joke.setSex("");
						}else{
							joke.setUsername(u.getUsername());
							joke.setSex(u.getSex());
						}
					}else{
						joke.setUsername("");
						joke.setSex("");
					}
					mjokes.add(joke);
				}
				return mjokes;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}
	
	/**
	 * 获取用户所有'浏览','分享','评论'过的jokes
	 * joketype 1-浏览，2-分享，3-评论
	 * @param cur_jokeid
	 * @param token
	 * @param count
	 * @return
	 */
	public List<SingleJoke> getAboutJokes(String cur_jokeid,String token,int count,String joketype){
		//		System.out.println("Dao_joke_id:"+cur_jokeid);
		//		System.out.println("Dao_scroll_type:"+scroll_type);
		//		System.out.println("Dao_count:"+count);
		if(count<0||cur_jokeid==null||token==null||joketype==null){
			return null;
		}
		List<SingleJoke> mjokes=null;
		ResultSet set;
		if(cur_jokeid.equals("0")){
			try {
				//下面语句是获取最新的最多count条消息
				if(joketype.equals(JokeConfig.USER_ABOUT_JOKE_TYPE_COLLECT)){
					pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id IN (SELECT joke_id FROM tb_usercollect WHERE user_id=?) ORDER BY joke_id DESC LIMIT 0,?");
				}else if(joketype.equals(JokeConfig.USER_ABOUT_JOKE_TYPE_LOOK)){
					pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id IN (SELECT joke_id FROM tb_userlook WHERE user_id=?) ORDER BY joke_id DESC LIMIT 0,?");
				}else if(joketype.equals(JokeConfig.USER_ABOUT_JOKE_TYPE_SHARE)){
					return null;
				}else if(joketype.equals(JokeConfig.USER_ABOUT_JOKE_TYPE_COMMENT)){
					pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id IN (SELECT joke_id FROM tb_usercomment WHERE user_id=?) ORDER BY joke_id DESC LIMIT 0,?");
				}else if(joketype.equals(JokeConfig.USER_ABOUT_JOKE_TYPE_SELFPUB)){
					pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE user_id=? ORDER BY joke_id DESC LIMIT 0,?");
				}else{
					return null;
				}
				//下面语句将最新的消息全部发送给客户端
				//				pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id>? ORDER BY joke_id DESC");
				pstmt.setString(1, token);
				pstmt.setInt(2, count);
				set=pstmt.executeQuery();
				while(set.next()){
					if(mjokes==null){
						mjokes=new ArrayList<SingleJoke>();
					}
					SingleJoke joke=new SingleJoke();
					joke.setJoke_id(set.getString(JokeConfig.DB_JOKE_ID));
					String userid=set.getString(JokeConfig.DB_JOKE_USER_ID);
					joke.setUser_id(userid);
					joke.setType(set.getString(JokeConfig.DB_JOKE_TYPE));
					joke.setCreatetime(set.getString(JokeConfig.DB_JOKE_TIME));
//					joke.setContent(set.getString(JokeConfig.DB_JOKE_CONTENT));
					try {
						joke.setContent(Base64Util.encode(set.getString(JokeConfig.DB_JOKE_CONTENT).getBytes("UTF-8")));
					} catch (UnsupportedEncodingException e) {
						System.out.println("base64 error");
						e.printStackTrace();
					}
					String mimgurl=set.getString(JokeConfig.DB_JOKE_IMGURL);
					if(mimgurl==null){
						joke.setImgurl("");
					}else{
						joke.setImgurl(mimgurl);
					}
					joke.setIshasing(set.getString(JokeConfig.DB_JOKE_ISHASIMG));
					joke.setShare_count(set.getString(JokeConfig.DB_JOKE_SHARE));
					joke.setCollect_count(set.getString(JokeConfig.DB_JOKE_COLLECT));
					joke.setLook_count(set.getString(JokeConfig.DB_JOKE_LOOK));
					if(!userid.equals("0")){
						User u=searchById(userid);
						if(u==null){
							joke.setUser_id("0");
							joke.setUsername("");
							joke.setSex("");
						}else{
							joke.setUsername(u.getUsername());
							joke.setSex(u.getSex());
						}
					}else{
						joke.setUsername("");
						joke.setSex("");
					}
					//					System.out.println("joke:"+joke.toString());
					mjokes.add(joke);
				}
				return mjokes;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			try {
				if(joketype.equals(JokeConfig.USER_ABOUT_JOKE_TYPE_COLLECT)){
					pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id IN (SELECT joke_id FROM tb_usercollect WHERE user_id=? AND joke_id<?) ORDER BY joke_id DESC LIMIT 0,?");
				}else if(joketype.equals(JokeConfig.USER_ABOUT_JOKE_TYPE_LOOK)){
					pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id IN (SELECT joke_id FROM tb_userlook WHERE user_id=? AND joke_id<?) ORDER BY joke_id DESC LIMIT 0,?");
				}else if(joketype.equals(JokeConfig.USER_ABOUT_JOKE_TYPE_SHARE)){
					return null;
				}else if(joketype.equals(JokeConfig.USER_ABOUT_JOKE_TYPE_COMMENT)){
					pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id IN (SELECT joke_id FROM tb_usercomment WHERE user_id=? AND joke_id<?) ORDER BY joke_id DESC LIMIT 0,?");
				}else if(joketype.equals(JokeConfig.USER_ABOUT_JOKE_TYPE_SELFPUB)){
					pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE user_id=? AND joke_id<? ORDER BY joke_id DESC LIMIT 0,?");
				}else{
					return null;
				}
				pstmt.setString(1, token);
				pstmt.setString(2, cur_jokeid);
				pstmt.setInt(3, count);
				set=pstmt.executeQuery();
				while(set.next()){
					if(mjokes==null){
						mjokes=new ArrayList<SingleJoke>();
					}
					SingleJoke joke=new SingleJoke();
					joke.setJoke_id(set.getString(JokeConfig.DB_JOKE_ID));

					String userid=set.getString(JokeConfig.DB_JOKE_USER_ID);

					joke.setUser_id(userid);
					joke.setType(set.getString(JokeConfig.DB_JOKE_TYPE));
					joke.setCreatetime(set.getString(JokeConfig.DB_JOKE_TIME));
//					joke.setContent(set.getString(JokeConfig.DB_JOKE_CONTENT));
					try {
						joke.setContent(Base64Util.encode(set.getString(JokeConfig.DB_JOKE_CONTENT).getBytes("UTF-8")));
					} catch (UnsupportedEncodingException e) {
						System.out.println("base64 error");
						e.printStackTrace();
					}
					String mimgurl=set.getString(JokeConfig.DB_JOKE_IMGURL);
					if(mimgurl==null){
						joke.setImgurl("");
					}else{
						joke.setImgurl(mimgurl);
					}
					joke.setIshasing(set.getString(JokeConfig.DB_JOKE_ISHASIMG));
					joke.setShare_count(set.getString(JokeConfig.DB_JOKE_SHARE));
					joke.setCollect_count(set.getString(JokeConfig.DB_JOKE_COLLECT));
					joke.setLook_count(set.getString(JokeConfig.DB_JOKE_LOOK));
					//					System.out.println("joke:"+joke.toString());
					if(!userid.equals("0")){
						User u=searchById(userid);
						if(u==null){
							joke.setUser_id("0");
							joke.setUsername("");
							joke.setSex("");
						}else{
							joke.setUsername(u.getUsername());
							joke.setSex(u.getSex());
						}
					}else{
						joke.setUsername("");
						joke.setSex("");
					}
					mjokes.add(joke);
				}
				return mjokes;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}
	
	
	
	/**
	 * 根据用户token获得收藏的总数
	 * @param token
	 * @return
	 */
	public int getCollectCount(String token){
		int count=0;
		if(token!=null&&!token.equals("0")){
			try {
				
				ResultSet set;
				pstmt=conn.prepareStatement("SELECT count(*) FROM tb_usercollect WHERE user_id=?");
				pstmt.setString(1, token);
				set=pstmt.executeQuery();
				if(set.next()){
					count=set.getInt(1);
					return count;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}
	
	/**
	 * 根据token获得用户浏览的个数
	 * @param token
	 * @return
	 */
	public int getLookCount(String token){
		int count=0;
		if(token!=null&&!token.equals("0")){
			try {
				
				ResultSet set;
				pstmt=conn.prepareStatement("SELECT count(*) FROM tb_userlook WHERE user_id=?");
				pstmt.setString(1, token);
				set=pstmt.executeQuery();
				if(set.next()){
					count=set.getInt(1);
					return count;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}
	
	/**
	 * 发送问题反馈
	 * @param qb
	 */
	public void sendQuestion(QuesBean qb){
		String content=qb.getQues_content();
		if(content==null||content.equals("")){
			return;
		}
		String email=qb.getQues_email();
		if(email==null||email.equals("")){
			return;
		}
		
		try {
			pstmt=conn.prepareStatement("INSERT INTO tb_ques(ques_content,ques_email,ques_time) VALUES(?,?,current_timestamp())");
			pstmt.setString(1, content);
			pstmt.setString(2, email);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//插入评论
	public boolean insertComment(OneCommentBean ocb){
		String joke_id=ocb.getJoke_id();
		if(joke_id==null||joke_id.equals("")){
			return false;
		}
		String token=ocb.getUser_id();
		if(token==null||token.equals("")){
			return false;
		}
		String content=ocb.getCom_content();
		if(content==null||content.equals("")){
			return false;
		}
		String username=ocb.getUser_name();
		if(username==null){
			return false;
		}
		try {
			int count=getAllComment(joke_id);
			if(count>=0){
				count=count+1;
			}else{
				return false;
			}
			pstmt=conn.prepareStatement("INSERT INTO tb_comment(joke_id,user_id,user_name,com_content,com_lou,com_time) VALUES(?,?,?,?,?,current_timestamp())");
			pstmt.setString(1, joke_id);
			pstmt.setString(2, token);
			pstmt.setString(3, username);
			pstmt.setString(4, content);
			pstmt.setInt(5, count);
			int i=pstmt.executeUpdate();
			if(i==1){
				//插入成功后再插入到专门的评论列表
				UserCommentBean ucb=new UserCommentBean();
				ucb.setJoke_id(joke_id);ucb.setUser_id(token);
				String s=DoUserComment(ucb);
				//System.out.println("comment:"+s);
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//获取该评论的评论次数
	public int getAllComment(String joke_id){
		if(joke_id==null||joke_id.equals("")){
			return -1;
		}
		int count=0;
		try {
			pstmt=conn.prepareStatement("SELECT count(*) FROM tb_comment WHERE joke_id=?");
			pstmt.setString(1, joke_id);
			ResultSet set=pstmt.executeQuery();
			if(set.next()){
				count=set.getInt(1);
				return count;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	//查询评论，根据当前的楼层，可以查询count次数的评论
	public ArrayList<OneCommentBean> getAllCommentByid(String joke_id,String cur_lou,int count){
		if(joke_id==null||joke_id.equals("")||cur_lou==null||cur_lou.equals("")){
			return null;
		}
		if(count<=0){
			count=10;
		}
		try {
			ArrayList<OneCommentBean> mListdata=null;
			pstmt=conn.prepareStatement("SELECT * FROM tb_comment WHERE joke_id=? AND com_lou>? ORDER BY com_lou ASC LIMIT 0,?");
			pstmt.setString(1, joke_id);
			pstmt.setString(2, cur_lou);
			pstmt.setInt(3, count);
			ResultSet set=pstmt.executeQuery();
			while(set.next()){
				if(mListdata==null){
					mListdata=new ArrayList<OneCommentBean>();
				}
				OneCommentBean ocb=new OneCommentBean();
				ocb.setCom_id(set.getString(JokeConfig.DB_COM_ID));
				ocb.setJoke_id(set.getString(JokeConfig.DB_COM_JOKE_ID));
				ocb.setUser_id(set.getString(JokeConfig.DB_COM_USER_ID));
				ocb.setUser_name(Base64Util.encode(set.getString(JokeConfig.DB_COM_USERNAME).getBytes("UTF-8")));
				ocb.setCom_content(Base64Util.encode(set.getString(JokeConfig.DB_COM_CONTENT).getBytes("UTF-8")));
				ocb.setCom_lou(set.getString(JokeConfig.DB_COM_LOU));
				ocb.setCom_time(set.getString(JokeConfig.DB_COM_TIME));
				mListdata.add(ocb);
			}
			return mListdata;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 更新赞
	 * @param joke_id
	 * @param zan_type
	 * @return
	 */
	public boolean updateZan(String joke_id,String zan_type){
		
		if(joke_id==null||joke_id.equals("")||zan_type==null){
			return false;
		}
		
		int count=getZanCount(joke_id);
		//增加
		if(zan_type.equals(JokeConfig.TYPE_ZAN_INC)){
			count++;
		}else{//减少
			count--;
		}
		try {
			pstmt=conn.prepareStatement("UPDATE tb_joke SET look_count=? WHERE joke_id=?");
			pstmt.setString(1, count+"");
			pstmt.setString(2, joke_id);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//根据joke-id获得赞的个数
	public int getZanCount(String joke_id){
		if(joke_id==null||joke_id.equals("")){
			return 0;
		}
		try {
			pstmt=conn.prepareStatement("SELECT look_count FROM tb_joke WHERE joke_id=?");
			pstmt.setString(1, joke_id);
			ResultSet set=pstmt.executeQuery();
			if(set.next()){
				String look_count=set.getString(1);
				int count=Integer.parseInt(look_count);
//				System.out.println("count:"+count);
				return count;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	//根据joke_id获得内容,这是为jsp中joke页面使用的
	//只能作为jsp页面调用，因为没有base64加密
	public SingleJoke getJokeByJokeId(String joke_id){
		if(joke_id==null||joke_id.equals("")){
			return null;
		}
		try {
			pstmt=conn.prepareStatement("SELECT * FROM tb_joke WHERE joke_id=?");
			pstmt.setString(1, joke_id);
			ResultSet set=pstmt.executeQuery();
			if(set.next())
			{
				SingleJoke joke=new SingleJoke();
				joke.setJoke_id(set.getString(JokeConfig.DB_JOKE_ID));

				String userid=set.getString(JokeConfig.DB_JOKE_USER_ID);

				joke.setUser_id(userid);
				joke.setType(set.getString(JokeConfig.DB_JOKE_TYPE));
				joke.setCreatetime(set.getString(JokeConfig.DB_JOKE_TIME));
				joke.setContent(set.getString(JokeConfig.DB_JOKE_CONTENT));
//				try {
//					joke.setContent(Base64Util.encode(set.getString(JokeConfig.DB_JOKE_CONTENT).getBytes("UTF-8")));
//				} catch (UnsupportedEncodingException e) {
//					System.out.println("base64 error");
//					e.printStackTrace();
//				}
				String mimgurl=set.getString(JokeConfig.DB_JOKE_IMGURL);
				if(mimgurl==null){
					joke.setImgurl("");
				}else{
					joke.setImgurl(mimgurl);
				}
				joke.setIshasing(set.getString(JokeConfig.DB_JOKE_ISHASIMG));
				joke.setShare_count(set.getString(JokeConfig.DB_JOKE_SHARE));
				joke.setCollect_count(set.getString(JokeConfig.DB_JOKE_COLLECT));
				joke.setLook_count(set.getString(JokeConfig.DB_JOKE_LOOK));
				//					System.out.println("joke:"+joke.toString());
				if(!userid.equals("0")){
					User u=searchById(userid);
					if(u==null){
						joke.setUser_id("0");
						joke.setUsername("");
						joke.setSex("");
					}else{
						joke.setUsername(u.getUsername());
						joke.setSex(u.getSex());
					}
				}else{
					joke.setUsername("");
					joke.setSex("");
				}
				return joke;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void doJuBao(String joke_id,String user_id){
		if(joke_id==null||joke_id.equals("")){
			return;
		}
		
		if(user_id==null||user_id.equals("")){
			user_id="0";
		}
		
		try {
			pstmt=conn.prepareStatement("INSERT INTO tb_jubao(joke_id,user_id,jubao_time) VALUES(?,?,current_timestamp())");
			pstmt.setString(1, joke_id);
			pstmt.setString(2, user_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
