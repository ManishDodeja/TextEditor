package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.Map;

public class OperationPanel extends JPanel implements ActionListener, ItemListener{
    static JButton fontColor = new JButton("Font-Color");
    static JComboBox fontSize;
    static JCheckBox boldStyle = new JCheckBox("Bold");
    static JCheckBox italicStyle = new JCheckBox("Italic");
    static JCheckBox underLineStyle = new JCheckBox("UnderLine");
    static JComboBox fontStyles;
    static FindFrame ff = new FindFrame();
    public OperationPanel(){

        setLayout(null);
        setBackground(Color.green);

        setPreferredSize(new Dimension((int) Constants.screeSize.getWidth(), 70));


        boldStyle.setBackground(Color.green);
        italicStyle.setBackground(Color.green);
        underLineStyle.setBackground(Color.green);

        boldStyle.addActionListener(this);
        italicStyle.addActionListener(this);
        underLineStyle.addActionListener(this);

        Box styles = new Box(1);

        styles.add(boldStyle);
        styles.add(italicStyle);
        styles.add(underLineStyle);
        styles.setBounds(0, 0, 100, 100);

        String string = "Arial Algerian Elephant Calibri Cambria Century ";
        String[] fontStyle = {"Arial", "Agency FB" ,"Algerian", "Arial Black", "Arial Narrow", "Arial Rounded MT Bold", "Arial Unicode MS", "Bahnschrift", "Blackadder ITC","Calibri", "Cambria", "Cambria Math", "Candra","Century", "Comic Sans MS", "Consolas", "Constantia", "Corbel", "Courier New", "Dialog","Ebrima", "Elephant", "Franklin Gothic Medium", "Gobriola", "Gadugi", "Georgia", "Impact", "Ink Free", "Javanese Text", "Leelawadee UI", "Microsoft Himalaya", "Microsoft JhengHei", "Microsoft PhagsPa",};


        fontStyles = new JComboBox(fontStyle);
        fontStyles.setBounds(100, 0, 100, 20);
        fontStyles.setSelectedIndex(19);
        fontStyles.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Font font = TextPanel.area.getFont();
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.FAMILY, e.getItem().toString());
                TextPanel.area.setFont(font.deriveFont(attributes));

            }
        });


        String str = "8 9 10 11 12 14 16 18 20 22 24 26 28 36 48 72";
        String[] size = str.split(" +");

        fontSize = new JComboBox(size);
        fontSize.setSelectedIndex(4);
        fontSize.setEditable(false);

        fontSize.addItemListener(this);
        fontSize.setBounds(100, 22, 100, 20);


        fontColor.setBounds(100, 46, 100, 20);
        fontColor.setBackground(Color.WHITE);
        fontColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Color c = JColorChooser.showDialog(Main.frame.getContentPane(), "Select Color", Color.BLACK, true);
                TextPanel.area.setForeground(c);
                fontColor.setForeground(c);

                if(c.equals(Color.WHITE)){
                    fontColor.setBackground(Color.BLACK);
                }
                else{
                    fontColor.setBackground(Color.WHITE);
                }

            }
        });
        JButton copy = new JButton("Copy");
        copy.setBounds(210, 0, 100, 20);
        copy.setActionCommand("Copy");
        copy.addActionListener(new Actions());

        JButton cut = new JButton("Cut");
        cut.setBounds(210, 22, 100, 20);
        cut.setActionCommand("Cut");
        cut.addActionListener(new Actions());

        JButton paste = new JButton("Paste");
        paste.setBounds(210, 46, 100, 20);
        paste.setActionCommand("Paste");
        paste.addActionListener(new Actions());

        JButton undo = new JButton("Undo");
        undo.setBounds(320, 0, 100, 20);
        undo.setActionCommand("Undo");
        undo.addActionListener(new Actions());

        JButton redo = new JButton("Redo");
        redo.setBounds(320, 22, 100, 20);
        redo.setActionCommand("Redo");
        redo.addActionListener(new Actions());

        JButton delete = new JButton("Delete");
        delete.setBounds(320, 46, 100, 20);
        delete.setActionCommand("Delete");
        delete.addActionListener(new Actions());
		
		
		//Find Button Coding
		JButton find = new JButton("Find");
        find.setBounds(430,0,120,20);
        find.setActionCommand("Find");
        find.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ff.setVisible(true);
			}
        });
        JButton predict_word = new JButton("Predict Word");
        predict_word.setBounds(430,22,120,20);
        Font font = new Font("Dialog", Font.PLAIN, 11);
        predict_word.setFont(font);
        predict_word.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PredictWord();
            }
        });

        JButton predict_next_word = new JButton("Predict Next Word");
        predict_next_word.setBounds(430,46,120,20);
        predict_next_word.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PredictNextWord.showPopup();
            }
        });


        predict_next_word.setFont(new Font("Dialog", Font.PLAIN, 10));
        
        
        add(styles);
        add(fontStyles);
        fontStyles.setToolTipText("Font Styles");
        add(fontSize);
        fontSize.setToolTipText("Font Size");
        add(fontColor);
        fontColor.setToolTipText("Font Color");
        add(copy);
        copy.setToolTipText("Copy Selected Text [ctrl + c]");
        add(cut);
        cut.setToolTipText("Cut Selected Text [ctrl + x]");
        add(paste);
        paste.setToolTipText("Paste Text [ctrl + v]");
        add(undo);
        undo.setToolTipText("Undo [ctrl + z]");
        add(redo);
        redo.setToolTipText("Redo [ctrl + y]");
        add(delete);
        delete.setToolTipText("Delete Selected Text [delete]");
		add(find);
		find.setToolTipText("Find or Replace Text [ctrl + f]");
		add(predict_word);
		predict_word.setToolTipText("Predict Word [ctrl + Enter]");
		add(predict_next_word);
        predict_next_word.setToolTipText("Predict Next Word [ctrl + E]");

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox ch = (JCheckBox) e.getSource();
        Font font = TextPanel.area.getFont();
        Map attributes = font.getAttributes();
        if(ch.isSelected()){
            if("bold".equalsIgnoreCase(ch.getText())){
                attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
            }
            else if("italic".equalsIgnoreCase(ch.getText())){
                attributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
            }
            else if("underLine".equalsIgnoreCase(ch.getText())){
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            }
        }
        else{
            if("bold".equalsIgnoreCase(ch.getText())){
                attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);

            }
            else if("italic".equalsIgnoreCase(ch.getText())){
                attributes.put(TextAttribute.POSTURE, 0);

            }
            else if("underLine".equalsIgnoreCase(ch.getText())){
                attributes.put(TextAttribute.UNDERLINE, -1);

            }

        }
        TextPanel.area.setFont(font.deriveFont(attributes));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        try {


            String string = e.getItem().toString();


            Double size = Double.parseDouble(string);

            Font font = TextPanel.area.getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.SIZE, size);

            TextPanel.area.setFont(font.deriveFont(attributes));
        }catch (Exception e1){
            System.out.println(e1);
        }
    }
}
