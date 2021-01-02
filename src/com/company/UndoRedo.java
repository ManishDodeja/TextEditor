package com.company;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UndoRedo {
    static Stack stack1 = new Stack();
    static Stack stack2 = new Stack();
    static PropertyChangeListener evtListener = (new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if("foreground".equals(evt.getPropertyName())){
                Do(evt);
            }
            else if("font".equals(evt.getPropertyName())){
                Do(evt);
            }
            else if(evt.getPropertyName().charAt(0) == '#'){
                Do(evt);
            }
        }
    });
    UndoRedo(){
        TextPanel.area.addPropertyChangeListener(evtListener);
        Do(new PropertyChangeEvent(TextPanel.area, "#", 0, 0));
    }
    static void Do(PropertyChangeEvent evt){
        stack1.push(evt);
    }

    static void Undo(){
        PropertyChangeEvent evt = stack1.pop();
        if(evt == null){
            return;
        }
        TextPanel.area.removePropertyChangeListener(evtListener);
        String property = evt.getPropertyName();
        if(property.equals("font")){
            Font font = (Font) evt.getOldValue();
            TextPanel.area.setFont(font);
            String size = font.getSize()+"";
            OperationPanel.fontSize.setSelectedItem(size);
            OperationPanel.fontStyles.setSelectedItem(font.getFamily());
            OperationPanel.boldStyle.setSelected(font.isBold());
            OperationPanel.italicStyle.setSelected(font.isItalic());
            boolean b = font.getAttributes().containsKey(TextAttribute.UNDERLINE);
            OperationPanel.underLineStyle.setSelected(b);
        }
        else if(property.equals("foreground")){
            Color c = (Color) evt.getOldValue();
            TextPanel.area.setForeground(c);
            OperationPanel.fontColor.setForeground(c);
            if(c.equals(Color.WHITE)){
                OperationPanel.fontColor.setBackground(Color.BLACK);
            }
            else{
                OperationPanel.fontColor.setBackground(Color.WHITE);
            }
        }
        else if(property.charAt(0) == '#'){

            PropertyChangeEvent event = new PropertyChangeEvent(TextPanel.area, "#"+TextPanel.area.getText(), evt.getOldValue(), TextPanel.area.getCaretPosition());

            if(property.length() == 1){
                TextPanel.area.setText("");
                TextPanel.area.setCaretPosition(0);
            }
            TextPanel.area.setText(property.substring(1));
            TextPanel.area.setCaretPosition((int)evt.getOldValue());
            evt = event;
        }
        TextPanel.area.addPropertyChangeListener(evtListener);
        stack2.push(evt);
    }
    static void Redo(){
        PropertyChangeEvent evt = stack2.pop();
        if(evt == null){
            return;
        }
        String property = evt.getPropertyName();
        if(property.equals("font")){
            Font font = (Font) evt.getNewValue();
            TextPanel.area.setFont(font);
            String size = font.getSize()+"";
            OperationPanel.fontSize.setSelectedItem(size);
            OperationPanel.fontStyles.setSelectedItem(font.getFamily());
            OperationPanel.boldStyle.setSelected(font.isBold());
            OperationPanel.italicStyle.setSelected(font.isItalic());
            boolean b = font.getAttributes().containsKey(TextAttribute.UNDERLINE);
            OperationPanel.underLineStyle.setSelected(b);
        }
        else if(property.equals("foreground")){
            Color c = (Color) evt.getNewValue();
            TextPanel.area.setForeground(c);
            OperationPanel.fontColor.setForeground(c);
            if(c.equals(Color.WHITE)){
                OperationPanel.fontColor.setBackground(Color.BLACK);
            }
            else{
                OperationPanel.fontColor.setBackground(Color.WHITE);
            }
        }
        else if(property.charAt(0) == '#'){
            PropertyChangeEvent event = new PropertyChangeEvent(TextPanel.area, "#"+TextPanel.area.getText(), TextPanel.area.getCaretPosition(), evt.getNewValue());
            if(property.length() == 1){
                TextPanel.area.setText("");
                TextPanel.area.setCaretPosition(0);
            }
            TextPanel.area.setText(property.substring(1));
            TextPanel.area.setCaretPosition((int)evt.getNewValue());
            Do(event);
        }
    }
}
class Stack{
    class Node{
        PropertyChangeEvent evt = null;
        Node next = null;
        Node prev = null;
        Node(PropertyChangeEvent evt){
            this.evt = evt;
        }
    }
    Node first = null;
    Node last = null;
    int size = 0;
    public void push(PropertyChangeEvent evt){
        if(first == null){
            size++;
            first = last = new Node(evt);
            return;
        }
        Node node = new Node(evt);
        last.next = node;
        node.prev = last;
        last = node;
        size++;
        if(size >=10){
            Node second = first.next;
            second.prev = null;
            first.next = null;
            first = second;
            size--;
        }
    }
    public PropertyChangeEvent pop(){
        if(first == null){
            return null;
        }
        if(first == last){
            PropertyChangeEvent evt = last.evt;
            first = last = null;
            return evt;
        }
        Node last2 = last.prev;
        last.prev = null;
        last2.next = null;
        PropertyChangeEvent evt = last.evt;
        last = last2;
        return evt;
    }
}
