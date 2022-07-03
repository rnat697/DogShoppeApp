package com.example.dogshoppe;

/*Import*/
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.denzcoskun.imageslider.models.SlideModel;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements RecentImgsRecycleAdaptor.ItemClickListener{
    RecentImgsRecycleAdaptor adapter;
    public static final String MAIN_SLIDER_DETAIL_KEY = "main";
    public static ArrayList<Item> foodItems;
    public static ArrayList<Item> toyItems;
    public static ArrayList<Item> accessorItems;
    public static ArrayList<Item> groomItems;
    public static ArrayList<Item> allItems;
    public static ArrayList<Item> mainSliderItems;

    class ViewHolder {
        Button buttonFood;
        Button buttonToys;
        Button buttonGroom;
        Button buttonAccessory;
        RecyclerView rvRecentlyViewed;

        ArrayList<SlideModel> images = new ArrayList<>();

        public ViewHolder() {
            buttonFood = findViewById(R.id.foodButton);
            buttonToys = findViewById(R.id.toyButton);
            buttonGroom = findViewById(R.id.groomButton);
            buttonAccessory = findViewById(R.id.accButton);
            rvRecentlyViewed = findViewById(R.id.rvRecentItemsImgs);
        }
    }

    ViewHolder vh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadCategoryItemsToArrayLists();

        // create first instances of wishlist and cart //
        WishList wishlist = new WishList();
        Cart cart = new Cart();

        vh = new ViewHolder();

        // Add default items to be shown on the image slider //
        mainSliderItems = new ArrayList<Item>();
        mainSliderItems.add(toyItems.get(0));
        mainSliderItems.add(accessorItems.get(0));
        mainSliderItems.add(foodItems.get(0));
        mainSliderItems.add(groomItems.get(0));


        vh.rvRecentlyViewed.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        adapter = new RecentImgsRecycleAdaptor(this, mainSliderItems);
        vh.rvRecentlyViewed.setAdapter(adapter);
        adapter.setClickListener(this);


        // -------- Category buttons On click Listeners ----------
        vh.buttonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent foodActivty = new Intent(getBaseContext(), FoodActivity.class);
                startActivity(foodActivty);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        vh.buttonToys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toyActivty = new Intent(getBaseContext(), ToysActivity.class);
                startActivity(toyActivty);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        vh.buttonAccessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accessorActivty = new Intent(getBaseContext(), AccessorActivity.class);
                startActivity(accessorActivty);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        vh.buttonGroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent groomActivty = new Intent(getBaseContext(), GroomActivity.class);
                startActivity(groomActivty);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        // ------- Bottom navigation on click listeners -----
        ImageButton btnWishlist = findViewById(R.id.wishlist_button);
        ImageButton btnSearch = findViewById(R.id.search_b3);
        ImageButton btnCart = findViewById(R.id.cart_button);

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
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        // --------- Update image slider with recently viewed items on activity close -------
        // (ie: when FoodActivity closes, it updates the items on the image slider to whatever item the user has clicked on)
        // get images
       adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(MainActivity.this,DetailedItemActivity.class);
        intent.putExtra("FromWhere", "Slider");
        intent.putExtra(MAIN_SLIDER_DETAIL_KEY, position);
        startActivity(intent);
        //*Animation for the page sliding in from the right*//
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void loadCategoryItemsToArrayLists(){
        // ----- Load all data from csvs to arraylists for each category ----
        // FOOD
        InputStream inStreamFood = getResources().openRawResource(R.raw.food);// modified from https://stackoverflow.com/questions/19974708/reading-csv-file-in-resources-folder-android/19976110#19976110
        Categories foods = new Categories(inStreamFood);
        foodItems = new ArrayList<Item>();
        foodItems = foods.generateItemData("Foods");
        // TOYS
        InputStream inStreamToys = getResources().openRawResource(R.raw.toys);
        Categories toys = new Categories(inStreamToys);
        toyItems = new ArrayList<Item>();
        toyItems = toys.generateItemData("Toys");
        // ACCESSORIES
        InputStream inStreamAcces = getResources().openRawResource(R.raw.accessories);
        Categories accessories = new Categories(inStreamAcces);
        accessorItems = new ArrayList<Item>();
        accessorItems = accessories.generateItemData("Accessories");
        // GROOM
        InputStream inStreamGroom = getResources().openRawResource(R.raw.grooming);
        Categories grooming = new Categories(inStreamGroom);
        groomItems = new ArrayList<Item>();
        groomItems = grooming.generateItemData("Grooming");

        // ALL ITEMS FROM ALL CATEGORIES
        allItems = new ArrayList<Item>();
        allItems.addAll(toyItems);
        allItems.addAll(foodItems);
        allItems.addAll(accessorItems);
        allItems.addAll(groomItems);
    }

    /*Animation for page sliding in from the left*/
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}