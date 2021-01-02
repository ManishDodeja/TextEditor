package com.company;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Actions implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "New":
                Main.newFile();
                break;
            case "Open":
                Main.open();
                break;
            case "Save":
                Main.checkSave();
                break;
            case "Save as":
                Main.save();
                break;
            case "Close":
                Main.close();
                break;
            case "Undo":
                Main.undo();
                break;
            case "Redo":
                Main.redo();
                break;
            case  "Copy":
                Main.copy();
                break;
            case "Paste":
                Main.paste();
                break;
            case "Cut":
                Main.cut();
                break;
            case "Delete":
                Main.delete();
                break;
            case "Date":
                Main.insertDateAndTime();
                break;
            case "Find":
                OperationPanel.ff.setVisible(true);
                break;
        }
    }
}
