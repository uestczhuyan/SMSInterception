package com.project.smsinterception.provider.columns;

import com.project.smsinterception.entity.SMS;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class SMSColumns implements BaseColumns{
	
	public static final String AUTHORITY = "com.zhuyan.smsinterception.provider";
	private static final Uri CONTENT_URI = Uri
			.parse("content://com.zhuyan.smsinterception.provider/sms");
	private static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.cm.sms";

	public static final String COLUMNS_BODY = "body";
	public static final String COLUMNS_NUM = "num";
	public static final String COLUMNS_TIME = "time";
	
	
	public static final String TABLE_NAME = "sms";
	public static final int TABLE_CODE = 1;

	public static SMSColumns INSTANCE = new SMSColumns();
	
	private SMSColumns(){
	}
	
	public static  String getTable() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

	public static  String getType() {
		// TODO Auto-generated method stub
		return CONTENT_TYPE;
	}

	public static  Uri getContentUri() {
		// TODO Auto-generated method stub
		return CONTENT_URI;
	}

	public SMS create(Cursor cursor) {
		// TODO Auto-generated method stub
		SMS sms = new SMS();
		sms.setBody(cursor.getString(cursor.getColumnIndexOrThrow(COLUMNS_BODY)));
		sms.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
		sms.setNum(cursor.getString(cursor.getColumnIndexOrThrow(COLUMNS_NUM)));
		sms.setTime(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMNS_TIME)));
		return sms;
	}

	public ContentValues createContentValues(SMS sms) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(COLUMNS_BODY, sms.getBody());
		values.put(COLUMNS_NUM, sms.getNum());
		values.put(COLUMNS_TIME, sms.getTime());
		return values;
	}

}
