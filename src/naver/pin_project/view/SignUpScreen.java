package src.naver.pin_project.view;

import src.naver.pin_project.data.User;
import src.naver.pin_project.db.DBHelper;
import src.naver.pin_project.viewmodel.SignUp_ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.HashSet;


public class SignUpScreen extends JDialog {
    private JTextField usernameField; // 사용자 이름 입력 필드
    private JTextField idField; // 아이디 입력 필드
    private JPasswordField passwordField; // 비밀번호 입력 필드
    private JPasswordField confirmPasswordField; //비밀번호 확인 입력 필드
    private JButton checkDuplicateButton; // 아이디 중복 확인 버튼
    private JTextField phoneField1; // 휴대전화번호 입력필드1
    private JTextField phoneField2; // 휴대전화번호 입력필드2
    private JTextField phoneField3; // 휴대전화번호 입력필드3
    private JTextField verificationCodeField; // 인증번호 입력 필드
    private JButton sendVerificationCodeButton; // 인증번호 받기 버튼

    private HashSet<String> existingUsernames = new HashSet<>();

    public SignUpScreen() {
        setTitle("회원가입");
        setSize(500,400);
        setLayout(new BorderLayout());
        //메인 패널 생성
        JPanel mainPanel = new JPanel(new BorderLayout()); // 메인패널 생성

        // GUI 구성 요소를 포함한 패널 생성
        JPanel textFieldPanel = createTextFieldPanel();
        JPanel buttonPanel = createButtonPanel();

        // 패널을 메인 패널에 추가
        mainPanel.add(textFieldPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        //setVisible(true);
    }

    // GUI 구성 요소를 포함한 텍스트 필드 패널 생성
    private JPanel createTextFieldPanel() {
        JPanel textFieldPanel = new JPanel(new GridBagLayout());
        textFieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // 사용자 이름 텍스트 필드
        gbc.gridx = 0;
        gbc.gridy = 0;
        textFieldPanel.add(new JLabel("사용자 이름", SwingConstants.LEFT), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        textFieldPanel.add(usernameField, gbc);


        // 아이디 텍스트 필드
        gbc.gridx = 0;
        gbc.gridy = 1;
        textFieldPanel.add(new JLabel("아이디", SwingConstants.LEFT), gbc);
        gbc.gridx = 1;
        idField = new JTextField(15);
        textFieldPanel.add(idField, gbc);


        // 아이디 중복 확인 버튼
        gbc.gridx = 2;
        checkDuplicateButton = new JButton("중복확인");
        JPanel buttonpanel = new JPanel();
        buttonpanel.add(checkDuplicateButton);
        textFieldPanel.add(buttonpanel, gbc);


        //비밀번호 텍스트 필드
        gbc.gridx = 0;
        gbc.gridy = 2;
        textFieldPanel.add(new JLabel("비밀번호", SwingConstants.LEFT), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        textFieldPanel.add(passwordField, gbc);


        // 비밀번호 확인 텍스트 필드
        gbc.gridx = 0;
        gbc.gridy = 3;
        textFieldPanel.add(new JLabel("비밀번호 확인", SwingConstants.LEFT), gbc);
        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(15);
        textFieldPanel.add(confirmPasswordField, gbc);


        // 생년월일 입력 텍스트 필드
        gbc.gridx = 0;
        gbc.gridy = 4;
        textFieldPanel.add(new JLabel("생년월일", SwingConstants.LEFT), gbc);
        gbc.gridx = 1;

        // placehold 같은 기능 추가
        JTextField birthDateField = new JTextField("YYYY-MM-DD 형식으로 입력하세요");
        birthDateField.setForeground(Color.GRAY);
        birthDateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (birthDateField.getText().equals("YYYY-MM-DD 형식으로 입력하세요")) {
                    birthDateField.setText("");
                    birthDateField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (birthDateField.getText().isEmpty()) {
                    birthDateField.setForeground(Color.GRAY);
                    birthDateField.setText("YYYY-MM-DD 형식으로 입력하세요");
                }
            }
        });
        textFieldPanel.add(birthDateField, gbc);

        // 휴대전화번호 라벨
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        textFieldPanel.add(new JLabel("휴대전화번호", SwingConstants.LEFT), gbc);

        // 휴대전화번호 입력 필드를 위한 패널
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)); // 5는 컴포넌트 사이의 수평 간격, 0은 수직 간격

        // 휴대전화번호 첫 번째 부분 입력 필드
        phoneField1 = new JTextField(4); // 너비 조절
        phonePanel.add(phoneField1);

        // 첫 번째와 두 번째 입력 필드 사이의 하이픈
        phonePanel.add(new JLabel("-"));

        // 휴대전화번호 두 번째 부분 입력 필드
        phoneField2 = new JTextField(5); // 너비 조절
        phonePanel.add(phoneField2);

        // 두 번째와 세 번째 입력 필드 사이의 하이픈
        phonePanel.add(new JLabel("-"));

