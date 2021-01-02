package com.company;

import java.util.Arrays;
import java.util.HashMap;

public class Tries {
    private class Node{
        char value;
        HashMap<Character, Node> children = new HashMap<Character, Node>();
        boolean isEndOfWord;
        Node(char value){
            this.value = value;
        }

        @Override
        public String toString() {
            return value + "";
        }
        public boolean hasChild(char ch){
            return children.containsKey(ch);
        }
        public void addChildren(char ch){
            children.put(ch, new Node(ch));
        }
        public Node getChild(char ch){
            return children.get(ch);
        }
        public Node[] getChildren(){
            return children.values().toArray(new Node[0]);
        }
    }
    Node root = new Node(' ');
    public void insert(String word){
        Node current = root;
        for(char ch: word.toCharArray()){
            if(!current.hasChild(ch)){
                current.addChildren(ch);
            }
            current = current.getChild(ch);
        }
        current.isEndOfWord = true;
    }
    public boolean contains(String word) {
        if (word == null)
            return false;

        Node current = root;
        for (char ch : word.toCharArray()) {
            if (!current.hasChild(ch))
                return false;
            current = current.getChild(ch);
        }
        return current.isEndOfWord;
    }
    public LinkedList getWords(String preWord) {
        LinkedList words = new LinkedList();
        Node lastNode = getLastCharNode(preWord);
        getWords(lastNode, preWord, words);

        return words;
    }

    private void getWords(Node cur, String preWord, LinkedList words) {
        if (cur == null)
            return;

        if (cur.isEndOfWord)
            words.add(preWord);

        for (Node child : cur.getChildren())
            getWords(child, preWord + child.value, words);
    }

    private Node getLastCharNode(String preWord) {
        if (preWord == null)
            return null;

        Node current = root;
        for (char ch : preWord.toCharArray()) {
            if (!current.hasChild(ch))
                return null;
            current = current.getChild(ch);
        }
        return current;
    }
}