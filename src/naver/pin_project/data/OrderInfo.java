package src.naver.pin_project.data;

import java.util.Date;

public class OrderInfo {
    private Date ordered_time;
    private int ordered_no;
    private String ordered_food_name;
    private int ordered_food_price;
    private int ordered_food_quantity;


    public OrderInfo(Date ordered_time, int ordered_no, String ordered_food_name, int ordered_food_price, int ordered_food_quantitiy) {
        this.ordered_time = ordered_time;
        this.ordered_no = ordered_no;
        this.ordered_food_name = ordered_food_name;
        this.ordered_food_price = ordered_food_price;
        this.ordered_food_quantity = ordered_food_quantitiy;
    }

    @Override
    public String toString() {
        int totalPrice = ordered_food_price * ordered_food_quantity;
        return ordered_no + " / " +
                ordered_time + " / " +
                ordered_food_name + " / " +
                ordered_food_price + " / " +
                ordered_food_quantity + " / " +
                totalPrice + "\n";

    }
}