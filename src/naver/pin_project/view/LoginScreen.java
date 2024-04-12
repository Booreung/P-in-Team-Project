package src.naver.pin_project.view;

import src.naver.pin_project.data.User;
import src.naver.pin_project.db.DBHelper;
import src.naver.pin_project.viewmodel.Login_ViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginScreen extends JPanel {
    public LoginScreen(CardLayout cardLayout, JPanel cardPanel){
        setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon logoIcon = new ImageIcon("src/naver/pin_project/lib/Pin로고.png");
        Image image = logoIcon.getImage();
        Image newImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon newlogoImage = new ImageIcon(newImage);
        JLabel logoLabel = new JLabel(newlogoImage);
        loginPanel.setBackground(Color.decode("#8A8585"));
        loginPanel.setBorder(new EmptyBorder(50, 0, 0, 0));
        loginPanel.add(logoLabel);

        //입력 필드 패널
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        inputPanel.setBorder(new EmptyBorder(0, 0, 70, 0));
        
        JLabel idLabel = new JLabel("아이디");
        idLabel.setForeground(Color.decode("#FFFFFF"));
        JTextField idField = new JTextField(15);
        // 라벨의 폰트 크기 설정
        idLabel.setFont(idLabel.getFont().deriveFont(Font.BOLD, 20));
        // 텍스트 필드의 크기 설정
        idField.setPreferredSize(new Dimension(300, 30)); // 가로 크기 조절
        idField.setFont(idField.getFont().deriveFont(Font.PLAIN, 20)); // 폰트 크기 조절

        JLabel pwLabel = new JLabel("비밀번호");
        pwLabel.setForeground(Color.decode("#FFFFFF"));
        JPasswordField pwField = new JPasswordField(15);
        // 라벨의 폰트 크기 설정
        pwLabel.setFont(idLabel.getFont().deriveFont(Font.BOLD, 20));
        pwField.setPreferredSize(new Dimension(300, 30)); // 가로 크기 조절
        pwField.setFont(idField.getFont().deriveFont(Font.PLAIN, 20));

        //위의 필드들을 inputPanel에 추가
        //각자의 행값을 부여해서 위치 조정
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(idLabel,gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(idField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(pwLabel,gbc);

        gbc.gridx = 1;  gbc.gridy = 1;
        inputPanel.add(pwField,gbc);

        //버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        JButton loginBtn = new JButton("로그인");
        JButton joinBtn = new JButton("회원가입");
        JButton nonloginBtn = new JButton("비회원 로그인");
        //버튼 크기 조절
        Dimension buttonSize = new Dimension(150,40);
        loginBtn.setPreferredSize(buttonSize);
        joinBtn.setPreferredSize(buttonSize);
        nonloginBtn.setPreferredSize(buttonSize);

        //배경색 설정
        inputPanel.setBackground(Color.decode("#8A8585"));
        buttonPanel.setBackground(Color.decode("#8A8585"));
        loginBtn.setBackground(Color.decode("#B0FFA9"));
        joinBtn.setBackground(Color.decode("#FCEB83"));
        nonloginBtn.setBackground(Color.decode("#8DFFF3"));


        //로그인 버튼을 누르면 메인 키오스크 화면으로
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //여기에 로그인 유효 검사랑 로그인 실패했을때
                String userId = idField.getText();
                String password = new String(pwField.getPassword());

                User user = new User();
                user.setUserId(userId);
                user.setPassword(password);

                Login_ViewModel loginViewModel = new Login_ViewModel(user);
                if(loginViewModel.isValidUser()){
                    //로그인 성공시 해당 사용자 정보를 가져오기
                    User loggedInUser = DBHelper.getUserInfoFromDB(userId);
                    // 로그인 성공시 로그인 성공 메세지 후 메인으로 이동
                    JOptionPane.showMessageDialog(null,"로그인 성공");
                    //cardLayout.show(cardPanel,"main");
                    cardPanel.add(new MainScreen(cardLayout,loggedInUser,cardPanel),"main");
                    cardLayout.show(cardPanel,"main");
                }
                else{
                    JOptionPane.showMessageDialog(null,"로그인 실패");

                }

            }
        });
        joinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //여기에는 회원가입 화면으로 전환되는 동작
                SignUpScreen signUpScreen = new SignUpScreen();
                signUpScreen.setModal(true);
                signUpScreen.setVisible(true);
            }
        });
        nonloginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //이름이 없이 전달을 위해 빈 user로 객체 생성후 전달
                User non_user = new User();
                non_user.setUserId("Guest");
                non_user.setUserName("게스트");
                non_user.setPassword("1234");

                //여기는 바로 메인 화면으로 가는데 이름없이 전돨되어야함
                JOptionPane.showMessageDialog(null,"비회원 로그인 성공");
                cardPanel.add(new MainScreen(cardLayout,non_user,cardPanel),"main");
                cardLayout.show(cardPanel,"main");
            }
        });

        //버튼들을 버튼 패널에 추가
        buttonPanel.add(loginBtn);
        buttonPanel.add(joinBtn);
        buttonPanel.add(nonloginBtn);

        //버튼패널, input필드를 전체 화면에 추가
        add(loginPanel,BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
