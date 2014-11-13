/**
 * CopyRight   2013 ZhuYan
 * @auther BLUE
 *
 * All right reserved
 *
 * Created on 2014-11-3 下午9:21:20
 * 
 */
package com.project.smsinterception.ui;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

/**
 * @author zy
 *
 * Create on 2014-11-3  下午9:21:20
 */
public class TestService extends Service{

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("com.zhuyan.test");
				sendBroadcast(intent);
			}
		}, 10*1000);
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	

}
