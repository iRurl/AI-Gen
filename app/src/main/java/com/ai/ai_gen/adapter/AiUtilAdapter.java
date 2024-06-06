package com.ai.ai_gen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.AiUtil_comActivity;
import com.ai.ai_gen.bean.UtilViewBean;
import com.bumptech.glide.Glide;

public class AiUtilAdapter extends RecyclerView.Adapter<AiUtilAdapter.UtilViewHolder>{
    private Context context;
    protected UtilViewBean mList;
    private MyItemClickListener mItemClickListener;
    public AiUtilAdapter(Context context , UtilViewBean utillist ){
        super();
        this.context=context;
        this.mList = utillist;
    }
    @NonNull
    @Override
    public UtilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.util_item,parent,false);
        UtilViewHolder myViewHolder = new UtilViewHolder(view,mItemClickListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UtilViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final UtilViewBean e = mList;
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view,position);
                    Intent intent = new Intent(context, AiUtil_comActivity.class);
                    intent.putExtra("utilViewBean", mList);
                    intent.putExtra("position", position);
                    context.startActivity(intent);

                }
            });
        }
        onBindData(holder, e,position);
    }

    protected void onBindData(UtilViewHolder holder, UtilViewBean serviceModel, int positon) {
        String url=serviceModel.getRows().get(positon).getImgUrl();
        Glide.with(context).load(url).into(holder.util_img);
        holder.util_name.setText(serviceModel.getRows().get(positon).getName());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.getTotal();
    }

    public class UtilViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView util_img;
        TextView util_name;
        Button util_button;
        private MyItemClickListener myListener;
        public UtilViewHolder(@NonNull View itemView, MyItemClickListener mItemClickListener) {
            super(itemView);
            util_img = itemView.findViewById(R.id.util_img);
            util_name = itemView.findViewById(R.id.util_name);
            util_button = itemView.findViewById(R.id.util_button);
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
