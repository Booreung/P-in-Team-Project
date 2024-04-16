package src.naver.pin_project.view;

import src.naver.pin_project.data.CustomFont;
import src.naver.pin_project.data.User;
import src.naver.pin_project.db.DBHelper;
import src.naver.pin_project.viewmodel.MyPage_ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPageScreen extends JDialog {    // 필드 정의하기
    private JTextField inputId;
    private JTextField inputName;
    private JTextField inputPw;


    public MyPageScreen(CardLayout cardLayout, User loggedInUser, JPanel cardPanel) {

        // 폰트 파일 경로
        String fontPath = "src/naver/pin_project/lib/온글잎밑미.ttf";
        // 원하는 폰트 크기로 폰트 로드
        Font customFont = CustomFont.loadFont(fontPath, 20f);
        // UI에 폰트 적용
        CustomFont.setUIFont(customFont);

        setSize(500, 350);

        setLocationRelativeTo(null); // 화면 가운데에 위치하도록 설정
        setBackground(Color.decode("#8A8585")); //배경색 설정


        //화면에 진입하면, 최신 유저정보 받아오기.
        User newestUser = DBHelper.getUserInfoFromDB(loggedInUser.getUserId());

        //마이페이지 타이틀 레이블 생성 및 색 설정
        JLabel title = new JLabel("마이페이지", JLabel.CENTER);
        title.setOpaque(true);
        title.setBackground(Color.decode("#8A8585"));
        title.setForeground(Color.decode("#FFFFFF"));

        //입력 필드 생성 및 초기값 최신유저 정보로 설정
        inputId = new JTextField(10);
        inputId.setText(newestUser.getUserId());
        inputName = new JTextField(10);
        inputName.setText(newestUser.getUserName());
        inputPw = new JTextField(10);
        inputPw.setText(newestUser.getPassword());

        // 이미지 출력 부분
        ImageIcon imageIcon = new ImageIcon("src/naver/pin_project/lib/mymelody.png");
        Image image = imageIcon.getImage(); // Image 객체로 변환
        Image newImage = image.getScaledInstance(70, 50, Image.SCALE_SMOOTH); // 크기 조정
        ImageIcon newImageIcon = new ImageIcon(newImage); // 조정된 이미지로 다시 ImageIcon 생성
        JLabel imageLabel = new JLabel(newImageIcon); // 조정된 이미지를 담은 JLabel 생성
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.decode("#8A8585"));
        imagePanel.add(imageLabel);

        // "아이디: "라는 입력 패널 생성
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel idLabel = new JLabel("아이디 :");
        idLabel.setForeground(Color.decode("#FFFFFF"));
        idPanel.add(idLabel); // 패널에 UI를 추가
        idPanel.add(inputId);
        idPanel.setBackground(Color.decode("#8A8585"));

        // "이름: "라는 입력 패널 생성
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel nameLabel = new JLabel(("이름 :"));
        nameLabel.setForeground(Color.decode("#FFFFFF"));
        namePanel.add(nameLabel);
        namePanel.add(inputName);
        namePanel.setBackground(Color.decode("#8A8585"));

        // "비밀번호: "라는 입력 패널 생성
        JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel pwLabel = new JLabel("비밀번호 :");
        pwLabel.setForeground(Color.decode("#FFFFFF"));
        pwPanel.add(pwLabel);
        pwPanel.add(inputPw);
        pwPanel.setBackground(Color.decode("#8A8585"));

        //압력 폼 패널 생성
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 1));
        formPanel.add(imagePanel);
        formPanel.add(idPanel);
        formPanel.add(namePanel);
        formPanel.add(pwPanel);
        formPanel.setBackground(Color.decode("#8A8585"));

        //컨텐츠 패널 생성
        JPanel contentPanel = new JPanel(new FlowLayout());
        contentPanel.setBackground(Color.decode("#8A8585"));
        contentPanel.add(formPanel); //컨텐츠패널에 폼패널(입력폼+이미지폼) 추가

        // 수정버튼 생성
        JButton update = new JButton("수정");
        update.setBackground(Color.decode("#FCEB83"));

        // 삭제버튼 생성
        JButton delete = new JButton("삭제");
        delete.setBackground(Color.decode("#8DFFF3"));

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#8A8585"));// FlowLayout 사용하여 버튼들이 화면 가운데에 위치하도록 설정

        // 버튼 패널에 버튼 추가
        buttonPanel.add(update);
        buttonPanel.add(delete);

        //위치 지정
        add(title, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true); //프레임을 보이도록 설정

        MyPage_ViewModel myPageFuc = new MyPage_ViewModel(); //MyPage_ViewModel 객체 생성

        String msgId = inputId.getText(); //입력된 아이디 값 가져오기

        //업데이트 버튼 클릭했을때
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newmsgId = inputId.getText();
                String msgName = inputName.getText();
                String msgPw = inputPw.getText();

                boolean updated =  myPageFuc.update(msgId, msgName,msgPw, newmsgId); //update함수호출 / 필요한 파라미터(변수)를 넣어줌

                if(updated){
                    dispose();//현재 프레임 창 닫기
                }
            }
        });

        //삭제버튼 눌렀을때
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean deleted = myPageFuc.delete(msgId); // delete 함수 호출

                // 삭제가 성공한 경우에만 로그인 화면으로 이동 및 현재 창 닫기
                if (deleted) {
                    cardPanel.add(new LoginScreen(cardLayout, cardPanel), "login");
                    cardLayout.show(cardPanel,"login");
                    dispose(); // 현재 프레임 창 닫기
                }
            }
        });

    }
}
