package com.zhuyan.smsinterception.ui.listadapter;

import java.util.List;

import com.project.smsinterception.R;
import com.zhuyan.smsinterception.MyApplication;
import com.zhuyan.smsinterception.entity.SMS;
import com.zhuyan.smsinterception.provider.ContentProviderUtilImp;
import com.zhuyan.smsinterception.receiver.SMSReceiver;
import com.zhuyan.smsinterception.util.SettingShares;
import com.zhuyan.smsinterception.util.TimeConverter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class MainListAdapter extends BaseAdapter {

	private ContentProviderUtilImp providerUtilImp;
	private List<SMS> smsList;
	private Context context;
	private LayoutInflater mInflater;
	private SharedPreferences sharedPreferences;
	
	
	public MainListAdapter(Context context,List<SMS> smsList,ContentProviderUtilImp providerUtilImp) {
		this.providerUtilImp = providerUtilImp;
		this.smsList = smsList;
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.sharedPreferences = context.getSharedPreferences(SettingShares.NAME, 0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return smsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.man_listitem, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.setValue(position);
		return convertView;
	}
	
	private class ViewHolder{
		private TextView number;
		private TextView time;
		private TextView content;
		private TextView deleteButton;
		private TextView replyButton;
		
		private int pos;
		
		public ViewHolder(View view){
			number = (TextView) view.findViewById(R.id.mainlist_number);
			time =  (TextView) view.findViewById(R.id.mainlist_time);
			content =  (TextView) view.findViewById(R.id.mainlist_content);
			deleteButton =  (TextView) view.findViewById(R.id.mainlist_delete);
			
			deleteButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					new AlertDialog.Builder(context)
					   .setTitle(R.string.app_name)
					   .setMessage(R.string.delet_msg)
					   .setPositiveButton(R.string.dialog_sure, 
							   new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									deletePos();
								}
							})
						.setNegativeButton(R.string.dialog_cancel, null)
						.show();
				}
			});
			
			replyButton =  (TextView) view.findViewById(R.id.mainlist_reply);
			replyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final EditText editText = new EditText(context);
					if(SettingShares.getReply(sharedPreferences) == 2){
						editText.setText(SettingShares.getReplyWords(sharedPreferences));
					}
					new AlertDialog.Builder(context)
					   .setTitle(R.string.app_name)
					   .setView(editText)
					   .setPositiveButton("回复", 
							   new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									SMSReceiver.sendMessage(smsList.get(pos).getNum(), editText.getText().toString().trim());
								}
							})
						.setNegativeButton(R.string.dialog_cancel, null)
						.show();
				}
			});
		}
		
		public void setValue(int pos){
			this.pos = pos;
			SMS sms = smsList.get(pos);
			number.setText(getContactNameByPhoneNumber(context, sms.getNum()));
			time.setText(TimeConverter.convertToStr2(sms.getTime()));
			content.setText(sms.getBody());
		}

		protected void deletePos() {
			// TODO Auto-generated method stub
			SMS sms = smsList.get(pos);
			providerUtilImp.delete(sms.getId());
			smsList.remove(pos);
			notifyDataSetChanged();
		}
	}
	
	 public  static String getContactNameByPhoneNumber(Context context, String num) {
		          String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME,
		                  ContactsContract.CommonDataKinds.Phone.NUMBER };
		  
		          // 将自己添加到 msPeers 中
		          Cursor cursor = context.getContentResolver().query(
		                 ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
		                 projection, // Which columns to return.
		                 ContactsContract.CommonDataKinds.Phone.NUMBER + " = '"
		                         + num + "'", 
		                null, 
		                null);
		 
		         if (cursor == null) {
		             System.out.println("getPeople null");
		             return num;
		         }
		         for (int i = 0; i < cursor.getCount(); i++) {
		             cursor.moveToPosition(i);
		
	             // 取得联系人名字
		             int nameFieldColumnIndex = cursor
		                     .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
		            String name = cursor.getString(nameFieldColumnIndex);
		            cursor.close();
		            cursor = null;
		             return name;
		         }
		         cursor.close();
		         cursor = null;
		         return num;
		    }
}
