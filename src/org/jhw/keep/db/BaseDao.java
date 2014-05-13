package org.jhw.keep.db;

import android.database.sqlite.SQLiteDatabase;

public abstract class BaseDao {
	
	SQLiteDatabase db;

	public BaseDao(SQLiteDatabase db) {
		super();
		this.db = db;
	}
}
