package com.example.dogshoppe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class DetailedItemActivity extends AppCompatActivity {
    class ViewHolder {

        TextView tvProductName;
        ImageSlider ivImageSlider;
        TextView tvPrice;
        TextView tvDescription;
        ImageButton ibdetailedWish;
        ImageButton ibdetailedCart;
        public ViewHolder() {
            tvProductName = findViewById(R.id.tvDetailedItemName);
            ivImageSlider = findViewById(R.id.detailedimageSlider);
            tvPrice = findViewById(R.id.tvDetailedItemPrice);
            tvDescription = findViewById(R.id.tvitemDescriptions);
            ibdetailedCart = findViewById(R.id.detailedCart);
            ibdetailedWish = findViewById(R.id.detailedWish);
        }
    }

    ViewHolder vh;
    Intent thisIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_item);

        vh = new ViewHolder();
        thisIntent = getIntent();
        Item item;
        int pos;
        // Checks which activity intent came from and gets Item instance class of that
        // cateogry
        // Modified from
        // https://stackoverflow.com/questions/11529529/how-to-check-which-intent-started-the-activity
        if (thisIntent != null) {
            String whichCategory = thisIntent.getExtras().getString("FromWhere");
            if (whichCategory.equals("Food")) {
                pos =  thisIntent.getExtras().getInt(FoodActivity.FOOD_DETAIL_KEY);
                item = MainActivity.foodItems.get(pos);
                loadItem(item);
            } else if (whichCategory.equals("Accessory")) {
                pos = thisIntent.getExtras().getInt(AccessorActivity.ACCESOR_DETAIL_KEY);
                item = MainActivity.accessorItems.get(pos);
                loadItem(item);
            } else if (whichCategory.equals("Toy")) {
                pos = thisIntent.getExtras().getInt(ToysActivity.TOY_DETAIL_KEY);
                item = MainActivity.toyItems.get(pos);
                loadItem(item);

            } else if (whichCategory.equals("Groom")) {
                pos = thisIntent.getExtras().getInt(GroomActivity.GROOM_DETAIL_KEY);
                item = MainActivity.groomItems.get(pos);
                loadItem(item);

            } else if (whichCategory.equals("Slider")) {
                pos = thisIntent.getExtras().getInt(MainActivity.MAIN_SLIDER_DETAIL_KEY);
                item = MainActivity.mainSliderItems.get(pos);
                loadItem(item);
            }else if (whichCategory.equals("Wishlist")) {
                item = (Item) thisIntent.getSerializableExtra(WishlistActivity.WISH_DETAIL_KEY);
                loadItem(item);
            }else if (whichCategory.equals("Cart")) {
                item = (Item) thisIntent.getSerializableExtra(CartActivity.CART_DETAIL_KEY);
                loadItem(item);
            }else if(whichCategory.equals("search")){
                item = (Item) thisIntent.getSerializableExtra(SearchActivity.SEARCH_DETAIL_KEY);
                loadItem(item);
            }
        }


        // ------- Bottom navigation on click listeners -----
        ImageButton btnHome = findViewById(R.id.homeButton);
        ImageButton btnCart = findViewById(R.id.cart_button);
        ImageButton btnWishlist = findViewById(R.id.wishlist_button);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeActivity = new Intent(getBaseContext(),MainActivity.class);
                startActivity(homeActivity);
                finish();
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartActivity = new Intent(getBaseContext(),CartActivity.class);
                startActivity(cartActivity);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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

    private void loadItem(Item item) {
        ArrayList<SlideModel> images = new ArrayList<>();
        WishList wishlist = ItemAdaptor.wishlist;
        Cart cart = ItemAdaptor.cart;


        vh.tvProductName.setText(item.getItemName());
        vh.tvPrice.setText(item.getPrice());
        vh.tvDescription.setText(item.getDescription());

        // Getting image resource from drawable address modified from:
        // https://stackoverflow.com/questions/11737607/how-to-set-the-image-from-drawable-dynamically-in-android
        int img1Res = getResources().getIdentifier(item.getFirstItemImagePath(), null, getPackageName());
        int img2Res = getResources().getIdentifier(item.getSecondItemImagePath(), null, getPackageName());
        int img3Res = getResources().getIdentifier(item.getThirdItemImagePath(), null, getPackageName());
        images.add(new SlideModel(img1Res, null));
        images.add(new SlideModel(img2Res, null));
        images.add(new SlideModel(img3Res, null));
        vh.ivImageSlider.setImageList(images, ScaleTypes.CENTER_INSIDE);

        final ColorMatrix grayscaleMatrix = new ColorMatrix();
        grayscaleMatrix.setSaturation(0);

        // sets icons to gray if wish state is false
        if(!item.getWishState()) {
            vh.ibdetailedWish.setColorFilter(new ColorMatrixColorFilter(grayscaleMatrix)); // grayscale tint
        }

        // sets icons to gray if cart state is false
        if(!item.getCartState()) {
            vh.ibdetailedCart.setColorFilter(new ColorMatrixColorFilter(grayscaleMatrix)); // grayscale tint
        }

        // Wishlist and Cart button listeners
        vh.ibdetailedWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ImageButton wishButton = (ImageButton) v;
                if(item.getWishState()) { // if button is orange, change back to default gray (ie: if the wish state is true)

                    Toast.makeText(DetailedItemActivity.this,"Removed from Wishlist", Toast.LENGTH_SHORT).show();

                    wishButton.setColorFilter(new ColorMatrixColorFilter(grayscaleMatrix));

                    wishlist.removeItemOffWishList(item);
                    item.setWishState(false); // reset for next click
                }else{ // if wish state is false clear the grayscale filter so the image is orange

                    Toast.makeText(DetailedItemActivity.this,"Added to Wishlist", Toast.LENGTH_SHORT).show();

                    wishButton.clearColorFilter();
                    wishlist.addItemToWishList(item);
                    item.setWishState(true);

                }

            }
        });
        vh.ibdetailedCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageButton cartBtn = (ImageButton) v;

                if (item.getCartState()) { // if button is orange, change back to default gray
                    Toast.makeText(DetailedItemActivity.this, "Removed from Cart", Toast.LENGTH_SHORT).show();


                    cartBtn.setColorFilter(new ColorMatrixColorFilter(grayscaleMatrix));
                    cart.removeItemOffCart(item);
                    item.setCartState(false); // reset for next click
                } else {

                    Toast.makeText(DetailedItemActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();


                    cartBtn.clearColorFilter();
                    cart.addItemToCart(item);
                    item.setCartState(true);

                }
            }
        });

    }
    @Override
    public void onBackPressed() {

        // Restart the previous activity to visually update wish and cart button states
        String whichCategory = thisIntent.getExtras().getString("FromWhere");
        if (whichCategory.equals("Food")) {
            Intent goBackToPrevActivity = new Intent(DetailedItemActivity.this,FoodActivity.class);
            startActivity(goBackToPrevActivity);

        } else if (whichCategory.equals("Accessory")) {
            Intent goBackToPrevActivity = new Intent(DetailedItemActivity.this,AccessorActivity.class);
            startActivity(goBackToPrevActivity);

       } else if (whichCategory.equals("Toy")) {
            Intent goBackToPrevActivity = new Intent(DetailedItemActivity.this,ToysActivity.class);
            startActivity(goBackToPrevActivity);


        } else if (whichCategory.equals("Groom")) {
            Intent goBackToPrevActivity = new Intent(DetailedItemActivity.this,GroomActivity.class);
            startActivity(goBackToPrevActivity);


        }else if(whichCategory.equals("search")){
            Intent goBackToPrevActivity = new Intent(DetailedItemActivity.this,SearchActivity.class);
            startActivity(goBackToPrevActivity);

        }
        // for search, wishlist and cart activites, there is no need to restart activity to update button state

        finish();
    }

    /*Animation for page sliding in from the left*/
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}