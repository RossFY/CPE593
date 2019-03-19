package LinkedListTest;

class Link{
	public int val;
	public Link next;
	
	public Link(int v) {
		val = v;
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
	
	public void insertEnd(int v) {
		if(head == null){
			head = new Link(v);
			head.next = null;
			return;
		}
		Link p;
		for(p = head; p.next != null; p = p.next) {
			;
		}
		Link tmp = new Link(v);
		p.next = tmp;
		p.next.next = null;
	}
	
	public void insertStart(int v) {
		Link newNode = new Link(v);
		newNode.next = head;
		head = newNode;
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
		if(head == null) {
			return;
		}
		//int temp = head.val;
		head = head.next;
		//return temp;
	}
	
	public void removeEnd() {
		if(head == null) {
			return;
		}
		if(head.next == null) {
			head = null;
			return;
		}
		Link p = head;
		for(Link q = p.next; q.next != null;) {
			p = q;
			q = q.next;
		}
		p.next = null;
	}
	
	public int getSize() {
		int count = 0;
		for(Link p = head; p != null; p = p.next) {
			count++;
		}
		return count;
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
			System.out.print(current.val+" ");
			current = current.next;
		}
		System.out.println();
	}
	
	public String get(int index) {
		Link current = find(index);
		if(index < 0 || index > getSize() - 1) {
			return null;
		}
		return Integer.toString(current.val);
	}
}

public class LinkedListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList l = new LinkedList();
		l.insertStart(12);
		l.insertStart(10);
		l.insertEnd(6);
		l.insertEnd(7);
		l.output();
		System.out.println(l.getSize());
		l.removeEnd();
		l.removeStart();
		l.output();
		System.out.println(l.getSize());
		l.removeEnd();
		l.removeStart();
		l.output();
		System.out.println(l.getSize());
		l.insertAfter(0, 1);
		l.output();
		l.insertAfter(0, 3);
		l.output();
		l.insertAfter(0, 2);
		l.output();
		System.out.println(l.get(4));
		l.Remove(0);
		l.Remove(0);
		l.Remove(0);
		l.output();
	}

}
