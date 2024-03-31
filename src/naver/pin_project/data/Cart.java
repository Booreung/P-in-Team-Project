package src.naver.pin_project.data;

import java.util.Date;

public class Cart {
    private int order_no;
    private String order_name;
    private int order_ea;
    private int order_price;
    private Date order_time;

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public int getOrder_ea() {
        return order_ea;
    }

    public void setOrder_ea(int order_ea) {
        this.order_ea = order_ea;
    }

    public int getOrder_price() {
        return order_price;
    }

    public void setOrder_price(int order_price) {
        this.order_price = order_price;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }
}
