package org.jhw.keep.app.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.jhw.keep.Constants;
import org.jhw.keep.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * 程序启动界面
 * @author jhw
 *
 */
public class Intro extends Activity {

	private final int INTRO_LENGTH = 0;
	
	String DATABASES_DIR="/data/data/org.jhw.keep/databases";
	String DATABASE_NAME="keep.db";
			
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				copyDatabaseFile(Intro.this, false);
				startActivity(new Intent(Intro.this,MainActivity.class));
				overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
				finish();
			}
			
		}, INTRO_LENGTH);
		
	}
	
	public void copyDatabaseFile(Context context, boolean isfored) {  
        
        Log.v(Constants.LOG_TAG, "--------------------------------copyDatabaseFile-");  
          
        File dir = new File(DATABASES_DIR);  
        if (!dir.exists() || isfored) {  
            try {  
                dir.mkdir();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
          
        File dest = new File(dir, DATABASE_NAME);  
        if(dest.exists() && !isfored){  
            return ;  
        }  
          
        try {  
            if(dest.exists()){  
                dest.delete();  
            }  
            dest.createNewFile();     
            InputStream in = context.getResources().openRawResource(R.raw.keep);  
            int size = in.available();  
            byte buf[] = new byte[size];  
            in.read(buf);  
            in.close();  
            FileOutputStream out = new FileOutputStream(dest);  
            out.write(buf);  
            out.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
