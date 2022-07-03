package com.example.dogshoppe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

// Adapter Class is modified from https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
public class WishNCartAdaptor extends
            RecyclerView.Adapter<WishNCartAdaptor.ViewHolder> {

        private static ArrayList<Item> wishCartItems;
        private LayoutInflater mInflator;
        private ItemClickListener mClickListener;
        private Context acontext;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView tvItemname;
            public TextView tvItemPrice;
            public ImageButton ibRemoveBtn;
            public ImageButton ibCartBtn;
            public ImageView ivItemDefaultImg;

            public ViewHolder(View itemView) {

                super(itemView);
                tvItemname = (TextView) itemView.findViewById(R.id.tvWishCartItemName);
                tvItemPrice = (TextView) itemView.findViewById(R.id.tvWishCartPrice);
                ibRemoveBtn = (ImageButton) itemView.findViewById(R.id.ibRemovebtn);
                ibCartBtn = (ImageButton) itemView.findViewById(R.id.ibCartbtn);
                ivItemDefaultImg = (ImageView) itemView.findViewById(R.id.ivWishCartItemImg);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
            }
        }
        WishNCartAdaptor(Context context, ArrayList<Item> data){
            this.acontext = context;
            this.mInflator = LayoutInflater.from(context);
            this.wishCartItems = data;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflator.inflate(R.layout.list_wish_cart_items_layout,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Item currentItem = wishCartItems.get(position);
            String itemName = currentItem.getItemName();
            String itemPrice = currentItem.getPrice();

            int imgRes = acontext.getResources().getIdentifier(currentItem.getFirstItemImagePath(), null, acontext.getPackageName());
            holder.tvItemname.setText(itemName);
            holder.tvItemPrice.setText(itemPrice);
            holder.ivItemDefaultImg.setImageResource(imgRes);

            if(acontext instanceof WishlistActivity){ // Check if the adapter is created from WishList Activity
                // Items in wishlist activity has a cart button to add items to cart.
                holder.ibCartBtn.setVisibility(View.VISIBLE);
                holder.ibCartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(acontext, "Item added to cart", Toast.LENGTH_SHORT).show();
                        wishCartItems.get(holder.getAdapterPosition()).setCartState(true);
                        Cart.addItemToCart(wishCartItems.get(holder.getAdapterPosition()));
                    }
                });
            }else{
                // Items in cart activity does not have a cart button
                holder.ibCartBtn.setVisibility(View.GONE);
            }

            holder.ibRemoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(acontext, "Removed item", Toast.LENGTH_SHORT).show();
                    // check which activity the adapter came from to reset the state of corresponding
                    // buttons
                    if(acontext instanceof WishlistActivity){
                        wishCartItems.get(holder.getAdapterPosition()).setWishState(false);
                    }else{
                        wishCartItems.get(holder.getAdapterPosition()).setCartState(false);
                    }

                    wishCartItems.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });


        }
        Item getItem(int id) {
        return wishCartItems.get(id);
        }

        @Override
        public int getItemCount() {
            return wishCartItems.size();
        }
        void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
        }
        public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

