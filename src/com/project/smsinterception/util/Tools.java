package com.project.smsinterception.util;

import java.util.ArrayList;
import java.util.List;

public class Tools {
	
	public final static boolean isEmpty(String str){
		if(str == null
				|| str.length() <= 0
				|| str.equalsIgnoreCase("null")){
			return true;
		}
		return false;
	}

	public final static List<String> StringTohotspotIds(String str){
		if(isEmpty(str)){
			return new ArrayList<String>();
		}
		String[] strs = str.split(",");
		List<String> ids = new ArrayList<String>();
		for(String string:strs){
			try {
				ids.add(string);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return ids;
	}
	
	public final static String HotspotIdsToString(List<String> ids){
		StringBuilder builder = new StringBuilder();
		for(String id:ids){
			builder.append(id)
					.append(',');
		}
		return builder.toString();
	}
	
	public static boolean isContain(String sms,String words){
		List<String> interceptionWorlds = StringTohotspotIds(words);
		for(String str:interceptionWorlds){
			if(sms.contains(str)){
				return true;
			}
		}
		return false;
	}
}
