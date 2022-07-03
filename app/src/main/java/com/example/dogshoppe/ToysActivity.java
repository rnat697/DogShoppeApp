package com.example.dogshoppe;

/*Imports*/
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.ArrayList;

public class ToysActivity extends AppCompatActivity {
    public static final String TOY_DETAIL_KEY = "toy";
    GridView gvToys;
    ItemAdaptor toyAdaptor;
    ArrayList<Item> toys;
    private SearchView search_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toys);

        gvToys = (GridView) findViewById(R.id.gvToys);
        toys = MainActivity.toyItems;
        toyAdaptor = new ItemAdaptor(this,R.layout.list_items_layout,new ArrayList<>(toys));
        gvToys.setAdapter(toyAdaptor);
        gvToys.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        /*Setting up search bar and checks for change in user input*/
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

        setupToySelectedListener();

        /*Set up bottom nav buttons to open new pages when they are pressed*/
        ImageButton btnHome = findViewById(R.id.homeButton);
        ImageButton btnCart = findViewById(R.id.cart_button);
        ImageButton btnWishlist = findViewById(R.id.wishlist_button);
        ImageButton btnSearch = findViewById(R.id.searching_button);

        btnHome.setOnClickListener(v -> {
            finish();
        });

        btnSearch.setOnClickListener(v -> {
            Intent searchActivity = new Intent(getBaseContext(),SearchActivity.class);
            startActivity(searchActivity);
            /*Slide animation when new activity opens*/
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        });

        btnCart.setOnClickListener(v -> {
            Intent cartActivity = new Intent(getBaseContext(),CartActivity.class);
            startActivity(cartActivity);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        });

        btnWishlist.setOnClickListener(v -> {
            Intent wishActivity = new Intent(getBaseContext(),WishlistActivity.class);
            startActivity(wishActivity);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        });
    }

    /*Sets up grid view and updates it based on input in search bar*/
    private void filterList(String search_text) {
        ArrayList<Item> filteredList = new ArrayList<>();
        for (Item item : toys) {
            if (item.getItemName().toLowerCase().contains(search_text.toLowerCase())) {
                filteredList.add(item);
            }
        }
    /*If there are no matches in the database with the input in the search bar then show user
    message or if the search bar has been cleared show all items again*/
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No search matches", Toast.LENGTH_SHORT).show();
        }else {
            toyAdaptor.setFilteredList(filteredList);
        }
    }

    public void setupToySelectedListener(){

        gvToys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // update the items to be shown on image slider in main activity to recently viewed
                ArrayList<Item> toyOnSlider = MainActivity.mainSliderItems;
                toyOnSlider.set(0,toyAdaptor.getItem(position));

                // Launch the detail view and passing toy item
                Intent intent = new Intent(ToysActivity.this,DetailedItemActivity.class);
                intent.putExtra("FromWhere", "Toy");
                intent.putExtra(TOY_DETAIL_KEY, position);
                startActivity(intent);
                finish();
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