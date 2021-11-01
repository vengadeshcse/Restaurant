package com.test.restaurant;

public class FoodDetails {
    String food_title;
    String food_desc;
    int price;
    int count;

    public FoodDetails(String food_title, String food_desc, int price, int count) {
        this.food_title = food_title;
        this.food_desc = food_desc;
        this.price = price;
        this.count = count;
    }

    public String getFood_title() {
        return food_title;
    }

    public void setFood_title(String food_title) {
        this.food_title = food_title;
    }

    public String getFood_desc() {
        return food_desc;
    }

    public void setFood_desc(String food_desc) {
        this.food_desc = food_desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "FoodDetails{" +
                "food_title='" + food_title + '\'' +
                ", food_desc='" + food_desc + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
