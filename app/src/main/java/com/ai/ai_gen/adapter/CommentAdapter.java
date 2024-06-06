package com.ai.ai_gen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.bean.CommentBean;
import com.bumptech.glide.Glide;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ComViewHolder> {
    protected CommentBean mList = new CommentBean();
    private Context context;
    private MyItemClickListener mItemClickListener;

    public CommentAdapter(Context context, CommentBean conlist) {
        super();
        this.context = context;
        this.mList = conlist;
    }

    @NonNull
    @Override
    public ComViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        ComViewHolder myViewHolder = new ComViewHolder(view, mItemClickListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final CommentBean e = mList;
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view, position);
                }
            });
        }
        onBindData(holder, e, position);
    }

    protected void onBindData(ComViewHolder holder, CommentBean conlist, int positon) {
        if (conlist.getRows().get(positon).getComment_avatar() != null) {
            Glide.with(context).load(conlist.getRows().get(positon).getComment_avatar()).into(holder.comment_avatar);
        } else {
            holder.comment_avatar.setImageResource(R.mipmap.ic_mine_avatar);
        }
        holder.comment_content.setText(conlist.getRows().get(positon).getComment_content());
        holder.comment_username.setText(conlist.getRows().get(positon).getComment_username());
        holder.comment_from.setText(conlist.getRows().get(positon).getComment_from());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.getTotal();
    }

    //在activity中adapter中调用此方法，将点击事件监听传递过去，并赋值给全局监听
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ComViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView comment_avatar;
        TextView comment_username;
        TextView comment_content;
        TextView comment_from;
        ImageView comment_like;
        private MyItemClickListener myListener;

        public ComViewHolder(@NonNull View itemView, MyItemClickListener mItemClickListener) {
            super(itemView);
            comment_avatar = itemView.findViewById(R.id.comment_avatar);
            comment_username = itemView.findViewById(R.id.comment_username);
            comment_content = itemView.findViewById(R.id.comment_content);
            comment_from = itemView.findViewById(R.id.comment_from);
            comment_like = itemView.findViewById(R.id.comment_like);
            comment_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comment_like.setImageResource(R.mipmap.detail_zan_sel);

                    if (!TextUtils.equals(String.valueOf(comment_like.getTag()), "1")) {
                        comment_like.setImageResource(R.mipmap.detail_zan_sel);
                        comment_like.setTag("1");
                    } else {
                        comment_like.setImageResource(R.mipmap.detail_zan_nor);
                        comment_like.setTag("0");
                    }


                }
            });
            myListener = mItemClickListener;
        }

        @Override
        public void onClick(View view) {
            if (myListener != null) {
                myListener.onItemClick(view, getPosition());
            }
        }
    }

}
