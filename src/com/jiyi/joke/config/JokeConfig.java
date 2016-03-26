package com.jiyi.joke.config;

public class JokeConfig {
	
	
	public static final String CURRENT_DOWNLOAD_APP_URL="http://img.blog.csdn.net/20150812231418851";
	public static final int CURRENT_BANBENNUM=1;//当前版本号
	public static final String USER_BANBENNUM="user_banbennum";//这个获取的是数字 1
	//1.0.0---1
	
	//parameter Key参数键
	public static final String USERNAME="username";//用户名
	public static final String USERPWD="userpwd";
	public static final String USERTOKEN="token";//token也就是id
	public static final String USERSEX="sex";//性别
	public static final String USERMOTTO="motto";//座右铭
	public static final String JOKE_ID="joke_id";
	public static final String SCROLL_TYPE="scroll_type";//下拉刷新还是上拉加载的
	public static final String COUNT="count";
	public static final String JOKE_TYPE="type";//joke的type
	public static final String JOKE_CONTENT="content";//joke的content
	public static final String JOKE_ISHASIMG="ishasimg";//joke的是否有图片
	public static final String JOKE_CONTENT_IMG="imagename";//发表的图片名称
	public static final String QUES_CONTENT="quescontent";//反馈问题内容
	public static final String QUES_EMAIL="quesemail";//反馈问题email
	public static final String TYPE_ZAN="zan_type";
		public static final String TYPE_ZAN_INC="0";//增加赞
		public static final String TYPE_ZAN_DES="1";//减少赞
	
	public static final String LOGINTYPE="type";
		public static final String LOGINTYPE_NAME="0";
		public static final String LOGINTYPE_QQ="1";
		public static final String LOGINTYPE_XINLANG="2";
		public static final String LOGINTYPE_WEIXIN="3";
	public static final String SCROLL_REFRESH="1";//是刷新加载最新数据
	public static final String SCROLL_LOADINGMORE="2";//还是加载更多的旧的数据
	
	public static final String FRAGMENT_TYPE="fragtype";//fragment的类型
		public static final String FRAGMENT_TYPE_NEW="0";//fragment的类型-最新
		public static final String FRAGMENT_TYPE_TOP="1";//fragment的类型-top
		public static final String FRAGMENT_TYPE_JOKE="2";//fragment的类型-joke
		public static final String FRAGMENT_TYPE_GIRL="3";//fragment的类型-girl
		
	
	public static final String USER_ABOUT_JOKE_TYPE="joketype";//这是个人中心浏览，分享或者收藏的类型
		public static final String USER_ABOUT_JOKE_TYPE_COLLECT="0";//收藏
		public static final String USER_ABOUT_JOKE_TYPE_LOOK="1";//浏览
		public static final String USER_ABOUT_JOKE_TYPE_SHARE="2";//分享
		public static final String USER_ABOUT_JOKE_TYPE_COMMENT="3";//评论
		public static final String USER_ABOUT_JOKE_TYPE_SELFPUB="4";//自己发表的
	//db里的键值
	public static final String DB_USERNAME="user_name";
	public static final String DB_USER_SEX="user_sex";
	public static final String DB_USER_MOTTO="user_motto";
	
	public static final String DB_JOKE_ID="joke_id";
	public static final String DB_JOKE_USER_ID="user_id";
	public static final String DB_JOKE_TYPE="type";
	public static final String DB_JOKE_TIME="timecreate";
	public static final String DB_JOKE_CONTENT="content";
	public static final String DB_JOKE_IMGURL="imgurl";
	public static final String DB_JOKE_ISHASIMG="ishasimg";
	public static final String DB_JOKE_SHARE="share_count";
	public static final String DB_JOKE_COLLECT="collect_count";
	public static final String DB_JOKE_LOOK="look_count";
	//Comment评论
	public static final String COMMENT_CONTENT="com_content";
	public static final String COMMENT_CUR_LOU="cur_lou";
	public static final String DB_COM_ID="com_id";
	public static final String DB_COM_JOKE_ID="joke_id";
	public static final String DB_COM_USER_ID="user_id";
	public static final String DB_COM_USERNAME="user_name";
	public static final String DB_COM_CONTENT="com_content";
	public static final String DB_COM_LOU="com_lou";
	public static final String DB_COM_TIME="com_time";
	//
//	public static final String IP_HOST=
	public static final String IP_HOST="mjokeapp.duapp.com";//例子地址
//	public static final String IP_HOST="jiyiren.tunnel.mobi";//例子地址
	public static final String PROJECT_NAME="joke";//项目名
	public static final String SAVE_HEADER_FOLDER_NAME="images";//保存头像图片的文件名,在根目录下
	public static final String SAVE_IMGCONTENT_FOLER_NAME="imgcontent";//保存头像图片的文件名,在根目录下
	public static final String SAVE_FILE_TEMP_NAME="imgtmp";//临时文件的文件名，在根目录下
	
}
