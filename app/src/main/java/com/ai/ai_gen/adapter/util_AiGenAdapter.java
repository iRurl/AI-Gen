package com.ai.ai_gen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.MainActivity;
import com.ai.ai_gen.utils.CustomViewpager;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class util_AiGenAdapter extends RecyclerView.Adapter<util_AiGenAdapter.MyViewHolder>
{
    private Context mContext;
    private List<Integer> mImages;
    private LayoutInflater layoutInflater;
    public util_AiGenAdapter(Context context,List<Integer> mImages){
        this.mContext=context;
        layoutInflater = LayoutInflater.from(context);
        this.mImages=mImages;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = layoutInflater.inflate(R.layout.util_recyclerview_banner,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.image.setImageResource(mImages.get(position));
    }

    @Override
    public int getItemCount()
    {
        return mImages.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        RoundedImageView image;

        public MyViewHolder(View view)
        {
            super(view);
            image =view.findViewById(R.id.util_banner);
        }
    }
}
