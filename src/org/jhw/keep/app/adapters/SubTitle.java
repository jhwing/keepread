package org.jhw.keep.app.adapters;

public class SubTitle extends GroupItem{
	
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String getItemName() {
		return title;
	}

	@Override
	int getType() {
		return GroupItem.TYPE_TITLE;
	}
	
}
