package src.naver.pin_project.view;

import src.naver.pin_project.data.User;
import src.naver.pin_project.db.DBHelper;
import src.naver.pin_project.viewmodel.MyPage_ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MyPageScreen extends JFrame {
    // 필드 정의하기
    private JTextField inputId;
    private JTextField inputName;
    private JTextField inputPw;

    public MyPageScreen(CardLayout cardLayout,User loggedInUser,JPanel cardPanel) {
        setSize(500, 300);
        setLocationRelativeTo(null); // 화면 가운데에 위치하도록 설정


        //화면에 진입하면, 최신 유저정보 받아오기.
        User newestUser = DBHelper.getUserInfoFromDB(loggedInUser.getUserId());

        JLabel title = new JLabel("마이페이지", JLabel.CENTER);

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
        imagePanel.add(imageLabel);

        // "ID: "라는 JLabel 생성
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        idPanel.add(new JLabel("아이디: ")); // 패널에 UI를 추가
        idPanel.add(inputId);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        namePanel.add(new JLabel("이름: "));
        namePanel.add(inputName);


        JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pwPanel.add(new JLabel("비밀번호: "));
        pwPanel.add(inputPw);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 1));
        formPanel.add(imagePanel);
        formPanel.add(idPanel);
        formPanel.add(namePanel);
        formPanel.add(pwPanel);

        JPanel contentPanel = new JPanel(new FlowLayout());
        contentPanel.add(formPanel);

        // 수정버튼
        JButton update = new JButton("수정");

        // 삭제버튼
        JButton delete = new JButton("삭제");

        // 패널 객체를 생성해서
        JPanel buttonPanel = new JPanel(new FlowLayout());// FlowLayout 사용하여 버튼들이 화면 가운데에 위치하도록 설정

        // 버튼 패널에 버튼 추가
        buttonPanel.add(update);
        buttonPanel.add(delete);

        add(title, BorderLayout.NORTH);

        // 패널 통째로 프레임에 추가하기
        add(contentPanel, BorderLayout.CENTER);

        // 버튼 패널 프레임에 추가하기
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);


        MyPage_ViewModel myPageFuc = new MyPage_ViewModel();

        String msgId = inputId.getText();
        String msgName = inputName.getText();
        String msgPw = inputPw.getText();

        //업데이트 버튼 클릭했을때
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                myPageFuc.update(msgId, msgName, msgPw); //update함수에게 필요한 파라미터(변수)를 넣어줌
                dispose();
            }
        });

        //삭제버튼 눌렀을때
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                myPageFuc.delete(msgId);
                dispose();

                //회원이 삭제되면 로그인화면으로 이동
                cardPanel.add(new LoginScreen(cardLayout, cardPanel), "login");
                cardLayout.show(cardPanel,"login");
            }
        });
    }




}