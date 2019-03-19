package DoubleEndedListTest;

class Link{
	public Link next;
	public int data;
	
	public Link(int v) {
		data = v;
	}
}

class DoubleEndedList{
	private Link head;
	private Link tail;
	
	public DoubleEndedList() {
		head = null;
		tail = null;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public void insertStart(int val) {
		Link newNode = new Link(val);
		if(isEmpty()) {
			tail = newNode;
		}
		newNode.next = head;
		head = newNode;
	}
	
	public void insertEnd(int val) {
		Link newNode = new Link(val);
		if(isEmpty()) {
			head = newNode;
		}else {
			tail.next = newNode;
		}
		tail = newNode;
	}
	
	public Link find(int index) {
		if(index < 0 || index > getSize() - 1) {
			System.out.println("Error! Out of Boundary!");
			return null;
		}
		
		int i = 0;
		Link current = head;
		while(current != null) {
			if(index == i) {
				return current;
			}
			i++;
			current = current.next;
		}
		return null;
	}
	
	public void insertBefore(int index, int v) {
		if(index < 0 || (index != 0 && index > getSize() - 1)) {
			System.out.println("Error! Out of Boundary!");
			return;
		}
		Link newLink = new Link(v);
		if(index == 0) {
			insertStart(v);
		}else {
			Link p = find(index-1);
			Link q = find(index);
			newLink.next = p;
			q.next = newLink;
		}
	}
	
	public void insertAfter(int index, int v) {
		if(index < 0 || (index != 0 && index > getSize() - 1)) {
			System.out.println("Error! Out of Boundary!");
			return;
		}
		Link newLink = new Link(v);
		if(isEmpty() || head.next == null || index == getSize() - 1) {
			insertEnd(v);
		}else {
			Link p = find(index);
			Link q = find(index+1);
			p.next = newLink;
			newLink.next = q;
		}
	}
	
	public void removeStart() {
		if(head.next == null) {
			tail = null;
		}
		head = head.next;
	}
	
	public void removeEnd() {
		if(head == null) {
			return;
		}
		if(head.next == null) {
			head = tail = null;
			return;
		}
		Link p = head;
		for(Link q = p.next; q.next != null;) {
			p = q;
			q = q.next;
		}
		p.next = null;
		tail = p;
	}
	
	public void Remove(int index) {
		if(index < 0 || index > getSize() - 1) {
			System.out.println("Error! Out of Boundary!");
			return;
		}
		if(index == 0) {
			removeStart();
		}else if(index == getSize() - 1) {
			removeEnd();
		}else {
			//Link temp = find(index);
			Link p = find(index - 1);
			Link q = find(index + 1);
			p.next = q;
		}
	}
	
	public void output() {
		Link current = head;
		while(current != null) {
			System.out.print(current.data+" ");
			current = current.next;
		}
		System.out.println();
	}
	
	public int getSize() {
		int count = 0;
		Link current = head;
		while(current != null) {
			count++;
			current = current.next;
		}
		return count;
	}
	
	public String get(int index) {
		Link current = find(index);
		if(index < 0 || index > getSize() - 1) {
			return null;
		}
		return Integer.toString(current.data);
	}
}

public class DoubleEndedListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DoubleEndedList del = new DoubleEndedList();
		del.insertEnd(10);
		del.insertEnd(20);
		del.insertStart(15);
		del.insertStart(25);
		del.output();
		System.out.println(del.get(4));
		del.removeEnd();
		del.output();
		del.removeStart();
		del.output();
		del.removeEnd();
		del.output();
		del.removeEnd();
		del.output();
		del.insertAfter(0, 1);
		del.output();
		del.insertAfter(0, 2);
		del.output();
		del.insertAfter(1, 3);
		del.output();
		del.insertAfter(1, 5);
		del.output();
		del.Remove(0);
		del.Remove(0);
		del.Remove(0);
		del.Remove(0);
		del.output();
	}

}
