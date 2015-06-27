package com.example.abner.listviewwithsoftpanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Abner on 15/6/27.
 * QQ 230877476
 * Email nimengbo@gmail.com
 */
public class SimpleAdapter extends BaseAdapter implements AbsListView.OnItemClickListener{
    private final String TAG = "SimpleAdapter";
    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<SimpleModel> mItems;

    public SimpleAdapter(List<SimpleModel> mItems, Context mContext) {
        this.mItems = mItems;
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
    }


    class ViewHolder{

        TextView mTextView;

        ListViewForScroll  mListView;

        TextAdapter mAdapter;

        SimpleModel model;
    }


    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public SimpleModel getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_list,null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_text);
            holder.mListView = (ListViewForScroll) convertView.findViewById(R.id.list_for_scroll);
            holder.mAdapter = new TextAdapter(mContext);
            holder.mListView.setAdapter(holder.mAdapter);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.model = getItem(position);
        holder.mTextView.setText(getItem(position).getText());
        holder.mAdapter.setmItems(getItem(position).getComments());
        holder.mAdapter.notifyDataSetChanged();
        holder.mListView.setOnItemClickListener(this);
        holder.mListView.setTag(holder);
        return convertView;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ViewHolder holder = (ViewHolder)parent.getTag();
        if(holder == null){
            return ;
        }
        if(commentListener != null){
            commentListener.replyComment(view,holder.model);
        }
    }

    public interface CommentListener {
        void replyComment(View view,SimpleModel model);
    }

    private CommentListener commentListener;

    public void setCommentListener(CommentListener commentListener) {
        this.commentListener = commentListener;
    }
}
