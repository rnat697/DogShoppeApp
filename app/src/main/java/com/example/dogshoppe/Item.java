package com.example.dogshoppe;

import java.io.Serializable;

public class Item implements Serializable {
    private String itemName;
    private String price;
    private String description;
    private String firstImagePath;
    private String secondImagePath;
    private String thirdImagePath;
    private Boolean wishState;
    private Boolean cartState;
    public Item(String name, String price, String description, String imagePaths){
        this.itemName =  name;
        this.price = price;
        this.description = description;

        // image paths are formatted in this way so we can get the image resource integer value
        String[] paths = imagePaths.split("/");
        this.firstImagePath = "@drawable/" + paths[0].toLowerCase();
        this.secondImagePath = "@drawable/"+paths[1].toLowerCase();
        this.thirdImagePath = "@drawable/"+paths[2].toLowerCase();

        this.wishState = false; // this is to determine the state of the wishlist button
        this.cartState = false; // this is to determine the state of the cart button
    }

    public String getItemName(){
        return itemName;
    }
    public String getPrice(){
        return price;
    }
    public String getDescription(){
        return description;
    }
    public String getFirstItemImagePath(){
        return firstImagePath;
    }
    public String getSecondItemImagePath(){
        return secondImagePath;
    }
    public String getThirdItemImagePath(){
        return thirdImagePath;
    }
    public Boolean getWishState(){return wishState;}
    public Boolean getCartState(){return cartState;}
    public void setWishState(Boolean state){
        wishState = state;
    }
    public void setCartState(Boolean state){
        cartState = state;
    }
}
