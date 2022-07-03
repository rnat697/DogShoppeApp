package com.example.dogshoppe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecentImgsRecycleAdaptor extends
        RecyclerView.Adapter<RecentImgsRecycleAdaptor.ViewHolder> {
    static ArrayList<Item> recentlyViewedItems;
    private LayoutInflater mInflator;
    private ItemClickListener mClickListener;
    private Context acontext;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivItemImage;
        ViewHolder(View v){
            super(v);
            ivItemImage = v.findViewById(R.id.ivitemImageRecycle);
            v.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public RecentImgsRecycleAdaptor(Context context, ArrayList<Item> data) {
        this.acontext = context;
        this.mInflator = LayoutInflater.from(context);
        this.recentlyViewedItems = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.recent_img_recycle_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item currentItem = recentlyViewedItems.get(position);
        int imgRes = acontext.getResources().getIdentifier(currentItem.getFirstItemImagePath(), null, acontext.getPackageName());
        holder.ivItemImage.setImageResource(imgRes);
    }

    @Override
    public int getItemCount() {
        return recentlyViewedItems.size();
    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
