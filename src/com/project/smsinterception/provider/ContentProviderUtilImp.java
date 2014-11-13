package com.project.smsinterception.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.project.smsinterception.entity.SMS;
import com.project.smsinterception.provider.columns.SMSColumns;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

public  class ContentProviderUtilImp  {

	protected final Context context;

	public ContentProviderUtilImp(Context context) {
		this.context = context;
	}

	public boolean bulkInsert(List<SMS> list) {
		// TODO Auto-generated method stub
		int length = list.size();
		ContentValues[] values = new ContentValues[length];
		for (int i = 0; i < length; i++) {
			values[i] =  SMSColumns.INSTANCE.createContentValues(list.get(i));
		}
		if ((context.getContentResolver().bulkInsert(SMSColumns.getContentUri(),
				values)) > 0) {
			return true;
		}
		return false;
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		if (id > 0) {
			context.getContentResolver().delete(SMSColumns.getContentUri(),
					BaseColumns._ID + "=" + id, null);
		}
	}

	public void delete(String where) {
		// TODO Auto-generated method stub
		if (!TextUtils.isEmpty(where))
			context.getContentResolver().delete(SMSColumns.getContentUri(), where,
					null);
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		context.getContentResolver()
				.delete(SMSColumns.getContentUri(), null, null);
	}

	public SMS get(int id) {
		// TODO Auto-generated method stub
		Cursor cursor = context.getContentResolver().query(
				SMSColumns.getContentUri(), null, BaseColumns._ID + "=" + id,
				null, null);
		if (cursor != null) {
			try {
				return SMSColumns.INSTANCE.create(cursor);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				cursor.close();
			}
		}
		return null;
	}

	public List<SMS> getAllByIdDesc(){
		return getAll( context.getContentResolver().query(
				SMSColumns.getContentUri(), null, null, null, BaseColumns._ID+" desc"));
	}
	
	public List<SMS> getAll() {
		// TODO Auto-generated method stub
		return getAll( context.getContentResolver().query(
				SMSColumns.getContentUri(), null, null, null, null));
	}

	private List<SMS> getAll(Cursor cursor) {
		if (cursor!=null) {
			try {
				if (cursor.moveToFirst()) {
					List<SMS> list = new ArrayList<SMS>(cursor.getCount());
					do {
						list.add(SMSColumns.INSTANCE.create(cursor));
					} while (cursor.moveToNext());
					return list;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				cursor.close();
			}
		}
		return Collections.emptyList();
	}


	public int insert(SMS sms) {
		// TODO Auto-generated method stub
//		String t = new String(Base64.decode(string, Base64.DEFAULT));
		try {
//			t = URLDecoder.decode(t, "utf-8");
			ContentValues values = SMSColumns.INSTANCE.createContentValues(sms);
			Uri uri = context.getContentResolver().insert(SMSColumns.getContentUri(),
					values);
			if (uri != null) {
				return Integer.parseInt(uri.getPathSegments().get(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

}
