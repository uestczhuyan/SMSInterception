package com.project.smsinterception.ui;

import java.util.List;

import com.project.smsinterception.MyApplication;
import com.project.smsinterception.R;
import com.project.smsinterception.entity.SMS;
import com.project.smsinterception.provider.ContentProviderUtilImp;
import com.project.smsinterception.ui.listadapter.MainListAdapter;
import com.project.smsinterception.util.slipt.BayesClassifier;
import com.project.smsinterception.util.slipt.SpecialClassify;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements 
			View.OnClickListener,AdapterView.OnItemClickListener{

	private TextView leftView;
	private TextView titleView;
	private ImageView setttingView;
	
	private TextView noSmsTextView;
	private ListView listView;
	
	private ContentProviderUtilImp providerUtilImp;
	
	private MainListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
		
//		Intent intent = new Intent(this,TestService.class);
//		startService(intent);
		
//		testAddTestData();
//		testSlipt("吃饭了么？");
//		isOk("吃饭了么？");
//		
//		testSlipt("远方也好，附近也好，只要记得就好！平淡也好，富贵也好，只要健康就好！电话也好，信息也好，随着我的祝福你幸福就好！祝你新年快乐！万事如意！鲁晓宇给您的晚餐添个小菜～");
//		isOk("远方也好，附近也好，只要记得就好！平淡也好，富贵也好，只要健康就好！电话也好，信息也好，随着我的祝福你幸福就好！祝你新年快乐！万事如意！鲁晓宇给您的晚餐添个小菜～");
	}
	

	/**
	 * 
	 */
	private void testSlipt(String text) {
		// TODO Auto-generated method stub
		BayesClassifier classifier = new BayesClassifier(this);//构造Bayes分类器
		SpecialClassify s=new SpecialClassify(text);
		if (s.CheckSpecialWordsMethod()){
			double result0 = classifier.classify(text , 0);//进行分类
			double result1 = classifier.classify(text , 1);
			double K;
			K=10000;
			if ((result0/result1)<K){
				System.out.println("no" +text);
			
			}else {
				System.out.println("Yes" + text);
			}
		}
	}
	
	private boolean isOk(String text){
		long time = System.currentTimeMillis();
		BayesClassifier classifier = new BayesClassifier(MyApplication.INSTANCE);//构造Bayes分类器
		SpecialClassify s=new SpecialClassify(text);
		double result0 = classifier.classify(text , 0);//进行分类
		double result1 = classifier.classify(text , 1);
		double K;
		if (text.length()<15) K = (text.length()/7) * (text.length()/7);
		else K=99;
		if ((result0/result1)>K) {
			System.out.println(text+"   ok:"+ (System.currentTimeMillis() - time));
			return true;
		}else{
			System.out.println(text+"  no:" + (System.currentTimeMillis() - time));
			return false;
		}
	}


	private void testAddTestData() {
		// TODO Auto-generated method stub
		if(providerUtilImp == null){
			providerUtilImp = new ContentProviderUtilImp(this);
		}
		for(int i=0;i<=10;i++){
			SMS sms = new SMS("haibucuo "+i, "15828039194");
			providerUtilImp.insert(sms);
		}
		for(int i=0;i<=10;i++){
			SMS sms = new SMS("haibucuo "+i, "15923775763");
			providerUtilImp.insert(sms);
		}
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		listNotify();
	}


	private void listNotify() {
		// TODO Auto-generated method stub
		if(providerUtilImp == null){
			providerUtilImp = new ContentProviderUtilImp(this);
		}
		List<SMS> sms = providerUtilImp.getAllByIdDesc();
		if(sms == null || sms.size() <= 0){
			if(noSmsTextView.getVisibility() != View.VISIBLE){
				noSmsTextView.setVisibility(View.VISIBLE);
			}
			return;
		}
		if(adapter == null){
			adapter = new MainListAdapter(this, sms, providerUtilImp);
		}
		if(noSmsTextView.getVisibility() != View.GONE){
			noSmsTextView.setVisibility(View.GONE);
		}
		listView.setAdapter(adapter);
		
	}


	private void init() {
		// TODO Auto-generated method stub
		leftView = (TextView) findViewById(R.id.title_left);
		titleView = (TextView) findViewById(R.id.title_center);
		setttingView = (ImageView) findViewById(R.id.title_right);
		setttingView.setVisibility(View.VISIBLE);
		
		noSmsTextView = (TextView) findViewById(R.id.main_activity_nomsgs);
		listView = (ListView) findViewById(R.id.main_activity_list);
		
		leftView.setText(getString(R.string.action_exit));
		leftView.setOnClickListener(this);
		titleView.setText(getString(R.string.main_title));
		setttingView.setOnClickListener(this);
		listView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_settings:
				SettingActivity.redirectToSettingActivity(MainActivity.this);
			break;
		case R.id.action_exit:
				finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.title_left:
				finish();
			break;
		case R.id.title_right:
				SettingActivity.redirectToSettingActivity(MainActivity.this);
			break;
		default:
			break;
		}
	}
	
	

}
