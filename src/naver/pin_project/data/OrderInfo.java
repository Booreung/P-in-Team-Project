package src.naver.pin_project.data;

import java.util.Date;

public class OrderInfo {
    private Date ordered_time;
    private int ordered_no;
    private String ordered_food_name;
    private int ordered_food_price;
    private int ordered_food_quantity;

    //주문내역에 필요한 정보 주문시간, 주문번호, 주문한음식이름, 주문한음식가격, 주문한음식수량
    public OrderInfo(Date ordered_time, int ordered_no, String ordered_food_name, int ordered_food_price, int ordered_food_quantity) {
        this.ordered_time = ordered_time;
        this.ordered_no = ordered_no;
        this.ordered_food_name = ordered_food_name;
        this.ordered_food_price = ordered_food_price;
        this.ordered_food_quantity = ordered_food_quantity;
    }

    // Getter 메서드 추가
    public Date getOrdered_time() {
        return ordered_time;
    }

    public int getOrdered_no() {
        return ordered_no;
    }

    public String getOrdered_food_name() {
        return ordered_food_name;
    }

    public int getOrdered_food_price() {
        return ordered_food_price;
    }

    public int getOrdered_food_quantity() {
        return ordered_food_quantity;
    }

    // 총 가격 계산 메서드 추가
    public int getTotalPrice() {
        return ordered_food_price * ordered_food_quantity;
    }
}
