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

public class CartActivity extends AppCompatActivity implements WishNCartAdaptor.ItemClickListener{

    WishNCartAdaptor adapter;
    public static final String CART_DETAIL_KEY = "cart";

    class ViewHolder{
        RecyclerView rvCart;
        TextView tvEmptyCartText;
        ImageView ivEmptyCartImg;
        TextView tvCartTitle;
        Button btnClearCart;
        Button btnCheckOut;
        public ViewHolder() {
            rvCart = findViewById(R.id.rvCart);
            tvEmptyCartText = findViewById(R.id.emptyCartMessage);
            ivEmptyCartImg= findViewById(R.id.redCart);
            tvCartTitle= findViewById(R.id.tvCartTitle);
            btnClearCart = findViewById(R.id.btnClearCart);
            btnCheckOut = findViewById(R.id.btnCheckOut);
        }
    }
    ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        vh = new ViewHolder();
        Boolean isEmpty = Cart.isCartEmpty();

        if(isEmpty){
            // display cart empty error message
            vh.rvCart.setVisibility(View.GONE);
            vh.ivEmptyCartImg.setVisibility(View.VISIBLE);
            vh.tvEmptyCartText.setVisibility(View.VISIBLE);
            vh.tvCartTitle.setVisibility(View.GONE);
            vh.btnClearCart.setVisibility(View.GONE);
            vh.btnCheckOut.setVisibility(View.GONE);

        }else{
            // Display items in cart
            vh.rvCart.setVisibility(View.VISIBLE);
            vh.ivEmptyCartImg.setVisibility(View.GONE);
            vh.tvEmptyCartText.setVisibility(View.GONE);
            vh.tvCartTitle.setVisibility(View.VISIBLE);
            vh.btnClearCart.setVisibility(View.VISIBLE);
            vh.btnCheckOut.setVisibility(View.VISIBLE);

            // set adapter
            vh.rvCart.setLayoutManager(new LinearLayoutManager(this));
            adapter = new WishNCartAdaptor(this, Cart.getCart());
            vh.rvCart.setAdapter(adapter);
            adapter.setClickListener(this);
            vh.btnClearCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart.clearAllItemsOffCart();
                    adapter.notifyDataSetChanged();
                }
            });

            vh.btnCheckOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent checkoutActivity = new Intent(getBaseContext(),CheckoutActivity.class);
                    startActivity(checkoutActivity);
                    Cart.clearAllItemsOffCart();
                    adapter.notifyDataSetChanged();
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            });

        }
        // ------- Bottom navigation on click listeners -----
        ImageButton btnHome = findViewById(R.id.homeButton);
        ImageButton btnSearch = findViewById(R.id.search_b3);
        ImageButton btnWishlist = findViewById(R.id.wishlist_button);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getBaseContext(),MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });

        btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wishActivity = new Intent(getBaseContext(),WishlistActivity.class);
                startActivity(wishActivity);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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

    }

    // Opens up detailed item activity for the item when user clicks on an item on the screen
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(CartActivity.this,DetailedItemActivity.class);
        intent.putExtra("FromWhere", "Cart");
        intent.putExtra(CART_DETAIL_KEY, adapter.getItem(position));
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}