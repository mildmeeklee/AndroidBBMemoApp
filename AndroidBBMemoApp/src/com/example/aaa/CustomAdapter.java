package com.example.aaa;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	
	Context ctx;
	ArrayList<BoardDTO> list;
	int layout;
	LayoutInflater inf;

	public CustomAdapter(Context ctx, ArrayList<BoardDTO> list, int layout) {
		super();
		this.ctx = ctx;
		this.list = list;
		this.layout = layout;
		inf = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null) { convertView = inf.inflate(layout, null); }
		
		TextView b_num = (TextView)convertView.findViewById(R.id.b_num);
		TextView b_name = (TextView)convertView.findViewById(R.id.b_name);
		TextView b_content = (TextView)convertView.findViewById(R.id.b_content);
		
	
		
		b_num.setText("b_num : "+list.get(position).getB_num());
		b_name.setText("b_name : "+list.get(position).getB_name());
		b_content.setText("b_content : "+list.get(position).getB_content());
		

		
		return convertView;
	}

}
