package com.zhuyan.smsinterception.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.smsinterception.R;
import com.zhuyan.smsinterception.util.SettingShares;
import com.zhuyan.smsinterception.util.TimeConverter;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

public class SettingActivity extends Activity implements OnClickListener
,OnCheckedChangeListener{

	private TextView titleLefTextView;
	private TextView titleTextView;
	private TextView titleRightTextView;
	
	private CheckBox openBox;
	private CheckBox replyBox;
	private CheckBox timmerBox;
	
	private Button startTime;
	private Button endTime;
	
	private SharedPreferences sharedPreferences;
	
	public static void redirectToSettingActivity(Context context){
		Intent intent = new Intent(context,SettingActivity.class);
		context.startActivity(intent);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setting);
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		titleLefTextView = (TextView) findViewById(R.id.title_left);
		titleTextView = (TextView) findViewById(R.id.title_center);
		titleRightTextView = (TextView) findViewById(R.id.title_right_tv);
		
		openBox = (CheckBox) findViewById(R.id.setting_open_checkbox);
		replyBox = (CheckBox) findViewById(R.id.setting_reply_checkbox);
		timmerBox = (CheckBox) findViewById(R.id.setting_timmer_checkbox);
		
		titleLefTextView.setOnClickListener(this);
		titleTextView.setText(getString(R.string.setting_title));
		titleRightTextView.setVisibility(View.VISIBLE);
		titleLefTextView.setText("返回");
		titleRightTextView.setOnClickListener(this);
		
		startTime  = (Button) findViewById(R.id.setting_start_times);
		endTime = (Button) findViewById(R.id.setting_end_times);
		startTime.setOnClickListener(this);
		endTime.setOnClickListener(this);
		
		
		sharedPreferences = getSharedPreferences(SettingShares.NAME, 0);
		initBox();
		openBox.setOnCheckedChangeListener(this);
		replyBox.setOnCheckedChangeListener(this);
		timmerBox.setOnCheckedChangeListener(this);
	}
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		startTime.setText("开始时间:"+SettingShares.getStratTime(sharedPreferences));
		endTime.setText("结束时间:"+SettingShares.getEndTime(sharedPreferences));
		super.onStart();
	}
	
	private void initBox(){
		int open = SettingShares.getOpen(sharedPreferences);
		openBox.setChecked(open == 0);
		replyBox.setChecked(open == 1);
		timmerBox.setChecked(open == 2);
		startTime.setClickable(open == 2);
		endTime.setClickable(open == 2);
	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.title_left:
				finish();
			break;
		case R.id.setting_end_times:
			ChooseEndtTime();
			break;
		case R.id.setting_start_times:
			choseStartTime();
			break;
		case R.id.title_right_tv:
			SettingRecActivity.redirectToSettingActivity(SettingActivity.this);
			SettingActivity.this.finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 */
	private void ChooseEndtTime() {
		// TODO Auto-generated method stub
		Date date = TimeConverter.getFromString(SettingShares.getEndTime(sharedPreferences));
		final TimePickerDialog dialog = new TimePickerDialog(SettingActivity.this,
				new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				Date date2 = new Date();
				date2.setHours(hourOfDay);
				date2.setMinutes(minute);
				SettingShares.storeEndTime(TimeConverter.formatHour.format(date2), sharedPreferences);
				endTime.setText("结束时间:"+SettingShares.getEndTime(sharedPreferences));
			}
		}, date.getHours(), date.getMinutes(), true);
		dialog.setButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
	}


	/**
	 * 
	 */
	private void choseStartTime() {
		// TODO Auto-generated method stub
		Date date = TimeConverter.getFromString(SettingShares.getStratTime(sharedPreferences));
		final TimePickerDialog dialog = new TimePickerDialog(SettingActivity.this,
				new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				Date date2 = new Date();
				date2.setHours(hourOfDay);
				date2.setMinutes(minute);
				SettingShares.storeStartTime(TimeConverter.formatHour.format(date2), sharedPreferences);
				startTime.setText("开始时间:"+SettingShares.getStratTime(sharedPreferences));
			}
		}, date.getHours(), date.getMinutes(), true);
		dialog.setButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
	}


	/* (non-Javadoc)
	 * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton checkbox, boolean isChecked) {
		// TODO Auto-generated method stub
		if(!isChecked){
			SettingShares.storeOpen(-1, sharedPreferences);
			initBox();
			return;
		}
		switch (checkbox.getId()) {
		case R.id.setting_open_checkbox:
//			System.out.println("open bx");
			SettingShares.storeOpen(0, sharedPreferences);
			break;
		case R.id.setting_reply_checkbox:
//			System.out.println("reply bx");
			SettingShares.storeOpen(1, sharedPreferences);
			break;
		case R.id.setting_timmer_checkbox:
//			System.out.println("timmer bx");
			SettingShares.storeOpen(2, sharedPreferences);
			break;
		default:
			break;
		}
		initBox();
	}

	
}
