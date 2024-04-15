package src.naver.pin_project.data;

// 'Food' 클래스는 음식 항목의 정보를 나타내는 데이터 객체입니다.
public class Food {
    // 음식 번호를 저장하는 변수
    private int food_no;
    // 음식 이름을 저장하는 변수
    private String food_name;
    // 음식 가격을 저장하는 변수
    private int food_price;
    // 음식 이미지 파일 경로를 저장하는 변수
    private String food_image;
    // 기타 옵션 여부를 저장하는 변수
    private boolean food_etc;
    // 음식 코드를 저장하는 변수
    private int food_code;

    // 음식 이름, 가격, 이미지 파일 경로, 기타 옵션 여부를 매개변수로 받아 새로운 Food 객체를 생성하는 생성자
    public Food(String foodName, int foodPrice, String food_image, boolean food_etc) {
        this.food_name = foodName;
        this.food_price = foodPrice;
        this.food_image = food_image;
        this.food_etc = food_etc;
    }

    // food_no 값을 반환하는 getter 메서드
    public int getFood_no() {
        return food_no;
    }

    // food_no 값을 설정하는 setter 메서드
    public void setFood_no(int food_no) {
        this.food_no = food_no;
    }

    // food_name 값을 반환하는 getter 메서드
    public String getFood_name() {
        return food_name;
    }

    // food_name 값을 설정하는 setter 메서드
    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    // food_price 값을 반환하는 getter 메서드
    public int getFood_price() {
        return food_price;
    }

    // food_price 값을 설정하는 setter 메서드
    public void setFood_price(int food_price) {
        this.food_price = food_price;
    }

    // food_etc 값을 반환하는 getter 메서드
    public boolean isFood_etc() {
        return food_etc;
    }

    // food_etc 값을 설정하는 setter 메서드
    public void setFood_etc(boolean food_etc) {
        this.food_etc = food_etc;
    }

    // food_code 값을 반환하는 getter 메서드
    public int getFood_code() {
        return food_code;
    }

    // food_code 값을 설정하는 setter 메서드
    public void setFood_code(int food_code) {
        this.food_code = food_code;
    }

    // food_image 값을 반환하는 getter 메서드
    public String getFood_image() {
        return food_image;
    }
}