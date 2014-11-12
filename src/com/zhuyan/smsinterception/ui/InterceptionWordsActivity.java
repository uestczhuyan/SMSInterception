package com.zhuyan.smsinterception.ui;

import java.util.List;

import com.project.smsinterception.R;
import com.zhuyan.smsinterception.ui.listadapter.InterceptionAdapter;
import com.zhuyan.smsinterception.util.SettingShares;
import com.zhuyan.smsinterception.util.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class InterceptionWordsActivity extends Activity implements OnClickListener{
	
	private  TextView leftTextView;
	private  TextView titleTextView;
	
	private EditText editText;
	private Button addButton;
	private ListView listView;
	
	
	private List<String> worlds; 
	private SharedPreferences sharedPreferences;
	private InterceptionAdapter adapter;
	
	public static void redirectToSettingActivity(Context context){
		Intent intent = new Intent(context,InterceptionWordsActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.interception_activity);
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		leftTextView = (TextView) findViewById(R.id.title_left);
		titleTextView = (TextView) findViewById(R.id.title_center);
		
		editText = (EditText) findViewById(R.id.interception_activity_edit);
		addButton = (Button) findViewById(R.id.interception_activity_addBtn);
		listView = (ListView) findViewById(R.id.interception_activity_list);
		
		leftTextView.setOnClickListener(this);
		titleTextView.setText(getString(R.string.interception_title));
		
		addButton.setOnClickListener(this);
		
		sharedPreferences = getSharedPreferences(SettingShares.NAME, 0);
		worlds = Tools.StringTohotspotIds(SettingShares.getInterception(sharedPreferences));
//		System.out.println(SettingShares.getInterception(sharedPreferences));
//		System.out.println(worlds.size());
		
		adapter = new InterceptionAdapter(worlds, this);
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.title_left:
			finish();
			break;
		case R.id.interception_activity_addBtn:
			String content = editText.getText().toString().trim();
			if(!Tools.isEmpty(content)){
				editText.setText("");
				worlds.add(0, content);
				adapter.notifyDataSetChanged();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onStop() {
		SettingShares.storeInterceptionWords(
					Tools.HotspotIdsToString(worlds), sharedPreferences);
		super.onStop();
	}
	
	
	
	
}
