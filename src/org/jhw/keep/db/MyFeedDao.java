package org.jhw.keep.db;

import java.util.ArrayList;
import java.util.List;

import org.jhw.keep.Constants;
import org.jhw.keep.datatype.MyFeed;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyFeedDao extends BaseDao{
	
	public MyFeedDao(SQLiteDatabase db) {
		super(db);
	}

	public static void createTable(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE 'MY_FEED' ('_ID' INTEGER PRIMARY KEY, 'TITLE' TEXT,'LINK' TEXT,'LINK_SELF' TEXT,'LINK_WEB_SITE' TEXT,'DESCRIPTION' TEXT,'LANGUAGE' TEXT,'PUBDATE' TEXT,'LASTBUILDDATE' TEXT);");
	}

	public static void dropTable(SQLiteDatabase db) {
//		db.execSQL("");
	}

	public int insert(MyFeed feed) {
		return 0;
	}
	
	public int insertList(List<MyFeed> feeds) {
		return 0;
	}
	
	public List<MyFeed> getContentByType(String type) {
		List<MyFeed> list = new ArrayList<MyFeed>();
		StringBuilder sql = new StringBuilder("SELECT * FROM MY_FEED WHERE TYPE= ?");
		Cursor cursor = db.rawQuery(sql.toString(), new String[]{type});
		while (cursor.moveToNext()) {
			MyFeed feed = new MyFeed();
			for (int i=0; i<cursor.getColumnCount(); i++) {
				String colName = cursor.getColumnName(i).toLowerCase();
				if ("title".equals(colName)) {
					feed.setTitle(cursor.getString(i));
				} else if ("link_self".equals(colName)) {
					feed.setLinkSelf(cursor.getString(i));
				} else if ("has_add".equals(colName)) {
					int boo = cursor.getInt(i);
					if (boo == 0) {
						feed.setHasAdd(false);
					} else {
						feed.setHasAdd(true);
					}
				}
			}
			list.add(feed);
		}
		cursor.close();
		return list;
	}

	public List<MyFeed> getAllContentType() {
		List<MyFeed> list = new ArrayList<MyFeed>();
		StringBuilder sql = new StringBuilder("SELECT DISTINCT TYPE from MY_FEED");
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
	
	public List<MyFeed> getAllHasFeed() {
		List<MyFeed> lisFeeds = new ArrayList<MyFeed>();
		StringBuilder sql = new StringBuilder("SELECT * FROM MY_FEED WHERE HAS_ADD = 1");
		Cursor cursor = db.rawQuery(sql.toString(), null);
		while (cursor.moveToNext()) {
			MyFeed feed =new MyFeed();
			for (int i=0; i < cursor.getColumnCount(); i++) {
				String colName = cursor.getColumnName(i).toLowerCase();
				String colValue = cursor.getString(i);
				if ("title".equals(colName)) {
					feed.setTitle(colValue);
				}
				if ("link".equals(colName)) {
					feed.setLink(colValue);
				}
				if ("link_self".equals(colName)) {
					feed.setLinkSelf(colValue);
				}
				if ("description".equals(colName)) {
					feed.setDescription(colValue);
				}
			}
			lisFeeds.add(feed);
		}
		cursor.close();
		return lisFeeds;
	}
	
	public List<MyFeed> getAllData() {
		List<MyFeed> lisFeeds = new ArrayList<MyFeed>();
		StringBuilder sql = new StringBuilder("SELECT * FROM MY_FEED");
		Cursor cursor = db.rawQuery(sql.toString(), null);
		while (cursor.moveToNext()) {
			MyFeed feed =new MyFeed();
			for (int i=0; i < cursor.getColumnCount(); i++) {
				String colName = cursor.getColumnName(i).toLowerCase();
				String colValue = cursor.getString(i);
				if ("title".equals(colName)) {
					feed.setTitle(colValue);
				}
				if ("link".equals(colName)) {
					feed.setLink(colValue);
				}
				if ("link_self".equals(colName)) {
					feed.setLinkSelf(colValue);
				}
				if ("description".equals(colName)) {
					feed.setDescription(colValue);
				}
			}
			lisFeeds.add(feed);
		}
		return lisFeeds;
	}

	public void update(MyFeed feed) {
		if (feed.isHasAdd()) {
			StringBuilder sql = new StringBuilder("update MY_FEED SET HAS_ADD = 1 WHERE LINK_SELF = '" + feed.getLinkSelf() + "'");
			db.execSQL(sql.toString());
		} else {
			StringBuilder sql1 = new StringBuilder("update MY_FEED SET HAS_ADD = 0 WHERE LINK_SELF = '" + feed.getLinkSelf() + "'");
			db.execSQL(sql1.toString());
		}
	}

}
