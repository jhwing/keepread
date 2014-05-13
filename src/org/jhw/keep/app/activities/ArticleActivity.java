package org.jhw.keep.app.activities;

import org.jhw.keep.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class ArticleActivity extends Activity{
	String content;
	String title;
	String link;
	WebView webView;
	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webread);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		title = (String) bundle.get("title");
		content = (String) bundle.get("content");
		link = (String) bundle.get("link");
		
		ActionBar bar = getActionBar();
		bar.hide();
		webView = (WebView) findViewById(R.id.webread_content);
		TextView textView = (TextView) findViewById(R.id.webread_title);
		textView.setText(title);
//		textView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				
//				addImageClickListner();
//			}
//		});
		WebSettings settings = webView.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); //自动适应屏幕
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		webView.addJavascriptInterface(ArticleActivity.this, "imagelistner");
		String linkCss = "<link rel=\"stylesheet\" href=\"file:///android_asset/pygments.css\" type=\"text/css\"/>";
		String script = "<script type=\"text/javascript\" src=\"file:///android_asset/comm.js\"></script>";
//		content = linkCss + "<div>" + content + "</div>" + script;
		content = linkCss  + content  + script;
		Log.e("WebReadActivity", content);
		String baseUrl = "file:///android_asset";
		webView.loadDataWithBaseURL(baseUrl, content, "text/html", "UTF-8", "");
		webView.setWebViewClient(new WebViewClient (){
	        @Override  
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
	  
	            return super.shouldOverrideUrlLoading(view, url);  
	        }  
	  
	        @Override  
	        public void onPageFinished(WebView view, String url) {  
	  
	            view.getSettings().setJavaScriptEnabled(true);  
	  
	            super.onPageFinished(view, url);  
	            // html加载完成之后，添加监听图片的点击js函数  
	            addImageClickListner();  
	  
	        }  
	  
	        @Override  
	        public void onPageStarted(WebView view, String url, Bitmap favicon) {  
	            view.getSettings().setJavaScriptEnabled(true);  
	  
	            super.onPageStarted(view, url, favicon);  
	        }  
	  
	        @Override  
	        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {  
	  
	            super.onReceivedError(view, errorCode, description, failingUrl);  
	  
	        }  
		});
	}

	// 注入js函数监听  
    private void addImageClickListner() {  
    	Log.e("org.jhw.keep", "addImageClickListner");
    	webView.loadUrl("javascript:js_openImage()");  
    }  
 
    public void test(){
    	Log.e("org.jhw.keep", "javascript test");
    }
  
    public void openImage(String img) {  
        Intent intent = new Intent();  
        intent.putExtra("image", img);  
        intent.setClass(this, ShowWebImageActivity.class);  
        startActivity(intent);  
    }  
}
