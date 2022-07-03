package com.example.dogshoppe;

/*Imports*/
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class ItemAdaptor extends BaseAdapter {

    public class ViewHolder{
        public ImageView ivItemImage;
        public TextView tvItemName;
        public TextView tvItemPrice;
        public ImageButton ibwishlist;
        public ImageButton ibcart;

        public ViewHolder(View currentView) {
            ivItemImage = currentView.findViewById(R.id.ivItemImg);
            tvItemName = currentView.findViewById(R.id.tvItemName);
            tvItemPrice = currentView.findViewById(R.id.tvPrice);
            ibwishlist = currentView.findViewById(R.id.ibwishIconTiny);
            ibcart = currentView.findViewById(R.id.ibcartIconTiny);
        }
    }

    int layoutID;
    Context acontext;
    ArrayList<Item> items;
    static WishList wishlist = new WishList();
    static Cart cart = new Cart();
    ViewHolder vh;


    public ItemAdaptor(@NonNull Context context, int resource, ArrayList<Item> anItem) {
        layoutID = resource;
        acontext = context;
        items = anItem;
    }

    public void setFilteredList(ArrayList<Item> filteredList){
        this.items.clear();
        this.items.addAll(filteredList);
        System.out.println(this.items);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        try {
            return this.items.size();
        } catch(NullPointerException ex) {
            return 0;
        }
    }

    @Override
    public Item getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Item currentItem = items.get(position); // get the data item for this position
        View currentGridView = convertView;

        if (currentGridView == null) { // On start up of activity, create the layout and inflate the view
            currentGridView = LayoutInflater.from(acontext).inflate(layoutID, parent, false);
        }


        vh = new ViewHolder(currentGridView);


        // Set image button for wishlist and cart to gray modified from https://stackoverflow.com/questions/38186885/is-there-a-way-i-can-gray-out-an-imagebutton-in-android-without-maintaining-a-se
        // default colour of the image should be gray, turns orange when clicked once. Clicking the button a second time, turns it gray
        final ColorMatrix grayscaleMatrix = new ColorMatrix();
        grayscaleMatrix.setSaturation(0);

        // Checks if the wish state is false, the colour of image should be gray,
        if(!currentItem.getWishState()) {

            vh.ibwishlist.setColorFilter(new ColorMatrixColorFilter(grayscaleMatrix)); // grayscale tint
        }else{
            vh.ibwishlist.clearColorFilter(); // orange
        }
        // Checks if the cart state is false, the colour of image should be gray
        if(!currentItem.getCartState()) {
            vh.ibcart.setColorFilter(new ColorMatrixColorFilter(grayscaleMatrix)); // grayscale tint
        }else{
            vh.ibcart.clearColorFilter(); // orange
        }


        // Wishlist and Cart button listeners
        vh.ibwishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageButton wishButton = (ImageButton) v;

                if(currentItem.getWishState()) { // if button is orange, change back to default gray (ie: if the state of wish button is true)

                    Toast.makeText(acontext,"Removed from Wishlist", Toast.LENGTH_SHORT).show();

                    wishButton.setColorFilter(new ColorMatrixColorFilter(grayscaleMatrix));

                    wishlist.removeItemOffWishList(currentItem);


                    currentItem.setWishState(false); // reset for next click
                }else{ // if state of wish button is false (gray), change to orange

                    Toast.makeText(acontext,"Added to Wishlist", Toast.LENGTH_SHORT).show();

                    wishButton.clearColorFilter();
                    wishlist.addItemToWishList(currentItem);
                    currentItem.setWishState(true);
                }
            }
        });
        vh.ibcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageButton cartBtn = (ImageButton) v;

                if (currentItem.getCartState()) { // if button is orange, change back to default gray (ie: if the state of cart button is true)
                    Toast.makeText(acontext, "Removed from Cart", Toast.LENGTH_SHORT).show();

                    cartBtn.setColorFilter(new ColorMatrixColorFilter(grayscaleMatrix));
                    cart.removeItemOffCart(currentItem);
                    currentItem.setCartState(false); // reset for next click
                } else {// if state of cart button is false (gray), change to orange

                    Toast.makeText(acontext, "Added to Cart", Toast.LENGTH_SHORT).show();

                    cartBtn.clearColorFilter();
                    cart.addItemToCart(currentItem);
                    currentItem.setCartState(true);
                }
            }
        });

        return populateItemsGridView(currentItem, currentGridView);

    }

    private View populateItemsGridView(Item currentItem, View currentView){

        // Set the item layout
        vh.tvItemName.setText(currentItem.getItemName()); // sets item name text view
        vh.tvItemPrice.setText(currentItem.getPrice()); // sets price text view

        // Getting image resource from drawable address modified from: https://stackoverflow.com/questions/11737607/how-to-set-the-image-from-drawable-dynamically-in-android
        int imgResource = acontext.getResources().getIdentifier(currentItem.getFirstItemImagePath(), null, acontext.getPackageName());
        vh.ivItemImage.setImageResource(imgResource); // sets item image view

        // Set image buttons for car and wishlist to not be focusable so that
        // when the gridview of the item is clicked anywhere except for on the cart or wish buttons,
        // it will open up the detailed item information
        vh.ibcart.setFocusable(false);
        vh.ibwishlist.setFocusable(false);
        return currentView;
    }


}
