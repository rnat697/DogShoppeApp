package com.example.dogshoppe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_out);
        // ------- Bottom navigation on click listeners -----
        ImageButton btnHome = findViewById(R.id.homeButton);
        ImageButton btnSearch = findViewById(R.id.search_b3);
        ImageButton btnWishlist = findViewById(R.id.wishlist_button);
        ImageButton btnCart = findViewById(R.id.cart_button);

        btnCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent mainActivity = new Intent(getBaseContext(),MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });

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

        btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wishActivity = new Intent(getBaseContext(),WishlistActivity.class);
                startActivity(wishActivity);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
