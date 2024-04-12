package src.naver.pin_project.view;

import src.naver.pin_project.data.User;
import src.naver.pin_project.db.DBHelper;
import src.naver.pin_project.viewmodel.MyPage_ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPageScreen extends JFrame {
    // 필드 정의하기
    private JTextField inputId;
    private JTextField inputName;
    private JTextField inputPw;

    public MyPageScreen(CardLayout cardLayout,User loggedInUser,JPanel cardPanel) {
        setSize(500, 300);
        setLocationRelativeTo(null); // 화면 가운데에 위치하도록 설정
        setBackground(Color.decode("#8A8585"));


        //화면에 진입하면, 최신 유저정보 받아오기.
        User newestUser = DBHelper.getUserInfoFromDB(loggedInUser.getUserId());

        JLabel title = new JLabel("마이페이지", JLabel.CENTER);
        title.setOpaque(true);
        title.setBackground(Color.decode("#8A8585"));
        title.setForeground(Color.decode("#FFFFFF"));

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

        // "ID: "라는 JLabel 생성
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel idLabel = new JLabel("아이디 :");
        idLabel.setForeground(Color.decode("#FFFFFF"));
        idPanel.add(idLabel); // 패널에 UI를 추가
        idPanel.add(inputId);
        idPanel.setBackground(Color.decode("#8A8585"));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel nameLabel = new JLabel(("이름 :"));
        nameLabel.setForeground(Color.decode("#FFFFFF"));
        namePanel.add(nameLabel);
        namePanel.add(inputName);
        namePanel.setBackground(Color.decode("#8A8585"));


        JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel pwLabel = new JLabel("비밀번호 :");
        pwLabel.setForeground(Color.decode("#FFFFFF"));
        pwPanel.add(pwLabel);
        pwPanel.add(inputPw);
        pwPanel.setBackground(Color.decode("#8A8585"));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 1));
        formPanel.add(imagePanel);
        formPanel.add(idPanel);
        formPanel.add(namePanel);
        formPanel.add(pwPanel);
        formPanel.setBackground(Color.decode("#8A8585"));

        JPanel contentPanel = new JPanel(new FlowLayout());
        contentPanel.setBackground(Color.decode("#8A8585"));
        contentPanel.add(formPanel);

        // 수정버튼
        JButton update = new JButton("수정");
        update.setBackground(Color.decode("#FCEB83"));

        // 삭제버튼
        JButton delete = new JButton("삭제");
        delete.setBackground(Color.decode("#8DFFF3"));

        // 패널 객체를 생성해서
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#8A8585"));// FlowLayout 사용하여 버튼들이 화면 가운데에 위치하도록 설정

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

        //업데이트 버튼 클릭했을때
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newmsgId = inputId.getText();
                String msgName = inputName.getText();
                String msgPw = inputPw.getText();

                myPageFuc.update(msgId, msgName,msgPw, newmsgId); //update함수에게 필요한 파라미터(변수)를 넣어줌

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