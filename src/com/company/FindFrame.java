package com.company;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;



public class FindFrame extends JFrame {

	/**
	 * Manish Dodeja
	 */
	private static final long serialVersionUID = 1L;
	//these comments will help you to understand the code easily

	static JPanel panel = new JPanel(new GridBagLayout());

	//Button Declaration
	static JButton FindWord=null,FindReplace=null, FindReplaceAll = null;
	static boolean firstClick = true;
	//Label Declartion
	static JLabel F = new JLabel("Find");
	static JLabel rw = new JLabel("Replace with");
	
	
	// Text Field for searching declaration
	static JTextField SText = new JTextField(30);
	static JTextField SText2 = new JTextField(30);

	CircularLinkedList listPos;
	//this is inner class implemented for highlight text
	static class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter{

		public MyHighlightPainter(Color c) {
			super(c);
		}
	}
	
	static Highlighter.HighlightPainter mhp = new MyHighlightPainter(Color.orange);
	
	
	//this is for remove highlighter 
		public void removeHighlights(JTextComponent comp) {
			Highlighter hilite =comp.getHighlighter();
			Highlighter.Highlight[] hilities = hilite.getHighlights();
			for(int i =0;i<hilities.length;i++) {
				if(hilities[i].getPainter() instanceof MyHighlightPainter) {
					hilite.removeHighlight(hilities[i]);
				}
			}
		}
	
	
	//This is method for highlighting the text
	static int pos = 0;
	public void highlight(JTextComponent textComp , String pattern) {

		try {
			Highlighter hilite = TextPanel.area.getHighlighter();
			removeHighlights(TextPanel.area);
			Document doc = textComp.getDocument();
			String text=doc.getText(0, doc.getLength());
			pos = listPos.removeFirst();
			hilite.addHighlight(pos, pos+pattern.length(), mhp);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	//Constructor
	FindFrame(){
		
		//Frame Settings is in this part regex
		super("Search Word");
		setLayout(new FlowLayout());
		setBackground(Color.black);
		setSize(500,200);
		setLocationRelativeTo(null);
		panel.setSize(getPreferredSize());

		// You will understand this further below 

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				removeHighlights(TextPanel.area);
			}
		});
		
		
		
		
		//Find Button Settings 
		FindWord = new JButton("Find Next");
		FindWord.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String SearchText=SText.getText();
				removeHighlights(TextPanel.area);
				String PanelText= TextPanel.area.getText();
				String arr[]=PanelText.split(" ");
				boolean found=false;
				if(listPos == null){
					for(int i=0;i<arr.length;i++) {
						if(arr[i].equalsIgnoreCase(SearchText)) {
							//calling highlight method which is implemented above
							found=true;
						}
					}
					if(found){
						makeList(SearchText, PanelText);
					}

				}
				if(listPos == null) {
					JOptionPane.showMessageDialog(TextPanel.area, " Word Not Found ");
					SText.setText("");
				}
				else{
					highlight(TextPanel.area,SText.getText());
				}
				firstClick = false;
		}});



		//Find and replace button 
		FindReplace = new JButton("Replace");
		FindReplaceAll = new JButton("Replace all");

		ActionListener replace  = (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String SearchText=SText.getText();
				String PanelText = TextPanel.area.getText();
				String arr[]=PanelText.split(" ");
				//String done=null;
				boolean found=false;
				if(listPos == null) {
					for (int i = 0; i < arr.length; i++) {
						if (arr[i].equalsIgnoreCase(SearchText)) {
							//calling highlight method which is implemented above
							found = true;
						}
					}
					if(found){
						makeList(SearchText, PanelText);
					}

				}
				if(listPos!=null && firstClick && e.getActionCommand().equals("replace")){
					highlight(TextPanel.area, SearchText);
					firstClick = false;
				}
				else if(listPos == null) {
					JOptionPane.showMessageDialog(TextPanel.area, " Word Not Found ");
					SText.setText("");
					SText2.setText("");
				}
				else {
					if(e.getActionCommand().equals("replace")) {
						int p = pos;
						PanelText = TextPanel.area.getText().substring(0, p) + TextPanel.area.getText().substring(p).replaceFirst(SearchText, SText2.getText());
						listPos.delete(p);
						makeList(SearchText, PanelText);
						firstClick = true;
					}
					else{
						PanelText=TextPanel.area.getText().replaceAll(SearchText,SText2.getText());
						listPos = null;
					}
					TextPanel.area.setText(PanelText);

				}
			}
		});

		FindReplace.addActionListener(replace);
		FindReplace.setActionCommand("replace");

		FindReplaceAll.addActionListener(replace);
		FindReplaceAll.setActionCommand("replaceAll");
		//this will arrange the buttons
		GridBagConstraints c = new GridBagConstraints(); 

		//Add in panel
		panel.add(F);
		c.insets= new Insets(0,0,10,10);
		c.gridx=0;
		c.gridy=1;
		panel.add(SText,c);
		c.insets= new Insets(0,0,10,10);
		c.gridx=0;
		c.gridy=2;
		panel.add(rw,c);
		c.insets= new Insets(0,0,10,10);
		c.gridx=0;
		c.gridy=3;
		panel.add(SText2,c);
		c.insets= new Insets(0,0,10,10);
		c.gridx=1;
		c.gridy=1;
		
		panel.add(FindWord, c);
		c.insets= new Insets(0,0,10,10);
		c.gridx=1;
		c.gridy=3;
		panel.add(FindReplace, c);
		c.gridx=1;
		c.gridy=4;
		panel.add(FindReplaceAll, c);

		SText.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				listPos = null;
			}
		});

		//Add Panel in frame
		add(panel, BorderLayout.EAST);
		
	}
	public void makeList(String SearchText, String PanelText){
		listPos = new CircularLinkedList();
		int p = 0;
		while((p= PanelText.indexOf(SearchText, p)) >=0){
			listPos.add(p);
			p+=SearchText.length();
		}
		listPos.makeItCircular();
	}
}
class CircularLinkedList{
	class Node{
		int data = 0;
		Node next = null;
		Node(int data){
			this.data = data;
		}
	}
	Node head = null;
	Node tail = null;
	public void add(int data){
		if(head == null){
			tail = head = new Node(data);
			return;
		}
		Node node = new Node(data);
		tail.next = node;
		tail = node;
	}
	public void makeItCircular(){
		if(head != null){
			tail.next = head;
		}
	}
	public int removeFirst(){
		int value = -1;
		if(head != null){
			value = head.data;
			head = head.next;
		}
		return value;
	}
	public int firstNode(){
		return head.data;
	}
	public void delete(int data){
		Node node = findNode(data);
		if(node == null){
			return;
		}
		node = previousNode(node);
		node.next = node.next.next;

	}
	public Node previousNode(Node node){
		Node temp = head;
		while(temp.next != node){
			temp = temp.next;
		}
		return temp;
	}
	public Node findNode(int data){
		Node node = head;
		do{
			if(node.data == data){
				return node;
			}
			node = node.next;

		}while(node != head);
		return null;
	}

	@Override
	public String toString() {
		String str = head.data + ", ";
		Node node = head.next;
		while(node.next != head){
			str += node.data + ",  ";
			node = node.next;
		}
		return str;
	}
}