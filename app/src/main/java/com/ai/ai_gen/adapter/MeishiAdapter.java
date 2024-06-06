package com.ai.ai_gen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.Item_Detail_type1Activity;
import com.ai.ai_gen.bean.ServiceviewBean;
import com.bumptech.glide.Glide;

public class MeishiAdapter extends RecyclerView.Adapter<MeishiAdapter.MeishiViewHolder>{
    private Context context;
    protected ServiceviewBean mList= new ServiceviewBean();;
    private MyItemClickListener mItemClickListener;
    public MeishiAdapter(Context context , ServiceviewBean meishi ){
        super();
        this.context=context;
        this.mList = meishi;
    }
    @NonNull
    @Override
    public MeishiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meishi_card,parent,false);
        MeishiViewHolder myViewHolder = new MeishiViewHolder(view,mItemClickListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeishiViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ServiceviewBean e = mList;
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view,position);

                    Intent intent = new Intent(context, Item_Detail_type1Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("imageurl", mList.getRows().get(position).getImgUrl());
                    bundle.putString("content",  mList.getRows().get(position).getContent());
                    bundle.putString("desc",  mList.getRows().get(position).getCreateBy());
                    bundle.putString("zannum",  mList.getRows().get(position).getLikeNumber());
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });
        }
        onBindData(holder, e,position);
    }

    protected void onBindData(MeishiViewHolder holder, ServiceviewBean serviceModel, int positon) {
        String url=serviceModel.getRows().get(positon).getImgUrl();
        Glide.with(context).load(url).into(holder.meishi_img);
        holder.meishi_context.setText(serviceModel.getRows().get(positon).getContent());
        holder.meishi_from.setText(serviceModel.getRows().get(positon).getCreateBy());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.getTotal();
    }

    public class MeishiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView meishi_img;
        TextView meishi_context;
        TextView meishi_from;
        private MyItemClickListener myListener;
        public MeishiViewHolder(@NonNull View itemView, MyItemClickListener mItemClickListener) {
            super(itemView);
            meishi_img = itemView.findViewById(R.id.meishi_img);
            meishi_context = itemView.findViewById(R.id.meishi_context);
            meishi_from = itemView.findViewById(R.id.meishi_from);
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
