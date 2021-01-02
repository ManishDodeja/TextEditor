package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class PredictNextWord {
    static String file = "C:\\Users\\Shankar\\Desktop\\TextEditor-1\\src\\com\\company\\data.txt";
    public static LinkedList.Node store() {


        return createLLFromData(file);


    }
    public static  String[] predictNextWord(String str){
        LinkedList.Node head = store();
        String[] arr = perdictNextWord(str, head);
        return arr;
    }
    public static LinkedList.Node createLLFromData(String file) {
        LinkedList list = new LinkedList();
        try {
            Scanner sc = new Scanner(new FileReader(file));
            while(sc.hasNext()) {
                list.add(sc.next().replaceAll("[-+^)(:.,&\"]*",""));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return list.head;
    }
    public static String[] perdictNextWord(String word, LinkedList.Node head){
        LinkedList list = new LinkedList();

        while(head!= null){
            if(word.equals(head.data)){
                if(head.next!= null && !list.contains(head.next.data)){
                    list.add(head.next.data);
                }
            }
            head = head.next;
        }
        return list.getArray();
    }
    public static void showPopup(){
        try {
            String str = TextPanel.area.getText();
            if(str.charAt(str.length()-1) == ' ')
                str =str.substring(0, str.length()-1);
            int i = str.lastIndexOf(" ") +1;

            str = TextPanel.area.getText(i, str.length()-i);
            String[] words = predictNextWord(str);
            if(words.length == 0){
                return;
            }
            ActionListener event = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JMenuItem m = (JMenuItem) e.getSource();
                    TextPanel.area.insert(m.getLabel()+" ", TextPanel.area.getCaretPosition());
                    TextPanel.pop.removeAll();
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