package DoublyLinkedListWithOnlyHeadNode;

class Link{
	public int val;
	public Link next;
	public Link previous;
	
	public Link(int v) {
		val = v;
	}
	
	public void display() {
		System.out.print(val+" ");
	}
}

class DoublyLinkedListWithOnlyHead{
	private Link head;
	
	public DoublyLinkedListWithOnlyHead() {
		head = null;
	}
	
	public boolean isEmpty() {
		return (head == null);
	}
	
	public void insertStart(int v) {
		Link newLink = new Link(v);
		if(!isEmpty()) {
			head.previous = newLink;
		}
		newLink.next = head;
		head = newLink;
	}
	
	public void insertEnd(int v) {
		Link newLink = new Link(v);
		if(isEmpty()) {
			head = newLink;

		}else {
			Link p;
			for(p = head; p.next != null; p = p.next) {
				;
			}
			newLink.previous = p;
			p.next = newLink;
		}
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
		if(isEmpty()) {
			return;
		}
		if(head.next == null) {
			head = null;
			return;
		}
		head.next.previous = null;
		head = head.next;
	}
	
	public void removeEnd() {
		if(isEmpty()) {
			return;
		}
		if(head.next==null) {
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
		return Integer.toString(current.val);
	}
	
	public void output() {
		Link current = head;
		while(current!=null) {
			current.display();
			current = current.next;
		}
		System.out.println();
	}
}

public class DoublyLinkedListWithOnlyHeadNodeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DoublyLinkedListWithOnlyHead dl = new DoublyLinkedListWithOnlyHead();
		dl.insertStart(12);
		dl.output();
		dl.insertStart(15);
		dl.output();
		dl.insertEnd(20);
		dl.output();
		dl.insertEnd(25);
		dl.output();
		dl.insertBefore(1,30);
		dl.output();
		dl.insertAfter(2, 55);
		dl.output();
		dl.removeEnd();
		dl.output();
		dl.removeEnd();
		dl.output();
		dl.removeEnd();
		dl.output();
		dl.removeEnd();
		dl.output();
		dl.removeEnd();
		dl.output();
		dl.removeEnd();
		dl.output();
		dl.insertAfter(0, 15);
		dl.output();
		dl.insertAfter(1, 5);
		dl.output();
		dl.insertAfter(0, 25);
		dl.output();
		dl.insertAfter(1, 4);
		dl.output();
		dl.insertAfter(2, 45);
		dl.output();
		dl.insertAfter(4, 75);
		dl.output();
		dl.Remove(0);
		dl.Remove(0);
		dl.Remove(0);
		dl.Remove(0);
		dl.output();
		System.out.println(dl.getSize());
		System.out.println(dl.get(3));
	}

}
