package com.example.dogshoppe;
/*Imports*/
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    public static final String SEARCH_DETAIL_KEY = "search";
    static ItemAdaptor searchAdaptor;
    ArrayList<Item> allItems;

    class ViewHolder {
        Button buttonFood2;
        Button buttonToys2;
        Button buttonGroom2;
        Button buttonAccessory2;
        GridView gvSearchItems;
        SearchView search_view;


        public ViewHolder() {
            buttonFood2 = findViewById(R.id.foodButton2);
            buttonToys2 = findViewById(R.id.toyButton2);
            buttonGroom2 = findViewById(R.id.groomButton2);
            buttonAccessory2 = findViewById(R.id.accButton2);
            gvSearchItems = findViewById(R.id.gvSearch);
            search_view = findViewById(R.id.search_view);
        }
    }
        ViewHolder vh;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            vh = new ViewHolder();
            allItems = MainActivity.allItems;
            searchAdaptor = new ItemAdaptor(this, R.layout.list_items_layout, new ArrayList<>(allItems));
            vh.gvSearchItems.setAdapter(searchAdaptor);
            vh.gvSearchItems.setVisibility(View.GONE);

            /*Check for user input in the search bar and check if the user presses enter*/
            vh.search_view.clearFocus();
            vh.search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

            // -------- Category buttons On click Listeners ---------- //
            vh.buttonFood2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent foodActivty = new Intent(getBaseContext(), FoodActivity.class);
                    startActivity(foodActivty);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            });
            vh.buttonToys2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent toyActivty = new Intent(getBaseContext(), ToysActivity.class);
                    startActivity(toyActivty);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            });

            vh.buttonAccessory2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent accessorActivty = new Intent(getBaseContext(), AccessorActivity.class);
                    startActivity(accessorActivty);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            });

            vh.buttonGroom2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent groomActivty = new Intent(getBaseContext(), GroomActivity.class);
                    startActivity(groomActivty);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            });

            setupItemSelectedListener();
            setupBottomNavBtnsSelectedListener();
        }

        /*Sets up grid view and updates it based on input in search bar*/
        private void filterList(String search_text) {
            ArrayList<Item> filteredList = new ArrayList<>();
            for (int i = 0; i < allItems.size(); i++) {
                Item item = allItems.get(i);

                if (item.getItemName().toLowerCase().contains(search_text.toLowerCase())) {
                    filteredList.add(item);

                }
            }
            System.out.println("Size of list: "+ filteredList.size());

            /*If there are no matches in the database with the input in the search bar then show user
            message or if the search bar has been cleared show all items again*/
            if (filteredList.isEmpty() ||( filteredList.size()== MainActivity.allItems.size())) {
                if(filteredList.isEmpty()) {
                    Toast.makeText(this, "No search matches", Toast.LENGTH_SHORT).show();
                }
                vh.gvSearchItems.setVisibility(View.GONE);
                vh.buttonAccessory2.setVisibility(View.VISIBLE);
                vh.buttonFood2.setVisibility(View.VISIBLE);
                vh.buttonGroom2.setVisibility(View.VISIBLE);
                vh.buttonToys2.setVisibility(View.VISIBLE);
            }
            /*Shows items that match the users search input*/
            else {
                searchAdaptor.setFilteredList(filteredList);
                vh.gvSearchItems.setVisibility(View.VISIBLE);
                vh.buttonAccessory2.setVisibility(View.GONE);
                vh.buttonFood2.setVisibility(View.GONE);
                vh.buttonGroom2.setVisibility(View.GONE);
                vh.buttonToys2.setVisibility(View.GONE);
            }
        }
        public void setupItemSelectedListener() {
            vh.gvSearchItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // Launch the detail view and passing search item
                    Intent intent = new Intent(SearchActivity.this, DetailedItemActivity.class);
                    intent.putExtra("FromWhere", "search");
                    intent.putExtra(SEARCH_DETAIL_KEY, searchAdaptor.getItem(position));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            });
        }

        /*Actions for bottom navigation buttons*/
        public void setupBottomNavBtnsSelectedListener() {
            // ------- Bottom navigation on click listeners -----
            ImageButton btnHome = findViewById(R.id.homeButton);
            ImageButton btnCart = findViewById(R.id.cart_button);
            ImageButton btnWishlist = findViewById(R.id.wishlist_button);

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainActivity = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(mainActivity);
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




