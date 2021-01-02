package com.company;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class Constants {
    public static Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static String LastWord(){
        String str = TextPanel.area.getText();

        if(str.charAt(str.length()-1) == ' ')
            str =str.substring(0, str.length()-1);

        int i = str.lastIndexOf(" ") +1;
        try {
            str = TextPanel.area.getText(i, str.length()-i);
        } catch (BadLocationException e) {

            e.printStackTrace();
        }
        return str;
    }
}
