package org.jhw.keep.app.adapters;

public class SubAddContent extends GroupItem{
	
	private String item;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	@Override
	public String getItemName() {
		return item;
	}

	@Override
	int getType() {
		return GroupItem.TYPE_ADD_CONTENT;
	}
	
}
