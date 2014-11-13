/**
 * 
 */
package com.project.smsinterception.provider;

import com.project.smsinterception.provider.columns.SMSColumns;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
	
	static final String DATABASE_NAME = "sms.db";
	static final int DATABASE_VERSION = 1;
	private final String dropTable = "drop table if exists ";
	private final String createTable = "create table ";

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String PRIMARY_KEY = " integer primary key autoincrement,";
		db.execSQL(createTable + SMSColumns.TABLE_NAME + " (" +
				SMSColumns._ID + PRIMARY_KEY +
				SMSColumns.COLUMNS_NUM + " text, "+
				SMSColumns.COLUMNS_TIME +" long, "+
				SMSColumns.COLUMNS_BODY + " text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(dropTable + SMSColumns.TABLE_NAME);
//		db.execSQL(dropTable + VideoColumns.TABLE_VIDEO);
		onCreate(db);
	}

}