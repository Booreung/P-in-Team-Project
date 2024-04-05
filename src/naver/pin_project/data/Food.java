package src.naver.pin_project.data;

public class Food {
    private int food_no;
    private String food_name;
    private int food_price;

    private String food_image;
    private boolean food_etc;
    private int food_code;

    public Food(String foodName, int foodPrice, String food_image,boolean food_etc) {
        this.food_name= foodName;
        this.food_price =foodPrice;
        this.food_image= food_image;
        this.food_etc=food_etc;
    }

    public int getFood_no() {
        return food_no;
    }

    public void setFood_no(int food_no) {
        this.food_no = food_no;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getFood_price() {
        return food_price;
    }

    public void setFood_price(int food_price) {
        this.food_price = food_price;
    }

    public boolean isFood_etc() {
        return food_etc;
    }

    public void setFood_etc(boolean food_etc) {
        this.food_etc = food_etc;
    }

    public int getFood_code() {
        return food_code;
    }

    public void setFood_code(int food_code) {
        this.food_code = food_code;
    }

    public String getFood_image() { return food_image;}

}
