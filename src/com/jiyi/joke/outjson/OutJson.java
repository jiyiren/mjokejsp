package com.jiyi.joke.outjson;

import java.util.List;

import com.jiyi.joke.bean.OneCommentBean;
import com.jiyi.joke.bean.SingleJoke;
import com.jiyi.joke.bean.User;
import com.jiyi.joke.config.JokeConfig;
import com.jiyi.joke.util.MyJsonUtil;
import com.jiyi.joke.util.MyJsonUtil.JsonString;

public class OutJson {
	//json key-value json的键
	public static final String RESULT="result";
		public static final String SUCCESS="1";
		public static final String FAIL="0";
		public static final String FAIL_EXIST="04";//数据已经存在
		public static final String FAIL_NODATA="03";//查询没有数据可更新了
	//Login
	public static final String FAIL_NOMAN="01";//查无此人
	public static final String FAIL_PWDFAULT="02";//密码错误
	
	public static final String TOKEN="token";
	public static final String USERNAME="username";
	public static final String SEX="sex";
	public static final String MOTTO="motto";
	
	public static final String DATAS="datas";
	
	
	//ReceiveUserPic
	public static final String HEADER_URL="headurl";//头像地址
	public static final String DOWNLOAD_URL="appurl";//更新地址
	/**
	 * 注册结果
	 * 格式{"result":"1/0"}
	 * @param result
	 * @return
	 */
	public static String userReg(String result){
		String json="{\""+RESULT+"\":\""+result+"\"}";
		return json;
	}
	
	/**
	 * 登录结果
	 * 格式：
	 * 失败：{"result":"01/02"}
	 * 成功：{"result":"1","token":user.id}
	 * @param result
	 * @param id
	 * @return
	 */
	public static String userLogin(String result,User user){
		String json=null;
		if(result.equals(FAIL_NOMAN)||result.equals(FAIL_PWDFAULT)){
			json="{\""+RESULT+"\":\""+result+"\"}";
		}else{
			json="{\""+RESULT+"\":\""+result+"\","+
		"\""+TOKEN+"\":\""+user.getId()+"\","+
		"\""+SEX+"\":\""+user.getSex()+"\","+
		"\""+MOTTO+"\":\""+user.getMotto()+"\"}";
		}
		return json;
	}
	
	/**
	 * 接收图片成功后返回
	 * 格式：
	 * 成功：{"result":"1","headurl":imgurl}
	 * 失败：{"result":"0"}
	 * @param result
	 * @param imgurl
	 * @return
	 */
	public static String receiveUserPic(String result,String imgurl){
		String json=null;
		if(result.equals(FAIL)){
			json="{\""+RESULT+"\":\""+result+"\"}";
		}else{
			json="{\""+RESULT+"\":\""+result+"\",\""+HEADER_URL+"\":\""+imgurl+"\"}";
		}
		return json;
	}
	
	/**
	 * 修改用户信息
	 * 成功：{"result":"1"}
	 * 失败：{"result":"0"}
	 * @param result
	 * @return
	 */
	public static String modifyUserInfo(String result){
		String json=null;
		json="{\""+RESULT+"\":\""+result+"\"}";
		return json;
	}

	/**
	 * 获取joke数据
	 * 成功：{"result":"1","datas":[
	 * {},
	 * {},
	 * {}
	 * ]}
	 * 失败：{"result":"03"}
	 * @param result
	 * @return
	 */
	public static final String JOKE_ID="joke_id";
	public static final String JOKE_USER_ID="user_id";
	public static final String JOKE_USERNAME="username";
	public static final String JOKE_USERSEX="sex";
	public static final String JOKE_TYPE="type";
	public static final String JOKE_TIME="timecreate";
	public static final String JOKE_CONTENT="content";
	public static final String JOKE_IMGURL="imgurl";
	public static final String JOKE_ISHASIMG="ishasimg";
	public static final String JOKE_SHARE="share_count";
	public static final String JOKE_COLLECT="collect_count";
	public static final String JOKE_LOOK="look_count";
	public static String getJokes(String result,List<SingleJoke> jokes){
		String json=null;
		if(result.equals(FAIL_NODATA)){
			json="{\""+RESULT+"\":\""+result+"\"}";
		}else{
			String arraystring="[";
			SingleJoke joke;
			for (int i = 0; i < jokes.size(); i++) {
				joke=jokes.get(i);
				JsonString mjson=new JsonString();
				mjson.addKeyValue(JOKE_ID, joke.getJoke_id());
				mjson.addKeyValue(JOKE_USER_ID, joke.getUser_id());
				mjson.addKeyValue(JOKE_USERNAME, joke.getUsername());
				mjson.addKeyValue(JOKE_USERSEX, joke.getSex());
				mjson.addKeyValue(JOKE_TYPE, joke.getType());
				mjson.addKeyValue(JOKE_TIME, joke.getCreatetime());
				mjson.addKeyValue(JOKE_CONTENT, joke.getContent());
				mjson.addKeyValue(JOKE_IMGURL, joke.getImgurl());
				mjson.addKeyValue(JOKE_ISHASIMG, joke.getIshasing());
				mjson.addKeyValue(JOKE_SHARE, joke.getShare_count());
				mjson.addKeyValue(JOKE_COLLECT, joke.getCollect_count());
				mjson.addKeyValue(JOKE_LOOK, joke.getLook_count());
				if(i!=0){
					arraystring+=(","+mjson.convertoString());
				}else{
					arraystring+=mjson.convertoString();
				}
			}
			arraystring+="]";
			json="{\""+RESULT+"\":\""+result+"\",\""+DATAS+"\":"+arraystring+"}";
		}
		return json;
	}
	/**
	 * 返回用户信息
	 * 成功:{"result":"1","username":"jiyi","sex":"woman","motto":"走自己的路"}
	 * 失败:{"result":"0"}
	 * @param result
	 * @param user
	 * @return
	 */
	public static String getUserInfo(String result,User user){
		String json=null;
		if(result.equals(FAIL)){
			json="{\""+RESULT+"\":\""+result+"\"}";
		}else{
			JsonString mjson=new JsonString();
			mjson.addKeyValue(RESULT, result);
			mjson.addKeyValue(USERNAME, user.getUsername());
			mjson.addKeyValue(SEX, user.getSex());
			mjson.addKeyValue(MOTTO, user.getMotto());
			json=mjson.convertoString();
		}
		return json;
	}
	
