package org.jhw.keep.app.adapters;

public class SubItem extends GroupItem{
	
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
		return GroupItem.TYPE_ITEM;
	}
	
}
