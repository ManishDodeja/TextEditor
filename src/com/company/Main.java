package com.company;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Scanner;

public class Main {
    static JFrame frame = new JFrame("Text Editor");
    static OperationPanel panel = new OperationPanel();
    static TextPanel textPanel = new TextPanel();
    static JLabel status = new JLabel("Lines : 0 |" + " Words : 0 |" + " Length : 0");
    static File Textfile = null;

    static JMenuBar mb;
    static JMenu file, edit;
    static JMenuItem newItem, open, save, saveAs, close, undo,redo, copy, paste, cut, delete, dateAndTime, find;


    public static void main(String[] args) {

        Textfile = null;
        addMenues();



        frame.setTitle("New");
        frame.setSize(Constants.screeSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(panel);
        frame.setIconImage(new ImageIcon("sparks.jpeg").getImage());

        frame.add(textPanel);
        //frame.add(new JTextArea());

        frame.add(status);

        frame.setVisible(true);

        frame.pack();
        TextPanel.area.grabFocus();
        StatusBar sb = new StatusBar();

        //frame.addKeyListener(new KeyListener());
    }


    private static void addMenues(){
        mb = new JMenuBar();

        file = new JMenu("File");
        newItem = new JMenuItem("New \t Ctrl+N");
        newItem.setActionCommand("New");
        file.add(newItem);

        open = new JMenuItem("Open \t Ctrl+O");
        open.setActionCommand("Open");
        file.add(open);

        save = new JMenuItem("Save \t Ctrl+S");
        save.setActionCommand("Save");
        file.add(save);

        saveAs = new JMenuItem("Save as \t Ctrl+Shift+S");
        saveAs.setActionCommand("Save as");
        file.add(saveAs);

        close = new JMenuItem("Close \t Ctrl+W");
        close.setActionCommand("Close");
        file.add(close);

        edit = new JMenu("Edit");

        undo = new JMenuItem("Undo \t Ctrl+Z");
        undo.setActionCommand("Undo");
        edit.add(undo);

        redo = new JMenuItem("Redo \t Ctrl+Y");
        redo.setActionCommand("Redo");
        edit.add(redo);

        copy = new JMenuItem("Copy \t Ctrl+C");
        copy.setActionCommand("Copy");
        edit.add(copy);

        paste = new JMenuItem("Paste \t Ctrl+V");
        paste.setActionCommand("Paste");
        edit.add(paste);

        cut = new JMenuItem("Cut \t Ctrl+X");
        cut.setActionCommand("Cut");
        edit.add(cut);

        find = new JMenuItem("Find");
        find.setActionCommand("Find");
        edit.add(find);

        delete = new JMenuItem("Delete");
        delete.setActionCommand("Delete");
        edit.add(delete);

        dateAndTime = new JMenuItem("Time and Date");
        dateAndTime.setActionCommand("Date");
        edit.add(dateAndTime);

        mb.add(file);
        mb.add(edit);
        mb.setPreferredSize(new Dimension((int)Constants.screeSize.getWidth(), 30));

        //frame.add(mb);
        frame.setJMenuBar(mb);

        newItem.addActionListener(new Actions());
        open.addActionListener(new Actions());
        save.addActionListener(new Actions());
        saveAs.addActionListener(new Actions());
        close.addActionListener(new Actions());
        undo.addActionListener(new Actions());
        redo.addActionListener(new Actions());
        copy.addActionListener(new Actions());
        paste.addActionListener(new Actions());
        cut.addActionListener(new Actions());
        delete.addActionListener(new Actions());
        dateAndTime.addActionListener(new Actions());
        find.addActionListener(new Actions());
    }

    public static void undo(){
        UndoRedo.Undo();
    }
    public static void copy(){
        TextPanel.area.firePropertyChange("#"+TextPanel.area.getText(), TextPanel.area.getCaretPosition(), 0);
        TextPanel.area.copy();
    }
    public static void cut(){
        TextPanel.area.firePropertyChange("#"+TextPanel.area.getText(), TextPanel.area.getCaretPosition(), 0);
        TextPanel.area.cut();
    }
    public static void paste(){
        TextPanel.area.firePropertyChange("#"+TextPanel.area.getText(), TextPanel.area.getCaretPosition(), 0);
        TextPanel.area.paste();
    }

    public static void redo(){
        UndoRedo.Redo();
    }
    public static void delete(){
        TextPanel.area.firePropertyChange("#"+TextPanel.area.getText(), TextPanel.area.getCaretPosition(), 0);
        TextPanel.area.replaceSelection("");
    }
    public static void insertDateAndTime(){
        Date date = new Date();
        TextPanel.area.firePropertyChange("#"+TextPanel.area.getText(), TextPanel.area.getCaretPosition(), 0);
        TextPanel.area.insert(date.toString(), TextPanel.area.getCaretPosition());
    }
    public static void checkSave(){
        if(Textfile == null)
            save();
        else
            write(Textfile);
    }

    public static void close(){
        System.exit(0);
    }

    public static void newFile(){
        Textfile = null;
        TextPanel.area.setText("");
        frame.setTitle("New");
    }

    public static void open(){

        Scanner sc = null;
        int resoponse = 0;
        JFileChooser chooser = new JFileChooser("");

        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        resoponse = chooser.showOpenDialog(null);
        if(resoponse == JFileChooser.APPROVE_OPTION){
            try{
                Textfile = chooser.getSelectedFile();
                if(Textfile == null) return;
                String text = "";
                sc = new Scanner(Textfile);
                while (sc.hasNextLine())
                    text += sc.nextLine() + "\n";
                TextPanel.area.setText(text);
                frame.setTitle(Textfile.getName());
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Please select the file");
            }
            finally {
                sc.close();
            }
        }
    }

    public static void save(){
        JFileChooser chooser = new JFileChooser("");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int response = chooser.showSaveDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            Textfile = chooser.getSelectedFile();
            write(Textfile);
            frame.setTitle(Textfile.getName());
        }
    }

    private static void write(File file){
        if(file == null)return;
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)){
            fileOutputStream.write(TextPanel.area.getText().getBytes());
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Please select the file");
        }
    }


}
