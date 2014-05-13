package org.jhw.keep.db;

import org.jhw.keep.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbManager {
	
	public static final int SCHEMA_VERSION = 2;

	SQLiteDatabase db;
	
	MyDatabaseHelper dbHelper;
	
	public MyDbManager(Context context, String name, Object object) {
		dbHelper = new MyDatabaseHelper(context, name, null, SCHEMA_VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	public void createAllTables(SQLiteDatabase db) {
		MyFeedDao.createTable(db);
		MyFeedItemDao.createTable(db);
		ContentLibDao.createTable(db);
		initData();
	}
	
	private void initData() {
		
	}

	public void clear() {
		if (db != null) {
			db.close();
		}
	}
	
	public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	public MyDatabaseHelper getDbHelper() {
		return dbHelper;
	}

	public void setDbHelper(MyDatabaseHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
	
	public class MyDatabaseHelper extends SQLiteOpenHelper {

		public MyDatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.e(Constants.LOG_TAG, "SQLiteDatabase  onCreate " + db);
			createAllTables(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			ContentLibDao.createTable(db);
		}

	}
}
