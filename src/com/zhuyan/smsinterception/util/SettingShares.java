package com.zhuyan.smsinterception.util;

import android.content.SharedPreferences;

public class SettingShares {
	
	public final static String NAME = "setting";
	
	private static final String KEY_OPEN = "open";
	private static final String KEY_REPLY = "reply";
	private static final String KEY_INTER = "inter";
	private static final String KEY_REPLY_WORDS = "reply_words";
	private static final String KEY_CHOOSE_TIME = "timeType";
	private static final String KEY_STRAT_TIME = "start";
	private static final String KEY_CEND_TIME = "end";
	
	public static int getOpen(SharedPreferences sharedPreferences){
		return sharedPreferences.getInt(KEY_OPEN, 0);
	}
	
	public static int getReply(SharedPreferences sharedPreferences){
		return sharedPreferences.getInt(KEY_REPLY, 0);
	}
	
	public static String getInterception(SharedPreferences sharedPreferences){
		return sharedPreferences.getString(KEY_INTER, "");
	}
	
	public static String getReplyWords(SharedPreferences sharedPreferences){
		return sharedPreferences.getString(KEY_REPLY_WORDS, "你好");
	}
	
	public static int getTimesType(SharedPreferences sharedPreferences){
		return sharedPreferences.getInt(KEY_CHOOSE_TIME, 0);
	}
	
	public static String getStratTime(SharedPreferences sharedPreferences){
		return sharedPreferences.getString(KEY_STRAT_TIME, "00:00");
	}
	
	public static String getEndTime(SharedPreferences sharedPreferences){
		return sharedPreferences.getString(KEY_CEND_TIME, "23:59");
	}
	
	public static boolean storeStartTime(String  start,SharedPreferences sharedPreferences){
		  SharedPreferences.Editor editor = sharedPreferences.edit();
			try {
				editor.putString(KEY_STRAT_TIME, start);
				return editor.commit();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return false;
	}
	
	public static boolean storeEndTime(String end,SharedPreferences sharedPreferences){
		  SharedPreferences.Editor editor = sharedPreferences.edit();
			try {
				editor.putString(KEY_CEND_TIME, end);
				return editor.commit();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return false;
	}
	
	public static boolean storeInterceptionWords(String words,SharedPreferences sharedPreferences){
		SharedPreferences.Editor editor = sharedPreferences.edit();
		try {
			editor.putString(KEY_INTER, words);
			return editor.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	public static boolean storeTimeType(int timeType,SharedPreferences sharedPreferences){
		  SharedPreferences.Editor editor = sharedPreferences.edit();
			try {
				editor.putInt(KEY_CHOOSE_TIME, timeType);
				return editor.commit();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return false;
	}
	
	public static boolean storeOpen(int open,SharedPreferences sharedPreferences){
		 SharedPreferences.Editor editor = sharedPreferences.edit();
			try {
				editor.putInt(KEY_OPEN, open);
				return editor.commit();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return false;
	}
	
	public static boolean storeReply(int reply,SharedPreferences sharedPreferences){
		 SharedPreferences.Editor editor = sharedPreferences.edit();
			try {
				editor.putInt(KEY_REPLY, reply);
				return editor.commit();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return false;
	}
	
	public static boolean storeReplyContent(String reply,SharedPreferences sharedPreferences){
		 SharedPreferences.Editor editor = sharedPreferences.edit();
			try {
				editor.putString(KEY_REPLY_WORDS, reply);
				return editor.commit();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return false;
	}
}
