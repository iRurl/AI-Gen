package com.ai.ai_gen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.bean.ConversionBean;

public class AiUtilComAdapter extends RecyclerView.Adapter<AiUtilComAdapter.ConViewHolder>{
    private Context context;
    protected ConversionBean mList= new ConversionBean();
    private MyItemClickListener mItemClickListener;
    public AiUtilComAdapter(Context context , ConversionBean conlist ){
        super();
        this.context=context;
        this.mList = conlist;
    }
    @NonNull
    @Override
    public ConViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gpt_conversion,parent,false);
        ConViewHolder myViewHolder = new ConViewHolder(view,mItemClickListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ConversionBean e = mList;
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view,position);
                }
            });
        }
        onBindData(holder, e,position);
    }

    protected void onBindData(ConViewHolder holder, ConversionBean conlist, int positon) {
        if(conlist.getRows().get(positon).gettype()==0){
            holder.cl_user.setVisibility(View.GONE);
            holder.tv_gpt_answer.setText(conlist.getRows().get(positon).getContent());
        }else {
            holder.cl_gpt.setVisibility(View.GONE);
            holder.tv_user_ask.setText(conlist.getRows().get(positon).getContent());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.getTotal();
    }

    public class ConViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_gpt_answer;
        TextView tv_user_ask;

        ConstraintLayout cl_user;
        ConstraintLayout cl_gpt;
        private MyItemClickListener myListener;
        public ConViewHolder(@NonNull View itemView, MyItemClickListener mItemClickListener) {
            super(itemView);
            tv_gpt_answer = itemView.findViewById(R.id.tv_gpt_answer);
            tv_user_ask = itemView.findViewById(R.id.tv_user_ask);
            cl_user = itemView.findViewById(R.id.cl_user);
            cl_gpt = itemView.findViewById(R.id.gptcl);
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
