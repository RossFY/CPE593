package LinkedListQueueTest;

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
	
	public void insertEnd(int val) {
		if(head == null) {
			head = new Link(val);
			head.next = null;
			return;
		}
		Link p;
		for(p = head; p.next!=null; p = p.next) {
			;
		}
		Link newNode = new Link(val);
		p.next = newNode;
		p.next.next = null;
	}
	
	public int removeStart() {
		int temp = head.data;
		head = head.next;
		return temp;
	}
	
	public void displayList() {
		Link current = head;
		while(current!=null) {
			current.display();
			current = current.next;
		}
		System.out.println();
	}
}

class LinkedListQueue{
	private LinkedList list;
	
	public LinkedListQueue() {
		list = new LinkedList();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void insert(int v) {
		list.insertEnd(v);
	}
	
	public int remove() {
		return list.removeStart();
	}
	
	public void displayQueue() {
		System.out.println("Queue(from first to last): ");
		list.displayList();
		System.out.println();
	}
}

public class LinkedListQueueTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedListQueue q = new LinkedListQueue();
		q.insert(5);
		q.insert(10);
		q.insert(15);
		q.insert(20);
		q.displayQueue();
		q.remove();
		q.remove();
		q.displayQueue();
	}

}
