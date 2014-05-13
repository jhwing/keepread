package org.jhw.keep.app.activities;

import java.util.ArrayList;
import java.util.List;

import org.jhw.keep.Constants;
import org.jhw.keep.R;
import org.jhw.keep.app.adapters.GroupItem;
import org.jhw.keep.app.adapters.SlidingMainListAdapter;
import org.jhw.keep.app.adapters.SubAddContent;
import org.jhw.keep.app.adapters.SubItem;
import org.jhw.keep.app.adapters.SubTitle;
import org.jhw.keep.app.fragments.ChannelFragment;
import org.jhw.keep.app.fragments.CoverFragment;
import org.jhw.keep.app.fragments.MyFeedFragment;
import org.jhw.keep.app.task.AsyncRefreshListener;
import org.jhw.keep.app.task.MyFeedListTask;
import org.jhw.keep.datatype.MyFeed;
import org.jhw.keep.parser.FeedResult;
import org.jhw.keep.util.Utility;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @author jihongwen
 */
public class MainActivity extends BaseActivity implements
		ListView.OnItemClickListener , AsyncRefreshListener<List<MyFeed>>{

	private DrawerLayout mDrawerLayout;
	private LinearLayout mSlidingLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private SlidingMainListAdapter mAdapter;
	private List<MyFeed> mData = new ArrayList<MyFeed>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		initActionBar();
		initSlidingMenu();
		
		MyFeedFragment feedFragment = new MyFeedFragment();
		Bundle args = new Bundle();
		args.putString("channelurl", Constants.ATOM_36kr);
		feedFragment.setArguments(args);
		replaceContentFragment(0, feedFragment);
	}

	@Override
	public void onBackPressed() {
		imageLoader.stop();
		super.onBackPressed();
	}
	
	private void initActionBar() {
		final ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_bg));
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setHomeButtonEnabled(true);
	}

	private void initSlidingMenu() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mSlidingLayout = (LinearLayout) findViewById(R.id.drawer_sliding_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_sliding_mainlist);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		mAdapter = new SlidingMainListAdapter(this);
		mDrawerList.setAdapter(mAdapter);
		Utility.setListViewHeightBasedOnChildren(mDrawerList);
		mDrawerList.setOnItemClickListener(this);

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout,                                  /* DrawerLayout object */
		R.drawable.ic_drawer,                           /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open,                           /* "open drawer" description for accessibility */
		R.string.drawer_close                           /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				MyFeedListTask feedListTask = new MyFeedListTask(MainActivity.this);
				feedListTask.setAsyncRefreshListener(MainActivity.this);
				feedListTask.execute("");
				// getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

//	private List<GroupItem> initData() {
//		String[] itemArray = getResources().getStringArray(R.array.item_array);
//		for (String item : itemArray) {
//			if ("item_title".equals(item)) {
//				SubTitle title = new SubTitle();
//				title.setTitle("");
//				mData.add(title);
//			} else if (getString(R.string.add_content).equals(item)) {
//				SubAddContent itadd = new SubAddContent();
//				itadd.setItem("Add Content");
//				mData.add(itadd);
//			} else {
//				SubItem sItem = new SubItem();
//				sItem.setItem(item);
//				mData.add(sItem);
//			}
//		}
//		return mData;
//	}

	private void replaceContentFragment(int position, Fragment fragment) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		fragmentTransaction.replace(R.id.main_content_frame, fragment).commitAllowingStateLoss();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mSlidingLayout);
		menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.action_websearch:
			// create intent to perform web search for this planet
			Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
			intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
			// catch event that there's no activity to handle intent
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			} else {
				Toast.makeText(this, R.string.app_not_available,
						Toast.LENGTH_LONG).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (intent == null) {
			return;
		}
		
		intent.getStringExtra("channelurl");
		String itemStr = intent.getStringExtra("channelurl");
		if (TextUtils.isEmpty(itemStr)) {
			return;
		}
		
		MyFeedFragment feedFragment = new MyFeedFragment();
		Bundle args = new Bundle();
		args.putString("channelurl", itemStr);
		feedFragment.setArguments(args);
		replaceContentFragment(0, feedFragment);
	}
	
	/* The click listner for ListView in the navigation drawer */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MyFeed item = mData.get(position);
		String itemStr = item.getLinkSelf();
		MyFeedFragment feedFragment = new MyFeedFragment();
		Bundle args = new Bundle();
		args.putString("channelurl", itemStr);
		feedFragment.setArguments(args);
		replaceContentFragment(position, feedFragment);
//		if (getString(R.string.cover).equals(itemStr)) {
//			/* cover view */
//			replaceContentFragment(position, new CoverFragment());
//		} else if (getString(R.string.read_later).equals(itemStr)) {
//			
//		} else if (getString(R.string.reading_notes).equals(itemStr)) {
//
//		} else if (getString(R.string.settings).equals(itemStr)) {
//
//		} else if (getString(R.string.feedback).equals(itemStr)) {
//
//		} else if (getString(R.string.share_me).equals(itemStr)) {
//
//		} else if (getString(R.string.help).equals(itemStr)) {
//
//		}
		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mSlidingLayout);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onAsyncRefreshComplete(List<MyFeed> myFeeds) {
		if (myFeeds != null) {
			mData.clear();
			mData.addAll(myFeeds);
			Log.e(Constants.LOG_TAG, mData + "");
			mAdapter.refresh(mData);
		}
	};
}
