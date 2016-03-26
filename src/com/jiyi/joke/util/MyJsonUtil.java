package com.jiyi.joke.util;

public class MyJsonUtil {
	
	public static class JsonString{
		private StringBuffer sb;
	
		public JsonString(){
			sb=new StringBuffer();
			sb.append("{");
		}
		
		public void addKeyValue(String key,String value){
			sb=sb.append(getJsonContentKeyValue(key,value));
		}
		public String convertoString(){
			String result=sb.toString();
			sb=null;
			result=result.substring(0,result.length()-1);
			result+="}";
			return result;
		}
	}
	//单个key value获取json
	//如 result,2->{"result":"2"}
	public static String getJsonFromKeyValue(String key,String value){
		return "{\""+key+"\":\""+value+"\"}";
	}
	//单个key,value获取json的内容
	//如result,2->"result":"2"
	public static String getJsonContentKeyValue(String key,String value){
		return "\""+key+"\":\""+value+"\",";
	}
	
}
