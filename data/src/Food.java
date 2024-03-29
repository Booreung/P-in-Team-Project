public class Food {
    private int food_no;
    private String food_name;
    private int food_price;
    private byte[] food_image; // 이미지는 byte 배열로 저장하는 것이 일반적
    private boolean food_etc;
    private int food_code;

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

    public byte[] getFood_image() {
        return food_image;
    }

    public void setFood_image(byte[] food_image) {
        this.food_image = food_image;
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
}
