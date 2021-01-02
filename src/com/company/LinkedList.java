package com.company;

import java.util.Arrays;
import java.util.NoSuchElementException;
public class LinkedList {
    public class Node{
        Node next = null;
        String data = "";

        public Node(String data) {
            this.data = data;
        }
    }
    Node head = null;
    Node tail = null;
    private int size = 0;

    public void add(String val){
        //O(n)
        Node node = new Node(val);
        if(isEmpty()){
            head = tail = node;
        }
        else{
            tail.next = node;
            tail = node;
        }
        size++;
    }
    public void remove(){
        //O(n)
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        if(head == tail){
            head = tail = null;
        }
        else{
            Node prev = previous(tail);
            prev.next = null;
            tail = prev;
        }
        size--;

    }
    public Node previous(Node node){
        Node current = head;
        while (current != null){
            if(current == node){
                return current;
            }
            current = current.next;
        }
        return null;
    }
    public int indexOf(String val){
        //O(n)
        Node current = head;
        int index = 0;
        while(current != null){
            if(val.equalsIgnoreCase(current.data)){
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }
    public boolean contains(String val){
        //O(n);
        return indexOf(val) != -1;
    }
    private boolean isEmpty(){
        //O(1)
        return  head == null;
    }
    public int getSize() {
        //O(1)
        return size;
    }
    public String[] getArray(){
        Node current = head;
        int index = 0;
        String[] arr = new String[size];
        while(current != null){
            arr[index++] = current.data;
            current = current.next;
        }
        return arr;
    }
}