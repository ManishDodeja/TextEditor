package com.company;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class PredictWord extends Thread{

    PredictWord(){
        start();
    }

    @Override
    public void run() {
        showPopup();
    }

    private static void showPopup(){
        try {
            String[] words = TextPanel.tries.getWords(Constants.LastWord()).getArray();
            if(words.length == 0){
                return;
            }
            ActionListener event = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JMenuItem m = (JMenuItem) e.getSource();
                    TextPanel.area.replaceRange(m.getLabel()+" ", TextPanel.area.getText().lastIndexOf(" ")+1, TextPanel.area.getText().length());
                    TextPanel.pop.removeAll();
                    new Store();

                }
            };
            TextPanel.pop.removeAll();
            for(String s : words){
                JMenuItem item = new JMenuItem(s);
                item.addActionListener(event);
                TextPanel.pop.add(item);
            }
            Rectangle rectangle = new Rectangle(TextPanel.area.getCaret().getMagicCaretPosition());
            TextPanel.pop.show(Main.textPanel, rectangle.x, rectangle.y);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
class Store extends Thread{
    Store(){
        start();
    }
    private void storeLastWord(){
        TextPanel.tries.insert(Constants.LastWord());

    }
    @Override
    public void run() {
        storeLastWord();
    }
}