        // 휴대전화번호 세 번째 부분 입력 필드
        phoneField3 = new JTextField(5); // 너비 조절
        phonePanel.add(phoneField3);

        // phonePanel을 메인 패널에 추가
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1; // 이 컴포넌트가 차지할 가로 칸 수
        textFieldPanel.add(phonePanel, gbc);

        // "인증번호 받기" 버튼 추가
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // 이 컴포넌트가 행의 나머지 부분을 차지하도록 설정
        sendVerificationCodeButton = new JButton("인증번호 받기");
        textFieldPanel.add(sendVerificationCodeButton, gbc);

        // gridwidth를 다시 기본값으로 설정
        gbc.gridwidth = 1;

        // 인증번호 입력 텍스트필드
        gbc.gridx = 0;
        gbc.gridy = 6;
        textFieldPanel.add(new JLabel("인증번호", SwingConstants.LEFT), gbc);
        gbc.gridx = 1;
        verificationCodeField = new JTextField(15);
        textFieldPanel.add(verificationCodeField, gbc);

        return textFieldPanel;
    }


    // 버튼 패널 생성
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton signupButton = new JButton("회원가입");
        JButton cancelButton = new JButton("취   소");
        buttonPanel.add(signupButton);
        buttonPanel.add(cancelButton);

        // 취소 버튼 눌렀을 때 이벤트
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(SignUpScreen.this,
                        "정말로 취소하시겠습니까?",
                        "취소 확인",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    dispose(); // 창 닫기
                }
            }
        });

        // 아이디 중복 확인 버튼 눌렀을 때 이벤트
        checkDuplicateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkUserId(); // 아이디 중복 확인 로직 호출
            }
        });

        // 인증번호 받기 버튼 눌렀을 때 이벤트
        sendVerificationCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 여기에 인증번호를 받는 로직을 구현합니다.
                String phone =
                        phoneField1.getText()+"-"
                                +phoneField2.getText()+"-"
                                +phoneField3.getText();
                // 휴대폰 번호로 인증번호 전송



                // 인증번호가 발송되었음을 나타내는 메시지 표시
                JOptionPane.showMessageDialog(SignUpScreen.this,
                        phone+"으로 인증번호가 발송되었습니다.",
                        "인증번호 전송",
                        JOptionPane.INFORMATION_MESSAGE);

            }
        });

        // 회원가입 버튼 이벤트
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInputFields() && checkPassword()) {
                    // 회원가입 로직 구현
                    String username = usernameField.getText();
                    String userId = idField.getText();
                    String password = new String(passwordField.getPassword());
                    // ... 회원 정보를 저장하거나 서버로 전송하는 등의 작업 수행

                    // Join_Viewmodel 클래스의 인스턴스 생성
                    SignUp_ViewModel joinViewmodel = new SignUp_ViewModel();

                    // 사용자 등록
                    try {
                        // Join_Viewmodel 클래스의 인스턴스를 사용하여 registerUser 메서드 호출
                        int rowsAffected = joinViewmodel.registerUser(userId, username, password);
                        if (rowsAffected > 0) {
                            // 회원가입 성공 메시지를 표시합니다.
                            JOptionPane.showMessageDialog(SignUpScreen.this,
                                    username + " 님의 회원가입이 완료되었습니다.",
                                    "회원가입 완료",
                                    JOptionPane.INFORMATION_MESSAGE);

                        dispose();
                        } else {
                            // 회원가입 실패 메시지를 표시합니다.
                            JOptionPane.showMessageDialog(SignUpScreen.this,
                                    "회원가입에 실패하였습니다.",
                                    "회원가입 실패",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        // SQL 예외를 처리합니다.
                        JOptionPane.showMessageDialog(SignUpScreen.this,
                                "회원가입 중 오류가 발생하였습니다: " + ex.getMessage(),
                                "회원가입 오류",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        return buttonPanel;
    }

    // 입력 필드의 값이 비어 있는지 확인
    private boolean checkInputFields() {
        String username = usernameField.getText();
        String userId = idField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String phone = phoneField1.getText() + "-" + phoneField2.getText() + "-" + phoneField3.getText();
        String verificationCode = verificationCodeField.getText();

        if (username.isEmpty() || userId.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty() || verificationCode.isEmpty()) {
            JOptionPane.showMessageDialog(SignUpScreen.this,
                    "모든 입력란을 작성해주세요.",
                    "입력 오류",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void checkUserId() {
        String userid = idField.getText();
        User duplicate_user = DBHelper.getUserInfoFromDB(userid);
        if (duplicate_user.getUserId().equals(userid)) {
            JOptionPane.showMessageDialog(this,
                    "이미 존재하는 아이디입니다.",
                    "중복 확인", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "사용 가능한 아이디입니다.",
                    "중복 확인",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean checkPassword() {
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (password.equals(confirmPassword)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this,
                    "비밀번호가 일치하지 않습니다.",
                    "비밀번호 확인",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

}
