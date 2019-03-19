package LinkedListStackTest;

class Link{
	public int data;
	public Link next;
	
	public Link(int v) {
		data = v;
	}
	
	public void display() {
		System.out.println(data+" ");
	}
}

class LinkedList{
	private Link head;
	
	public LinkedList() {
		head = null;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public void insertStart(int val) {
		Link newNode = new Link(val);
		newNode.next = head;
		head = newNode;
	}
	
	public int removeStart() {
		int temp = head.data;
		head = head.next;
		return temp;
	}
	
	public void displayList() {
		Link current = head;
		while(current != null) {
			current.display();
			current = current.next;
		}
		System.out.println();
	}
}

class LinkedListStack{
	private LinkedList list;
	
	public LinkedListStack() {
		list = new LinkedList();
	}
	
	public void push(int v) {
		list.insertStart(v);
	}
	
	public int pop() {
		return list.removeStart();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void displayStack() {
		System.out.println("Stack(from top to bottom):");
		list.displayList();
	}
}

public class LinkedListStackTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedListStack s = new LinkedListStack();
		s.push(10);
		s.push(15);
		s.push(20);
		s.push(25);
		s.displayStack();
		s.pop();
		s.pop();
		s.displayStack();
	}

}
