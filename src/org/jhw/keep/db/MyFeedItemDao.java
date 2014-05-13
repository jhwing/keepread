package org.jhw.keep.db;

import android.database.sqlite.SQLiteDatabase;

public class MyFeedItemDao extends BaseDao{

	public MyFeedItemDao(SQLiteDatabase db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	public static void createTable(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE 'MY_FEED_ITEM' ('_ID' INTEGER PRIMARY KEY,'THUMBNAIL_IMAGE' TEXT, 'TITLE' TEXT,'LINK' TEXT,'COMMENTS' TEXT,'PUBDATE' TEXT,'SUMMARY' TEXT,'CONTENT' TEXT,'AUTHOR' TEXT,'COMMENT_RSS' TEXT,'SLASH_COMMENTS' TEXT);");
	}

	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("");
	}

}
