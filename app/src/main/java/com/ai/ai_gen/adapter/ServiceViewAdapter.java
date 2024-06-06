package com.ai.ai_gen.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.Item_Detail_type1Activity;
import com.ai.ai_gen.bean.ServiceviewBean;
import com.bumptech.glide.Glide;

public class ServiceViewAdapter extends BaseAdapter {
    private ServiceviewBean serviceviewBean;
    private Context context;

    public ServiceViewAdapter(Context context, ServiceviewBean serviceviewBean) {
        this.serviceviewBean = serviceviewBean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return serviceviewBean.getRows().size();
    }

    @Override
    public Object getItem(int position) {
        return serviceviewBean.getRows().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return serviceviewBean.getRows().get(position).getPressStatus() == 1 ? 1 : 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int viewType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            if (viewType == 1) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_service_view_type1, parent, false);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_service_view_type2, parent, false);
            }
            holder.new_context = convertView.findViewById(R.id.new_context);
            holder.new_from = convertView.findViewById(R.id.new_from);
            holder.new_img = convertView.findViewById(R.id.new_img);
            holder.likeNumber = convertView.findViewById(R.id.like_number);
            holder.copy_btn = convertView.findViewById(R.id.copybtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ServiceviewBean.RowsBean row = serviceviewBean.getRows().get(position);
        holder.new_context.setText(row.getContent());
        holder.likeNumber.setText(String.valueOf(row.getLikeNumber()));
        holder.new_from.setText(row.getCreateBy());
        String url = row.getImgUrl();
        if (url != null) {
            Glide.with(context).load(url).into(holder.new_img);
        } else {
            holder.new_img.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Item_Detail_type1Activity.class);
            Bundle bundle = new Bundle();
            bundle.putString("imageurl", row.getImgUrl());
            bundle.putString("content", row.getContent());
            bundle.putString("desc", row.getCreateBy());
            bundle.putString("zannum", row.getLikeNumber());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        return convertView;
    }

    static class ViewHolder {
        TextView new_context;
        TextView new_from;
        ImageView new_img;
        TextView likeNumber;
        Button copy_btn;
    }
}
