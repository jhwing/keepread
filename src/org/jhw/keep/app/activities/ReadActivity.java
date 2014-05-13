package org.jhw.keep.app.activities;

import org.jhw.keep.R;
import org.jhw.keep.app.fragments.ChannelFragment;
import org.jhw.keep.datatype.MyFeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ReadActivity extends FragmentActivity {

	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	private TextView textView;
	private TextView titleView;
	String content;
	String title;
	int width;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		ChannelFragment fragment = new ChannelFragment();
		Bundle bundle = intent.getExtras();
		MyFeed feed = (MyFeed) bundle.getSerializable("feed");
		if (feed != null) {
			setTitle(feed.getTitle());
		}
		fragment.setArguments(bundle);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.channel_frame, fragment).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.readopt, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
