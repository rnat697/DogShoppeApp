package com.example.dogshoppe;

import java.util.ArrayList;

public class WishList {
    static ArrayList<Item> wishlist;
    public WishList(){
        this.wishlist = new ArrayList<Item>();
    }
    public static void addItemToWishList(Item item){

        if(!wishlist.contains(item)) { // to prevent multiple instances of the same item adding to the array
            wishlist.add(item);
            System.out.println("In wish: "+wishlist.size());
        }
    }
    public static void removeItemOffWishList(Item item){
        wishlist.remove(item);
        item.setWishState(false);// reset the wishlist button state to false for the specific item
    }
    public static ArrayList<Item> getWishlist(){
        return wishlist;
    }
    public static void clearAllItemsOffWishList(){
        for(int i = 0; i<wishlist.size(); i++){
            wishlist.get(i).setWishState(false);// All items in the wishlist will reset their wishlist button state
        }
        wishlist.clear();
    }
    public static boolean isWishListEmpty(){
       return wishlist.isEmpty();
    }

}
