package com.project.smsinterception.provider;

import com.project.smsinterception.provider.columns.SMSColumns;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

public class BaseContentProvider extends ContentProvider {

	public static final String TAG = "BaseContentProvider";

	private SQLiteDatabase db;
	private UriMatcher uriMatcher;
	Context context;

	public BaseContentProvider() {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(SMSColumns.AUTHORITY, SMSColumns.TABLE_NAME, SMSColumns.TABLE_CODE);
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		// TODO Auto-generated method stub
		int numInserted = 0;
		try {
			// Use a transaction in order to make the insertions run as a single
			// batch
			db.beginTransaction();
			int typeCode = uriMatcher.match(uri);
			for (numInserted = 0; numInserted < values.length; numInserted++) {
				ContentValues value = values[numInserted];
				insertType(uri, value, typeCode);
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return numInserted;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String table = SMSColumns.TABLE_NAME;
		int count = db.delete(table, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null, true);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		String type = SMSColumns.getType();
		return type;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		int typeCode = uriMatcher.match(uri);
		return insertType(uri, values, typeCode);
	}

	private Uri insertType(Uri uri, ContentValues values, int typeCode) {
		long id = db.insert(SMSColumns.getTable(), BaseColumns._ID, values);
		if (id >= 0) {
			Uri result = ContentUris.appendId(
					SMSColumns.getContentUri().buildUpon(), id).build();
			getContext().getContentResolver().notifyChange(uri, null, true);
			return result;
		}
		throw new SQLiteException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		DataBaseHelper dbHelper = new DataBaseHelper(getContext());
		db = dbHelper.getWritableDatabase();
		return db != null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(SMSColumns.getTable());
		if (sortOrder == null) {
			sortOrder = BaseColumns._ID;
		}
		Cursor cursor = builder.query(db, projection, selection, selectionArgs,
				null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count;
		count = db.update(SMSColumns.getTable(), values, selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null, true);
		return count;
	}

}
