/**
 * 
 */

package org.jhw.keep.app.adapters;

import java.util.ArrayList;

import org.jhw.keep.app.fragments.RefreshableFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author Andrew Neal
 */
public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

	private final ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
	
    public FragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
        notifyDataSetChanged();
    }
    
	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}
	
	/**
	 * This method update the fragments that extends the {@link RefreshableFragment} class
	 */
	public void refresh() {
		for (int i = 0; i < mFragments.size(); i++) {
			if( mFragments.get(i) instanceof RefreshableFragment ) {
				((RefreshableFragment)mFragments.get(i)).refresh();
			}
		}
	}
	
}
