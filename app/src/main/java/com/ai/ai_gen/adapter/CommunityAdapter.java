package com.ai.ai_gen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.CommunityActivity;
import com.ai.ai_gen.activity.Item_Detail_type1Activity;
import com.ai.ai_gen.bean.CommunityBean;
import com.ai.ai_gen.bean.UtilViewBean;
import com.bumptech.glide.Glide;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityHolder>{
    private Context context;
    protected CommunityBean mList= new CommunityBean();;
    private MyItemClickListener mItemClickListener;
    public CommunityAdapter(Context context , CommunityBean utillist ){
        super();
        this.context=context;
        this.mList = utillist;
    }
    @NonNull
    @Override
    public CommunityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.community_item,parent,false);
        CommunityHolder myViewHolder = new CommunityHolder(view,mItemClickListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityHolder holder, @SuppressLint("RecyclerView") int position) {
        final CommunityBean e = mList;
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view,position);
                    Intent intent = new Intent(context, CommunityActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", mList.getRows().get(position).getName());
                    bundle.putString("content",  mList.getRows().get(position).getContent());
                    bundle.putString("img",  mList.getRows().get(position).getImgUrl());
                    bundle.putString("like",  mList.getRows().get(position).getlikenum());
                    bundle.putString("collection",  mList.getRows().get(position).getColection());
                    bundle.putInt("commentnum",  mList.getRows().get(position).getCommentsnum());
                    bundle.putString("commentid",  mList.getRows().get(position).getCommentsid());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        onBindData(holder, e,position);
    }

    protected void onBindData(CommunityHolder holder, CommunityBean serviceModel, int positon) {
        String url=serviceModel.getRows().get(positon).getImgUrl();
        Glide.with(context).load(url).into(holder.com_img);
        holder.com_name.setText(serviceModel.getRows().get(positon).getName());
        holder.com_content.setText(serviceModel.getRows().get(positon).getContent());
        holder.likenum.setText(serviceModel.getRows().get(positon).getlikenum());
        String num= String.valueOf(serviceModel.getRows().get(positon).getCommentsnum());
        holder.comment_num.setText(num);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.getTotal();
    }

    public class CommunityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView com_img;
        TextView com_name;
        TextView com_content;
        Button com_button;
        Button likenum;
        Button comment_num;
        private MyItemClickListener myListener;
        public CommunityHolder(@NonNull View itemView, MyItemClickListener mItemClickListener) {
            super(itemView);
            com_img = itemView.findViewById(R.id.com_img);
            com_name = itemView.findViewById(R.id.com_name);
            com_content = itemView.findViewById(R.id.com_content);
            com_button = itemView.findViewById(R.id.com_button);
            likenum = itemView.findViewById(R.id.likenum);
            comment_num = itemView.findViewById(R.id.comment_num);
            myListener=mItemClickListener;
        }

        @Override
        public void onClick(View view) {
            if (myListener!=null) {
                myListener.onItemClick(view,getPosition());
            }
        }
    }
    public interface MyItemClickListener {
        void onItemClick(View view,int position);
    }
    //在activity中adapter中调用此方法，将点击事件监听传递过去，并赋值给全局监听
    public void setItemClickListener(MyItemClickListener myItemClickListener){
        this.mItemClickListener = myItemClickListener;
    }

}
