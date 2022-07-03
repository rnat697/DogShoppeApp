package com.example.dogshoppe;

import java.util.ArrayList;

public class Cart {
    static ArrayList<Item> cart;
    public Cart(){
        cart = new ArrayList<Item>();
    }
    public static void addItemToCart(Item item){

        if (!cart.contains(item)) { // to prevent multiple instances of the same item adding to the array
            cart.add(item);
        }
    }
    public static void removeItemOffCart(Item item){
        item.setCartState(false); // reset the cart button state to false for the specific item
        cart.remove(item);
    }
    public static ArrayList<Item> getCart(){
        return cart;
    }
    public static void clearAllItemsOffCart(){
        for(int i = 0; i<cart.size(); i++){
            cart.get(i).setCartState(false); // All items in the cart will reset their cart button state
        }
        cart.clear();
    }
    public static boolean isCartEmpty(){
        return cart.isEmpty();
    }
}
