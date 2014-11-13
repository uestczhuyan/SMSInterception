package com.project.smsinterception.receiver;

import java.util.Calendar;

import com.project.smsinterception.MyApplication;
import com.project.smsinterception.R;
import com.project.smsinterception.provider.ContentProviderUtilImp;
import com.project.smsinterception.ui.listadapter.MainListAdapter;
import com.project.smsinterception.util.SettingShares;
import com.project.smsinterception.util.TimeConverter;
import com.project.smsinterception.util.slipt.BayesClassifier;
import com.project.smsinterception.util.slipt.SpecialClassify;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class SMSReceiver extends BroadcastReceiver{

	private static final String TAG = "SMSReceiver:";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub		
		
		SharedPreferences sharedPreferences = 
						context.getSharedPreferences(SettingShares.NAME, 0);
		
		//查看设置是否开启了拦截功能
		if(SettingShares.getOpen(sharedPreferences) == -1){
			return;
		}
		//短信广播包含的内容。拿出短信的 发件人号码和短信内容
		Bundle bundle = intent.getExtras();
//		System.out.println(bundle);
		Object[] OBJpdus = (Object[]) bundle.get("pdus");
		SmsMessage[] SMS = new SmsMessage[OBJpdus.length];
		for(int i = 0;i<SMS.length;i++){
			SMS[i] = SmsMessage.createFromPdu((byte[]) OBJpdus[i]);
		}
		StringBuilder sb = new StringBuilder();
		String num = null;
		for(SmsMessage currMessage : SMS){
			sb.append(currMessage.getDisplayMessageBody());
			num = currMessage.getDisplayOriginatingAddress();
		}
		String body = sb.toString().trim();
//		String num = "15828039194";
//		String body = "远方也好，附近也好，只要记得就好！平淡也好，富贵也好，只要健康就好！";
//		System.out.println("re:"+body);
//		if(Tools.isContain(body, SettingShares.getInterception(sharedPreferences))){
		
		
		//这个方法就是接入的中午分词判断的算法。  判断这个短信内容是否是垃圾短信。
		boolean ok = isOk(body);  
//		System.out.println("ok?:"+ok+"    "+(ok && isTimeOK(sharedPreferences)));
		//isTimeOk 判断现在的时候是否在拦截的时间段内
		if(ok && isTimeOK(sharedPreferences)){
			//用provider把垃圾短信储存到数据库中
			com.project.smsinterception.entity.SMS mySms = 
					new com.project.smsinterception.entity.SMS(body,num);
			ContentProviderUtilImp providerUtilImp = new ContentProviderUtilImp(context);
			providerUtilImp.insert(mySms);
			
			//回复短信处理
			replySms(sharedPreferences, num,body);
			abortBroadcast();
		}
	}
	
	/**
	 * @param sharedPreferences 
	 * @return
	 */
	private boolean isTimeOK(SharedPreferences sharedPreferences) {
		// TODO Auto-generated method stub
		int timeType = SettingShares.getOpen(sharedPreferences);
		if(timeType == 0){
			return true;
		}
		if(timeType == 2 || timeType == 1){
			Calendar today = Calendar.getInstance();
//			int weekDay = today.get(Calendar.DAY_OF_WEEK);
//			if(weekDay==1 || weekDay == 7){
//				return true;
//			}
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			start.setTime(TimeConverter.getFromString(SettingShares.getStratTime(sharedPreferences)));
			end.setTime(TimeConverter.getFromString(SettingShares.getEndTime(sharedPreferences)));
			start.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
			end.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
			
			if(today.getTimeInMillis()>= start.getTimeInMillis() && today.getTimeInMillis()<= end.getTimeInMillis()){
				return true;
			}
			System.out.println("时间段不对  不拦截");
			return false;
		}
		
		return false;
	}

	private boolean isOk(String text){
//		long time = System.currentTimeMillis();
		BayesClassifier classifier = new BayesClassifier(MyApplication.INSTANCE);//构造Bayes分类器
		SpecialClassify s=new SpecialClassify(text);
		double result0 = classifier.classify(text , 0);//进行分类
		double result1 = classifier.classify(text , 1);
		double K = 10000;
		if ((result0/result1)>K) {
//			System.out.println("ok:"+ (System.currentTimeMillis() - time));
			return true;
		}else{
//			System.out.println("no:" + (System.currentTimeMillis() - time));
			return false;
		}
	}

	private void replySms(SharedPreferences sharedPreferences, String num,String msg) {
		int reply = SettingShares.getReply(sharedPreferences);
		String content = "";
		switch (reply) {
		case 0:
			//自动回复
			sendMessage(num,MyApplication.INSTANCE.getString(R.string.setting_auto_reply_content));
			break;
		case 2:
			content = SettingShares.getReplyWords(sharedPreferences);
		case 1:
//			System.out.println("唤醒屏幕");
			//唤醒屏幕
			theScreen();
			//唤醒屏幕之后弹出恢复对话框
			createReplyDialog(MyApplication.INSTANCE,content,num,msg);
			break;
		default:
			return;
		}
	}

	/**
	 * @param num
	 */
	public static void sendMessage(String num,String content) {
		SmsManager smr = SmsManager.getDefault();
//		System.out.println("reply num:"+num+"  content:"+content);
		smr.sendMultipartTextMessage(num, null,
				smr.divideMessage(content), null, null);
	}
	
	/**
	 * @param content 
	 * @param msg 
	 * 
	 */
	private void createReplyDialog(Context context, String content,final String num, String msg) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_content, null);
		TextView textView = (TextView) view.findViewById(R.id.dialog_msg_content);
		textView.setText(MainListAdapter.getContactNameByPhoneNumber(context, num)+"\n"+msg);
		final EditText editText = (EditText)view.findViewById(R.id.dialog_edit);
		editText.setText(content);
		builder.setTitle("短信拦截回复"); 
		builder.setView(view);
		builder.setCancelable(true); 
		builder.setNegativeButton("取消", null); 
		builder.setPositiveButton("回复",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				sendMessage(num,editText.getText().toString().trim());
			}
		});
		AlertDialog dialog = builder.create(); 
		dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)); 
		dialog.show();
	}

	private void theScreen(){
		KeyguardManager km= (KeyguardManager) MyApplication.INSTANCE.getSystemService(Context.KEYGUARD_SERVICE); 
		KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock"); 
		//解锁 
		kl.disableKeyguard(); 
		//获取电源管理器对象 
		PowerManager pm=(PowerManager)  MyApplication.INSTANCE.getSystemService(Context.POWER_SERVICE); 
		//获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag 
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright"); 
		//点亮屏幕 
		wl.acquire(); 
		//释放 
		wl.release(); 
	}

}
