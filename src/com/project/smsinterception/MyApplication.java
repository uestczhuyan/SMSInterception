/**
 * CopyRight   2013 ZhuYan
 * @auther BLUE
 *
 * All right reserved
 *
 * Created on 2014-10-18 下午3:52:46
 * 
 */
package com.project.smsinterception;

import android.app.Application;

/**
 * @author zy
 *
 * Create on 2014-10-18  下午3:52:46
 */
public class MyApplication  extends Application{

	public static Application INSTANCE = null;
	
	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		INSTANCE = this;
		
		super.onCreate();
	}
	
}
