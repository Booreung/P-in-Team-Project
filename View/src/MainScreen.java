import javax.swing.*;

public class MainScreen extends JPanel {
    public MainScreen(){
        //좌측 상단 버튼 3개 1열로
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton callbtn = new JButton("직원 호출");
        JButton rankbtn = new JButton("랭킹");
        JButton myrecordbtn = new JButton("내 기록");
    }
}
