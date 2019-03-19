/*
 * Ye Fang - 10431002
 * reference:
 * 1. https://codereview.stackexchange.com/questions/85781/double-ended-linked-list
 * 2. https://en.wikipedia.org/wiki/Josephus_problem
 */

package Hw_4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class Link{
	public int val;
	public Link next;
	
	public Link(int v) {
		val = v;
	}
	
	public void displayLink() {
		System.out.print(val+" ");
	}
}

class LinkedList{
	private Link head;
	private Link tail;
	private int size;
	
	public LinkedList(){
		head = null;
		tail = null;
		size = 0;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public void addFront(int v) {
		Link newNode = new Link(v);
		if(isEmpty()) {
			tail = newNode;
		}
		newNode.next = head;
		head = newNode;
		size++;
	}
	
	public void addBack(int v) {
		Link newNode = new Link(v);
		if(isEmpty()) {
			head = newNode;
		}else {
			tail.next = newNode;
		}
		tail = newNode;
		size++;
	}
	
	public int removeFront() {
        if (head == tail) { // one node in list
            int temp = head.val;
            head = null;
            tail = null;
            size--;
            return temp;               
        }
        else { // more than one node in list
            int temp = head.val;      
            head = head.next;   
            size--;
            return temp;  
        }
	}
	
	public int removeBack() {
		Link current, prev;
		prev = current = head;
		while(current.next!=null) {
			prev = current;
			current = current.next;
		}
		int temp = current.val;
		if(!(head == tail)) {
			tail = prev;
			tail.next = null;
			size--;
		}else {
			head = tail = null;
			size--;
		}
		return temp;
	}
	
	public void output() {
		Link current = head;
		while(current!=null) {
			current.displayLink();
			current = current.next;
		}
		System.out.println();
	}
	
	public int getSize() {
		return size;
	}
}

class Josephus{
	private LinkedList list;
	private int len;
	
	public Josephus(int n) {
		list = new LinkedList();
		len = n;
	}
	
	public void run() {
		for(int i = 0; i < len; i++) {
			list.addBack(i+1);
		}
		
		System.out.println("After adding, the list shows below: ");
		list.output();
		
		LinkedList res1 = new LinkedList();
		int n = list.getSize();
		for(int i = 0; i < n; i++) {
			if(i % 3 == 0) {
				list.removeFront();
			}else {
				res1.addBack(list.removeFront());
			}
		}
		System.out.println("Output First Round result:");
		res1.output();
		
		int m = res1.getSize();
		
		LinkedList res2 = new LinkedList();
		for(int i = 0; i < m; i++) {
			if(i % 3 == 0) {
				res1.removeFront();
			}else {
				res2.addBack(res1.removeFront());
			}
		}
		System.out.println("Then repeat. Output Second Round result:");
		res2.output();
		
		int resultSize = res2.getSize();
		LinkedList res3 = new LinkedList();
		System.out.println("Reverse the result list and print it:");
		for(int i = 0; i < resultSize; i++)
			res3.addFront(res2.removeFront());
		res3.output();
		System.out.println();
	}
	
}

public class Hw_4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int size = 0;
		try {
			File f = new File(System.getProperty("user.dir")+"/linkedlist.dat");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			while((line = br.readLine()) != null) {
				size = Integer.parseInt(line);
			}
			br.close();
		
		}catch(Exception e) {
		e.printStackTrace();
		}
		Josephus j = new Josephus(size);
		j.run();
	}
}
