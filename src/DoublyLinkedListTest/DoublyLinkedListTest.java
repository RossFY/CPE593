package DoublyLinkedListTest;

class Link{
	public int data;
	public Link next;
	public Link previous;
	
	public Link(int val) {
		data = val;
	}
	
	public void display() {
		System.out.print(data+" ");
	}
}

class DoublyLinkedList{
	private Link head;
	private Link tail;
	
	public DoublyLinkedList() {
		head = null;
		tail = null;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public void insertStart(int v) {
		Link newNode = new Link(v);
		if(isEmpty()) {
			tail = newNode;
		}else {
			head.previous = newNode;
		}
		newNode.next = head;
		head = newNode;
	}
	
	public void insertEnd(int v) {
		Link newNode = new Link(v);
		if(isEmpty()) {
			head = newNode;
		}else {
			tail.next = newNode;
			newNode.previous = tail;
		}
		tail = newNode;
	}
	
	public Link find(int index) {
		if(index < 0 || index > getSize() - 1) {
			System.out.println("Error! Out of Boundary!");
			return null;
		}
		Link current = head;
		int i = 0;
		while(current != null) {
			if(i == index) {
				return current;
			}
			i++;
			current = current.next;
		}
		
		return current;
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
			Link p = find(index);
			newLink.previous = p.previous;
			newLink.next = p;
			p.previous.next = newLink;
			p.previous = newLink;
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
			newLink.previous = p;
			newLink.next = p.next;
			p.next.previous = newLink;
			p.next = newLink;
		}
	}
	
	public void removeStart() {
		if(head.next == null) {
			tail = null;
		}else {
			head.next.previous = null;
		}
		head = head.next;
	}
	
	public void removeEnd() {
		if(head.next==null) {
			head = null;
		}else {
			tail.previous.next = null;
		}
		tail = tail.previous;
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
			Link temp = find(index);
			temp.previous.next = temp.next;
			temp.next.previous = temp.previous;
		}
	}
	
	public void displayForward() {
		System.out.println("List(from head to tail): ");
		Link current = head;
		while(current != null) {
			current.display();
			current = current.next;
		}
		System.out.println();
	}
	
	public void displayBackward() {
		System.out.println("List(from tail to head): ");
		Link current = tail;
		while(current != null) {
			current.display();
			current = current.previous;
		}
		System.out.println();
	}
	
	public int getSize() {
		Link current = head;
		int count = 0;
		while(current!=null) {
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

public class DoublyLinkedListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DoublyLinkedList dll = new DoublyLinkedList();
		dll.insertStart(10);
		dll.insertStart(20);
		dll.insertEnd(15);
		dll.insertEnd(25);
		dll.displayForward();
		dll.displayBackward();
		dll.removeStart();
		dll.displayForward();
		dll.removeEnd();
		dll.displayForward();
		dll.removeEnd();
		dll.displayForward();
		dll.removeStart();
		dll.displayForward();
		dll.insertAfter(0, 5);
		dll.displayForward();
		dll.insertAfter(1, 5);
		dll.displayForward();
		dll.insertAfter(0, 25);
		dll.displayForward();
		dll.insertAfter(0, 10);
		dll.displayForward();
		System.out.println(dll.get(2));
		dll.Remove(0);
		dll.Remove(0);
		dll.Remove(0);
		dll.displayForward();
	}

}
