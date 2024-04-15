package src.naver.pin_project.data;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CustomFont {

    public static void setUIFont(Font font) {
        // 각각의 UI 요소에 폰트 적용
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("Panel.font", font);
        UIManager.put("Dialog.font", font);
    }

    public static Font loadFont(String fontPath, float fontSize) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // 오류 발생 시 기본 폰트 반환
            return new Font("Arial", Font.PLAIN, (int) fontSize);
        }
    }
}