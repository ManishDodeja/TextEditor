package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class StatusBar extends Thread{
    public StatusBar() {
        start();
    }
    public void run(){
        while(true){
            int lines = TextPanel.area.getLineCount();
            String words = TextPanel.area.getText();

            Main.status.setText("Lines : " + lines +" | Words : " + numWords(words) + " | Length : " + words.length());
        }
    }
    private int numWords(String str){
        Scanner sc = new Scanner(str);
        int words = 0;
        while(sc.hasNext()){
            sc.next();
            words++;
        }
        return words;
    }

}
