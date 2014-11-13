package com.project.smsinterception.ui;
import java.util.Date;

import com.project.smsinterception.R;
import com.project.smsinterception.R.id;
import com.project.smsinterception.util.SettingShares;
import com.project.smsinterception.util.TimeConverter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class SettingRecActivity extends Activity implements OnClickListener
,OnCheckedChangeListener{

	private TextView titleLefTextView;
	private TextView titleTextView;
	
	private CheckBox autoBox;
	private CheckBox brainBox;
	private CheckBox handBox;
	
	private EditText replyWordsEditText;
	
	private SharedPreferences sharedPreferences;
	
	public static void redirectToSettingActivity(Context context){
		Intent intent = new Intent(context,SettingRecActivity.class);
		context.startActivity(intent);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_rec_setting);
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		titleLefTextView = (TextView) findViewById(R.id.title_left);
		titleTextView = (TextView) findViewById(R.id.title_center);
		
		autoBox = (CheckBox) findViewById(R.id.setting_auto_reply_checkbox);
		brainBox = (CheckBox) findViewById(R.id.setting_brain_reply_checkbox);
		handBox = (CheckBox) findViewById(R.id.setting_hand_reply_checkbox);
		
		replyWordsEditText = (EditText) findViewById(R.id.setting_reply_edit);
		
		titleLefTextView.setOnClickListener(this);
		titleTextView.setText(getString(R.string.setting_title));
		titleLefTextView.setText("返回");
		
		sharedPreferences = getSharedPreferences(SettingShares.NAME, 0);
		
		initBox();
		autoBox.setOnCheckedChangeListener(this);
		brainBox.setOnCheckedChangeListener(this);
		handBox.setOnCheckedChangeListener(this);
	}

	
	private void initBox() {
		// TODO Auto-generated method stub
		int open = SettingShares.getReply(sharedPreferences);
		autoBox.setChecked(open == 0);
		brainBox.setChecked(open == 1);
		handBox.setChecked(open == 2);
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
//		openBox.setChecked(SettingShares.getOpen(sharedPreferences));
		replyWordsEditText.setText(SettingShares.getReplyWords(sharedPreferences));
		super.onStart();
	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.title_left:
				finish();
			break;
		default:
			break;
		}
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		SettingShares.storeReplyContent(replyWordsEditText.getText().toString().trim(),sharedPreferences);
				
		super.onStop();
	}
	

	/* (non-Javadoc)
	 * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton checkbox, boolean isChecked) {
		// TODO Auto-generated method stub
		if(!isChecked){
			SettingShares.storeReply(-1, sharedPreferences);
			initBox();
			return;
		}
		switch (checkbox.getId()) {
		case R.id.setting_auto_reply_checkbox:
			SettingShares.storeReply(0, sharedPreferences);
			break;
		case R.id.setting_brain_reply_checkbox:
			SettingShares.storeReply(1, sharedPreferences);
			break;
		case R.id.setting_hand_reply_checkbox:
			SettingShares.storeReply(2, sharedPreferences);
			break;
		default:
			break;
		}
		
		initBox();
	}

	
}
