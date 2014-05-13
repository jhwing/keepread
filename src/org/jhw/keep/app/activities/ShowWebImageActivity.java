package org.jhw.keep.app.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jhw.keep.R;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowWebImageActivity extends Activity implements OnKeyListener{

	private TextView imageTextView = null;  
    private String imagePath = null;  
    private ImageView imageView = null;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.show_webimage);  
        this.imagePath = getIntent().getStringExtra("image");  
  
        this.imageTextView = (TextView) findViewById(R.id.show_webimage_imagepath_textview);  
        imageTextView.setText(this.imagePath);  
        imageView = (ImageView) findViewById(R.id.show_webimage_imageview);  
  
        new MyTask().execute("");
    }  
	
    class MyTask extends AsyncTask<String, String, Drawable> {
    	
    	@Override
    	protected void onPostExecute(Drawable result) {
    			Log.e("org.jhw.keep", "ShowWebImageActivity onPostExecute");
    			if (result != null) {
    				
    				imageView.setImageBitmap(((BitmapDrawable) result).getBitmap());  
    			}
    	}

		@Override
		protected Drawable doInBackground(String... params) {
			try {
				return loadImageFromUrl(imagePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
    	
    }
    
    public static Drawable loadImageFromUrl(String url) throws IOException {  
    	
    	URL m = new URL(url);  
    	InputStream i = (InputStream) m.getContent();  
    	Drawable d = Drawable.createFromStream(i, "src");  
    	return d;  
    }  

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (KeyEvent.ACTION_DOWN == event.getAction() && keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		
		return false;
	}
    
}
