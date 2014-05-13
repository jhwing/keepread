package org.jhw.keep.app.adapters;

public abstract class GroupItem {

	public static final int TYPE_TITLE = 0;
	public static final int TYPE_ITEM = 1;
	public static final int TYPE_ADD_CONTENT = 2;
	
	public static final int TYPE_COUNT = 3;
	
	public abstract String getItemName();
	
	abstract int getType();
}