	/**
	 * 插入joke返回结果
	 * 成功:{"result":"1"}
	 * 失败:{"result":"0"}
	 * @param result
	 * @return
	 */
	public static String publishJoke(String result){
		String json=null;
		json="{\""+RESULT+"\":\""+result+"\"}";
		return json;
	}
	/**
	 * 插入收藏的结果
	 * 成功:{"result":"1"}
	 * 失败:{"result":"0"}
	 * 已经存在:{"result":"04"}
	 * @param result
	 * @return
	 */
	public static String outUserCollect(String result){
		String json=null;
		json="{\""+RESULT+"\":\""+result+"\"}";
		return json;
	}
	/**
	 * 插入浏览的结果
	 * 成功:{"result":"1"}
	 * 失败:{"result":"0"}
	 * @param result
	 * @return
	 */
	public static String outUserLook(String result){
		String json=null;
		json="{\""+RESULT+"\":\""+result+"\"}";
		return json;
	}
	/**
	 * 问题反馈
	 * @param result
	 * @return
	 */
	public static String outSendQues(String result){
		String json=null;
		json="{\""+RESULT+"\":\""+result+"\"}";
		return json;
	}
	/**
	 * 插入评论成功否
	 * @param result
	 * @return
	 */
	public static String outInsertCom(String result){
		String json=null;
		json="{\""+RESULT+"\":\""+result+"\"}";
		return json;
	}
	
	//输出评论json
	public static String outAllComments(String result,List<OneCommentBean> coms){
		String json=null;
		if(result.equals(FAIL_NODATA)){
			json="{\""+RESULT+"\":\""+result+"\"}";
		}else{
			String arraystring="[";
			OneCommentBean comm;
			for (int i = 0; i < coms.size(); i++) {
				comm=coms.get(i);
				JsonString mjson=new JsonString();
				mjson.addKeyValue(JokeConfig.DB_COM_ID, comm.getCom_id());
				mjson.addKeyValue(JokeConfig.DB_COM_JOKE_ID, comm.getJoke_id());
				mjson.addKeyValue(JokeConfig.DB_COM_USER_ID, comm.getUser_id());
				mjson.addKeyValue(JokeConfig.DB_COM_USERNAME, comm.getUser_name());
				mjson.addKeyValue(JokeConfig.DB_COM_CONTENT, comm.getCom_content());
				mjson.addKeyValue(JokeConfig.DB_COM_LOU, comm.getCom_lou());
				mjson.addKeyValue(JokeConfig.DB_COM_TIME, comm.getCom_time());
				if(i!=0){
					arraystring+=(","+mjson.convertoString());
				}else{
					arraystring+=mjson.convertoString();
				}
			}
			arraystring+="]";
			json="{\""+RESULT+"\":\""+result+"\",\""+DATAS+"\":"+arraystring+"}";
		}
		return json;
	}
	
	/**
	 * 输出升级结果
	 * 失败:{"result":"0"}
	 * 成功:{"result":"1","appurl":"http://ddd"}
	 * @param result
	 * @param appurl
	 * @return
	 */
	public static String outUpdate(String result,String appurl){
		String json=null;
		if(result.equals(FAIL)){
			json="{\""+RESULT+"\":\""+result+"\"}";
		}else{
			json="{\""+RESULT+"\":\""+result+"\",\""+DOWNLOAD_URL+"\":\""+appurl+"\"}";
		}
		return json;
	}
	
	
	/**
	 * 点赞
	 * @param result
	 * @return
	 */
	public static String outZan(String result){
		String json=null;
		json="{\""+RESULT+"\":\""+result+"\"}";
		return json;
	}
}
