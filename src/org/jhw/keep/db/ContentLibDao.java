package org.jhw.keep.db;

import java.util.ArrayList;
import java.util.List;

import org.jhw.keep.Constants;
import org.jhw.keep.datatype.MyFeed;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ContentLibDao extends BaseDao{

	public ContentLibDao(SQLiteDatabase db) {
		super(db);
	}

	public static void createTable(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE 'CONTENT_LIB' ('_ID' INTEGER PRIMARY KEY,'TYPE' TEXT, 'FEED_LINK' TEXT,'FEED_TITLE' TEXT);");
	}
	
	public List<MyFeed> getContentByType(String type) {
		List<MyFeed> list = new ArrayList<MyFeed>();
		StringBuilder sql = new StringBuilder("SELECT * FROM CONTENT_LIB WHERE TYPE= ?");
		Cursor cursor = db.rawQuery(sql.toString(), new String[]{type});
		while (cursor.moveToNext()) {
			MyFeed feed = new MyFeed();
			for (int i=0; i<cursor.getColumnCount(); i++) {
				String colName = cursor.getColumnName(i).toLowerCase();
				String colValue = cursor.getString(i);
				if ("feed_title".equals(colName)) {
					feed.setTitle(colValue);
				} else if ("feed_link".equals(colName)) {
					feed.setLinkSelf(colValue);
				}
			}
			list.add(feed);
		}
		cursor.close();
		return list;
	}

	public List<MyFeed> getAllContentType() {
		List<MyFeed> list = new ArrayList<MyFeed>();
		StringBuilder sql = new StringBuilder("SELECT DISTINCT TYPE from CONTENT_LIB");
		Cursor cursor = db.rawQuery(sql.toString(), null);
		while (cursor.moveToNext()) {
			MyFeed feed = new MyFeed();
			for (int i=0; i<cursor.getColumnCount(); i++) {
				String colName = cursor.getColumnName(i).toLowerCase();
				String colValue = cursor.getString(i);
				if ("type".equals(colName)) {
					feed.setTitle(colValue);
				}
			}
			list.add(feed);
		}
		cursor.close();
		return list;
	}
	
	public List<MyFeed> getAllData() {
		List<MyFeed> lisFeeds = new ArrayList<MyFeed>();
		StringBuilder sql = new StringBuilder("SELECT * FROM CONTENT_LIB");
		Cursor cursor = db.rawQuery(sql.toString(), null);
		int colCount = cursor.getColumnCount();
		int count = cursor.getCount();
		Log.e(Constants.LOG_TAG, "ColumnCount is :" + colCount + " count is :" + count);
		
		while (cursor.moveToNext()) {
			MyFeed feed =new MyFeed();
			for (int i=0; i < cursor.getColumnCount(); i++) {
				String colName = cursor.getColumnName(i).toLowerCase();
				String colValue = cursor.getString(i);
				
				if ("feed_title".equals(colName)) {
					feed.setTitle(colValue);
				}
				if ("feed_link".equals(colName)) {
					feed.setLink(colValue);
				}
				Log.e(Constants.LOG_TAG, "colName " + colName + " colValue " + colValue);
			}
			lisFeeds.add(feed);
		}
		cursor.close();
		return lisFeeds;
	}
}
