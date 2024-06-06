package com.ai.ai_gen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class GgTitleAdapter extends RecyclerView.Adapter<GgTitleAdapter.ViewHolder> {
    private List<String> imageUrls;
    private Context context;
    private int margin; // margin in pixels

    public GgTitleAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        // Convert 5dp to pixels
        this.margin = (int) (5 * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gg_horizontal_title, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(imageUrls.get(position % imageUrls.size()))
                .timeout(1500)
                .into(holder.imageView);

        // Set margin
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.imageView.getLayoutParams();
        params.setMargins(margin, margin, margin, margin);
        holder.imageView.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gg_image);
        }
    }
}
