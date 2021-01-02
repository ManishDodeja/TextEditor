package com.company;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.Arrays;

public class TextPanel extends JPanel{
    static JTextArea area = new JTextArea();
    static JPopupMenu pop = new JPopupMenu();
    static Tries tries = new Tries();
    public TextPanel(){

        area.addKeyListener(new KeyListener());

        setLayout(new BorderLayout());
        //area.setSize(500, 500);
        area.setLineWrap(true);
        area.setWrapStyleWord(false);
        //area.setSize(200, 300);

        //area.setPreferredSize(new Dimension(1345, 5000));

        JScrollPane scroll = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        setPreferredSize(new Dimension((int)Constants.screeSize.getWidth(), 570));
        add(scroll, BorderLayout.CENTER);
        new UndoRedo();
        area.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_SPACE){
                    TextPanel.area.firePropertyChange("#"+TextPanel.area.getText(), TextPanel.area.getCaretPosition(), 0);
                }
                if(e.isControlDown() ){
                    int key = e.getKeyCode();
                    if(key == KeyEvent.VK_V || key == KeyEvent.VK_C || key == KeyEvent.VK_X){
                        TextPanel.area.firePropertyChange("#"+TextPanel.area.getText(), TextPanel.area.getCaretPosition(), 0);
                    }
                }
            }
        });
    }
}