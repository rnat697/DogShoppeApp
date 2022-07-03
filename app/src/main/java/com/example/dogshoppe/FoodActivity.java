package com.example.dogshoppe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {
    public static final String FOOD_DETAIL_KEY = "food";
    GridView gvFood;
    static ItemAdaptor foodAdaptor;
    ArrayList<Item> foods;
    private SearchView search_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        gvFood = (GridView) findViewById(R.id.gvFood);
        foods = MainActivity.foodItems;
        foodAdaptor = new ItemAdaptor(this, R.layout.list_items_layout, new ArrayList<>(foods));
        gvFood.setAdapter(foodAdaptor);
        gvFood.setVisibility(View.VISIBLE);

        /*Check for user input in the search bar and check if the user presses enter*/
        search_view = findViewById(R.id.search_view);
        search_view.clearFocus();
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        setupFoodSelectedListener();
        setupBottomNavBtnsSelectedListener();
    }

    /*Sets up grid view and updates it based on input in search bar*/
    private void filterList(String search_text) {
        ArrayList<Item> filteredList = new ArrayList<>();
        for (Item item : foods) {
            if (item.getItemName().toLowerCase().contains(search_text.toLowerCase())) {
                filteredList.add(item);
            }
        }
    /*If there are no matches in the database with the input in the search bar then show user
        pre-set message or if the search bar has been cleared show all items again*/
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No search matches", Toast.LENGTH_SHORT).show();
        }else {
            foodAdaptor.setFilteredList(filteredList);
        }
    }

    public void setupFoodSelectedListener() {
        gvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // update the items to be shown on image slider in main activity to recently viewed
                ArrayList<Item> foodOnSlider = MainActivity.mainSliderItems;
                foodOnSlider.set(2, foodAdaptor.getItem(position));

                // Launch the detail view and passing food item
                Intent intent = new Intent(FoodActivity.this, DetailedItemActivity.class);
                intent.putExtra("FromWhere", "Food");
                intent.putExtra(FOOD_DETAIL_KEY, position);

                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    /*Actions for bottom navigation buttons*/
    private void setupBottomNavBtnsSelectedListener() {
        // ------- Bottom navigation on click listeners -----
        ImageButton btnHome = findViewById(R.id.homeButton);
        ImageButton btnCart = findViewById(R.id.cart_button);
        ImageButton btnWishlist = findViewById(R.id.wishlist_button);
        ImageButton btnSearch = findViewById(R.id.search_b3);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartActivity = new Intent(getBaseContext(), CartActivity.class);
                startActivity(cartActivity);
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

        btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wishActivity = new Intent(getBaseContext(), WishlistActivity.class);
                startActivity(wishActivity);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    /*Animation for page sliding in from the left*/
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}