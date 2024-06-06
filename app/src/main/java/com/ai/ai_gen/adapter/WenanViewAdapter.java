package com.ai.ai_gen.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.Item_Detail_type1Activity;
import com.ai.ai_gen.activity.Item_Detail_type3Activity;
import com.ai.ai_gen.bean.WenanListViewBean;

public class WenanViewAdapter extends BaseAdapter {
    private WenanListViewBean wenanListViewBean;
    private Context context;

    public WenanViewAdapter(Context context, WenanListViewBean wenanListViewBean) {
        this.wenanListViewBean = wenanListViewBean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return wenanListViewBean.getRows().size();
    }

    @Override
    public Object getItem(int position) {
        return wenanListViewBean.getRows().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_wenan_view_type1, parent, false);

            holder.new_context = convertView.findViewById(R.id.new_context);
            holder.likeNumber = convertView.findViewById(R.id.like_number);
            holder.copy_btn = convertView.findViewById(R.id.copybtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        WenanListViewBean.RowsBean row = wenanListViewBean.getRows().get(position);
        holder.new_context.setText(row.getContent());
        holder.likeNumber.setText(row.getLikeNumber());

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Item_Detail_type3Activity.class);
            Bundle bundle = new Bundle();
            bundle.putString("content", row.getContent());
            bundle.putString("desc", "");
            bundle.putString("zannum", row.getLikeNumber());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        return convertView;
    }

    static class ViewHolder {
        TextView new_context;
        TextView likeNumber;
        Button copy_btn;
    }
}
