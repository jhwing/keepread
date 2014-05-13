package org.jhw.keep.app.fragments;

import java.util.ArrayList;
import java.util.List;

import org.jhw.keep.Constants;
import org.jhw.keep.R;
import org.jhw.keep.app.activities.ReadActivity;
import org.jhw.keep.app.adapters.MyCoverAdapter;
import org.jhw.keep.app.adapters.MyFeedAdapter;
import org.jhw.keep.cache.ImageCache.ImageCacheParams;
import org.jhw.keep.cache.ImageFetcher;
import org.jhw.keep.datatype.MyFeed;
import org.jhw.keep.datatype.MyFeedItem;
import org.jhw.keep.parser.FeedResult;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;

public class CoverFragment extends Fragment implements AdapterView.OnItemClickListener{
    private static final String IMAGE_CACHE_DIR = "thumbs";

    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ImageFetcher mImageFetcher;
    
    private MyFeed mFeed;
    private List<MyFeedItem> data = new ArrayList<MyFeedItem>();
    FeedResult result;
    MyCoverAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mAdapter = new MyCoverAdapter(getActivity());
		
		initImageCache();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View v = inflater.inflate(R.layout.cover, container, false);
		final GridView gridView = (GridView) v.findViewById(R.id.cover_view);
		gridView.setAdapter(mAdapter);
		gridView.setOnItemClickListener(this);
		// This listener is used to get the final width of the GridView and then calculate the
        // number of columns and the width of each column. The width of each column is variable
        // as the GridView has stretchMode=columnWidth. The column width is used to set the height
        // of each view so we get nice square thumbnails.
		gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout() {
				if (mAdapter.getmNumColumns() == 0) {
					final int numColumns = (int)Math.floor(gridView.getWidth() / (mImageThumbSize + mImageThumbSpacing));
					
					if (numColumns > 0) {
						final int columnWidth = (gridView.getWidth() / numColumns) - mImageThumbSpacing;
						mAdapter.setmNumColumns(numColumns);
						mAdapter.setmItemHeight(columnWidth);
						Log.d(Constants.LOG_TAG, "columnWidth:" + columnWidth);
						gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					}
				}
			}
		});
		return v;
	}

	private void initImageCache() {
		// init image cache
		mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
		mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
		ImageCacheParams cacheParams = new ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
		mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
		mImageFetcher.setLoadingImage(R.drawable.ati);
		mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.e("org.jhw.keep", "gridView onItemClick");
		startActivity(new Intent(getActivity(), ReadActivity.class));
	}

}
