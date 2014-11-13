package com.project.smsinterception.ui.listadapter;

import java.util.List;

import com.project.smsinterception.R;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InterceptionAdapter extends BaseAdapter{

	private List<String> words;
	private Context context;
	private LayoutInflater mInflater;
	
	
	
	public InterceptionAdapter(List<String> smsList, Context context) {
		super();
		this.words = smsList;
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return words.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.interception_listitem, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.setValue(position);
		return convertView;
	}
	
	private class ViewHolder{
		private TextView content;
		private TextView deleteButton;
		
		private int pos;
	
		public ViewHolder(View view){
			content =  (TextView) view.findViewById(R.id.interceptionlist_content);
			deleteButton =  (TextView) view.findViewById(R.id.interceptionlist_delete);
			
			deleteButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					new AlertDialog.Builder(context)
					   .setTitle(R.string.app_name)
					   .setMessage(R.string.delet_interception)
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
		}
		
		public void setValue(int pos){
			this.pos = pos;
			content.setText(words.get(pos));
		}

		protected void deletePos() {
			// TODO Auto-generated method stub
			words.remove(pos);
			notifyDataSetChanged();
		}
	}

	

}
