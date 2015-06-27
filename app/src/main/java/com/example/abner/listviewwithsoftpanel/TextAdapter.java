package com.example.abner.listviewwithsoftpanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abner on 15/6/27.
 * QQ 230877476
 * Email nimengbo@gmail.com
 */
public class TextAdapter extends BaseAdapter {
    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<String> mItems = new ArrayList<>();

    public TextAdapter(Context mContext) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
    }

    class ViewHolder{

        TextView mTextView;
    }

    public void setmItems(List<String> mItems) {
        this.mItems = mItems;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_text,null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(getItem(position));
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }
}
