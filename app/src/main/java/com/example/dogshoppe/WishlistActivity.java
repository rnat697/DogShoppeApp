package com.example.dogshoppe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WishlistActivity extends AppCompatActivity implements WishNCartAdaptor.ItemClickListener {

    WishNCartAdaptor adapter;
    public static final String WISH_DETAIL_KEY = "wish";


    class ViewHolder{
        RecyclerView rvWishlist;
        TextView tvEmptyWishText;
        ImageView ivEmptyWishImg;
        TextView tvWishTitle;
        Button btnClearWishList;
        public ViewHolder() {
            rvWishlist = findViewById(R.id.rvWish);
            tvEmptyWishText = findViewById(R.id.emptyWishListText);
            ivEmptyWishImg= findViewById(R.id.redPaw);
            tvWishTitle= findViewById(R.id.tvWishTitle);
            btnClearWishList = findViewById(R.id.btnClearWish);
        }
    }
    ViewHolder vh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        vh = new ViewHolder();
        Boolean isEmpty = WishList.isWishListEmpty();

        if(isEmpty){
            // display cart empty error message
            vh.rvWishlist.setVisibility(View.GONE);
            vh.ivEmptyWishImg.setVisibility(View.VISIBLE);
            vh.tvEmptyWishText.setVisibility(View.VISIBLE);
            vh.tvWishTitle.setVisibility(View.GONE);
            vh.btnClearWishList.setVisibility(View.GONE);

        }else{
            // Display items in wishlist
            vh.rvWishlist.setVisibility(View.VISIBLE);
            vh.ivEmptyWishImg.setVisibility(View.GONE);
            vh.tvEmptyWishText.setVisibility(View.GONE);
            vh.tvWishTitle.setVisibility(View.VISIBLE);
            vh.btnClearWishList.setVisibility(View.VISIBLE);

            vh.rvWishlist.setLayoutManager(new LinearLayoutManager(this));
            adapter = new WishNCartAdaptor(this, WishList.getWishlist());
            vh.rvWishlist.setAdapter(adapter);
            adapter.setClickListener(this);

            vh.btnClearWishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WishList.clearAllItemsOffWishList();
                    adapter.notifyDataSetChanged();
                }
            });
        }

        // ------- Bottom navigation on click listeners -----
        ImageButton btnHome = findViewById(R.id.homeButton);
        ImageButton btnSearch = findViewById(R.id.search_b3);
        ImageButton btnCart = findViewById(R.id.cart_button);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getBaseContext(),MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivity = new Intent(getBaseContext(),SearchActivity.class);
                startActivity(searchActivity);
                finish();
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartActivity = new Intent(getBaseContext(),CartActivity.class);
                startActivity(cartActivity);
                finish();
            }
        });

    }

    // Opens up detailed activity of the item when user clicks on an item
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(WishlistActivity.this,DetailedItemActivity.class);
        intent.putExtra("FromWhere", "Wishlist");
        intent.putExtra(WISH_DETAIL_KEY, adapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}